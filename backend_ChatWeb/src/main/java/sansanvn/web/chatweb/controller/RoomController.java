package sansanvn.web.chatweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sansanvn.web.chatweb.dto.ChatMessage;
import sansanvn.web.chatweb.dto.ChatRoom;
import sansanvn.web.chatweb.dto.GetRoomRequest;
import sansanvn.web.chatweb.dto.LoginRequest;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.service.ServiceChatRoom;

@RestController
public class RoomController {
	
	@Autowired
	private ServiceChatRoom serviceChatRoom;
	
	@PostMapping(value = "/api/rooms")
    public List<ChatRoom> getAllJoinableRooms(@RequestBody GetRoomRequest request){
		System.out.println("getAllJoinableRooms of user:" + request.getUsername());
		List<ChatRoom> retWeb = new ArrayList<ChatRoom>();
		
		List<DBChatRoom> retDB = serviceChatRoom.getAllJoinableRooms(request.getUsername());
		
		if (retDB != null && retDB.size() > 0) {
			for (DBChatRoom dbChatRoom : retDB) {
				retWeb.add(new ChatRoom(dbChatRoom.getId(), dbChatRoom.getRoomname()));
			}
		}
		return retWeb;
    }

}
