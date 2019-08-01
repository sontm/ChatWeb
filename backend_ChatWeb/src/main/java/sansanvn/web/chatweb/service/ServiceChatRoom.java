package sansanvn.web.chatweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sansanvn.web.chatweb.dao.DBChatRoomMapper;
import sansanvn.web.chatweb.dao.DBUserInRoomMapper;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBChatRoomExample;
import sansanvn.web.chatweb.entity.DBUserInRoom;
import sansanvn.web.chatweb.entity.DBUserInRoomExample;

@Service
public class ServiceChatRoom {

	@Autowired
	private DBChatRoomMapper mapper;
	
	@Autowired
	private DBUserInRoomMapper mapperRoomAndUser;
	
	// TODO. Using JOIN, not this Slow code
	public List<DBChatRoom> getAllJoinableRooms(String username) {
//		DBChatRoomExample query = new DBChatRoomExample();
//		query.createCriteria();
//		
//		return mapper.selectByExample(query);
		DBUserInRoomExample query = new DBUserInRoomExample();
		query.createCriteria().andUsernameEqualTo(username);
		
		List<DBUserInRoom> joinableIDs =  mapperRoomAndUser.selectByExample(query);
		List<DBChatRoom> result = new ArrayList<DBChatRoom>();
		if (joinableIDs != null) {
			for (DBUserInRoom dbUserInRoom : joinableIDs) {
				
				DBChatRoom  cur = mapper.selectByPrimaryKey(dbUserInRoom.getIdroom());
				if (cur != null) {
					result.add(cur);
				}
			}
		}
		return result;
	}
	
	public List<String> getAllUserInRoom(int roomID) {
		List<String> result = new ArrayList<String>();
		DBUserInRoomExample query = new DBUserInRoomExample();
		query.createCriteria().andIdroomEqualTo(roomID);
		
		List<DBUserInRoom> joinableIDs =  mapperRoomAndUser.selectByExample(query);
		if (joinableIDs != null) {
			for (DBUserInRoom dbUserInRoom : joinableIDs) {
				result.add(dbUserInRoom.getUsername());
			}
		}
		
		return result;
	}
}
