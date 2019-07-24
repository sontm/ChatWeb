package sansanvn.web.chatweb.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sansanvn.web.chatweb.dto.ChatMessage;
import sansanvn.web.chatweb.dto.ChatRoom;
import sansanvn.web.chatweb.dto.SubcribeRoom;
import sansanvn.web.chatweb.entity.DBChatMessage;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBUser;
import sansanvn.web.chatweb.service.ServiceChatMessage;
import sansanvn.web.chatweb.service.ServiceUser;

@RestController
public class MessageController {
	@Autowired
	private ServiceChatMessage serviceChatMessage;
	
	@Autowired
	private ServiceUser serviceUser;
	
	@PostConstruct
	public void initialize() {
		//
	}
	
	// Whenusing Socket, need add param to Parameter, no in Request URL
	@MessageMapping("/subcribeToRoom")
	@SendTo("/socket/messages")
	//@Secured("ROLE_ADMIN") // Disable Security for now
    public List<ChatMessage> subcribeToRoom(SubcribeRoom room){
		System.out.println("subcribeToRoom:" + room.getId());
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
		return retWeb;
    }
	
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
	
	// handler
	// api/subcribe
	@MessageMapping("/subcribe")
	@SendTo("/socket/messages")
	public List<ChatMessage> newMessage(ChatMessage msg) throws Exception {
		System.out.println("New Msg:" + msg.getFrom() + "," + msg.getContent());
		List<ChatMessage> retWeb = new ArrayList<ChatMessage>();
		// Insert this Message to DB
		// TODO this get User ID from User Name

		
		DBChatMessage dbMsg = new DBChatMessage();
		dbMsg.setContent(msg.getContent());
		dbMsg.setRoomid(msg.getToRoomID());
		dbMsg.setTimestamp(new Date());
		
		DBUser user = serviceUser.getByUsernameOrEmail(msg.getFrom(), msg.getFrom());
		if (user != null) {
			dbMsg.setFromUserid(user.getId());
		}
		
		serviceChatMessage.insertMessage(dbMsg);

		retWeb.add (new ChatMessage(dbMsg.getId(), msg.getFrom(), 1, msg.getContent()));
		
		return retWeb;
	}
}
