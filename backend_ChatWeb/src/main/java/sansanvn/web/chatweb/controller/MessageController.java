package sansanvn.web.chatweb.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import sansanvn.web.chatweb.dto.ChatMessage;
import sansanvn.web.chatweb.dto.ChatRoom;
import sansanvn.web.chatweb.dto.SubcribeRoom;
import sansanvn.web.chatweb.entity.DBChatMessage;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBUser;
import sansanvn.web.chatweb.service.ServiceChatMessage;
import sansanvn.web.chatweb.service.ServiceChatRoom;
import sansanvn.web.chatweb.service.ServiceUser;

@RestController
public class MessageController {
	@Autowired
	private ServiceChatMessage serviceChatMessage;
	
	@Autowired
	private ServiceUser serviceUser;
	
	@Autowired
	private ServiceChatRoom serviceRoom;
	
	@PostConstruct
	public void initialize() {
	}

	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@RequestMapping(value = "/api/messagesOfRoomID/{id}", method = RequestMethod.GET)
	//@Secured("ROLE_ADMIN") // Disable Security for now
    public List<ChatMessage> getCurrencyTypeOfID(@PathVariable(value="id") int rID){
		List<ChatMessage> retWeb = new ArrayList<ChatMessage>();
		
		List<DBChatMessage> retDB = serviceChatMessage.getAllMessageOfRoom(rID);
		if (retDB != null && retDB.size() > 0) {
			for (DBChatMessage e : retDB) {
				DBUser user = serviceUser.getUserFromID(e.getFromUserid());
				if (user != null) {
					retWeb.add(new ChatMessage(e.getId(), user.getUsername(), e.getRoomid(), e.getContent()));
				}
				
			}
		}
		return retWeb;
    }
	
	// -----------------------------------------
		// Example of using @SendToUser; will send to user who request, broadcast=false mean only send to 1 session
		// -----------------------------------------
	// Whenusing Socket, need add param to Parameter, no in Request URL
	@MessageMapping("/subcribe/newJoinRoom")
	//@SendTo("topic/room/1")
	@SendToUser(destinations="/queue/messages", broadcast=false)
	//@Secured("ROLE_ADMIN") // Disable Security for now
    public List<ChatMessage> userJoinRoom(SubcribeRoom room){
		System.out.println("newJoinRoom:" + room.getId());
		List<ChatMessage> retWeb = new ArrayList<ChatMessage>();
		
		List<DBChatMessage> retDB = serviceChatMessage.getAllMessageOfRoom(room.getId());
		if (retDB != null && retDB.size() > 0) {
			for (DBChatMessage e : retDB) {
				DBUser user = serviceUser.getUserFromID(e.getFromUserid());
				if (user != null) {
					retWeb.add(new ChatMessage(e.getId(), user.getUsername(), e.getRoomid(), e.getContent()));
				}
				
			}
		}
		//messagingTemplate3.convertAndSendToUser(strUser, "/queue/messages", retWeb);
		return retWeb;
    }
	
	// -----------------------------------------
	// Example of using convertAndSendToUser to send to all User in Room
	// -----------------------------------------
	@MessageMapping("/subcribe/newMessage")
	//@SendToUser(destinations="/queue/messages", broadcast=false)
	//@SendToUser("/queue/messages")
	public void newMessage(ChatMessage msg, Principal principal) throws Exception {
		System.out.println("New User Msg:" + msg.getFrom() + "," + msg.getContent() + "," + msg.getToRoomID());
		System.out.println( "    Principal:" + principal.getName() + ",session:"
		);
		List<ChatMessage> retWeb = new ArrayList<ChatMessage>();

		DBChatMessage dbMsg = new DBChatMessage();
		dbMsg.setContent(msg.getContent());
		dbMsg.setRoomid(msg.getToRoomID());
		dbMsg.setTimestamp(new Date());
		
		DBUser user = serviceUser.getByUsernameOrEmail(msg.getFrom(), msg.getFrom());
		if (user != null) {
			dbMsg.setFromUserid(user.getId());
		}
		
		serviceChatMessage.insertMessage(dbMsg);

		retWeb.add (new ChatMessage(dbMsg.getId(), msg.getFrom(), msg.getToRoomID(), msg.getContent()));
		
		List<String> allUsersInRoom = serviceRoom.getAllUserInRoom(msg.getToRoomID());
		for (String strUser : allUsersInRoom) {
			messagingTemplate.convertAndSendToUser(strUser, "/queue/messages", retWeb);
		}
	}
	
	
	// -----------------------------------------
	// Example of using convertAndSend to send to a Topic with ID
	// -----------------------------------------
	// Whenusing Socket, need add param to Parameter, no in Request URL
	@MessageMapping("/newJoinRoom/{id}")
	//@SendTo("topic/room/1")
	//@SendToUser(destinations="/queue/messages", broadcast=false)
	//@Secured("ROLE_ADMIN") // Disable Security for now
    public void newJoinRoom(@DestinationVariable(value="id") int rID){
		System.out.println("newJoinRoom:" + rID);
		List<ChatMessage> retWeb = new ArrayList<ChatMessage>();
		
		List<DBChatMessage> retDB = serviceChatMessage.getAllMessageOfRoom(rID);
		if (retDB != null && retDB.size() > 0) {
			for (DBChatMessage e : retDB) {
				DBUser user = serviceUser.getUserFromID(e.getFromUserid());
				if (user != null) {
					retWeb.add(new ChatMessage(e.getId(), user.getUsername(), e.getRoomid(), e.getContent()));
				}
				
			}
		}
		messagingTemplate.convertAndSend("/topic/room/" + rID, retWeb);
		//return retWeb;
    }
		
	@MessageMapping("/newMessageToRoom/{id}")
	//@Secured("ROLE_ADMIN") // Disable Security for now
	//@SendTo("/topic/room/1")
    public void newRoomMessage(@DestinationVariable(value="id") int rID, ChatMessage msg){
		List<ChatMessage> retWeb = new ArrayList<ChatMessage>();
		
		System.out.println("NewRoomMessage of id:" + rID);
		
		System.out.println("    Msg:" + msg.getFrom() + "," + msg.getContent() + "," + msg.getToRoomID());
		
		DBChatMessage dbMsg = new DBChatMessage();
		dbMsg.setContent(msg.getContent());
		dbMsg.setRoomid(msg.getToRoomID());
		dbMsg.setTimestamp(new Date());
		
		DBUser user = serviceUser.getByUsernameOrEmail(msg.getFrom(), msg.getFrom());
		if (user != null) {
			dbMsg.setFromUserid(user.getId());
		}
		serviceChatMessage.insertMessage(dbMsg);

		retWeb.add (new ChatMessage(dbMsg.getId(), msg.getFrom(), msg.getToRoomID(), msg.getContent()));
		
		messagingTemplate.convertAndSend("/topic/room/" + rID, retWeb);
		
		//return retWeb;
    }
}
