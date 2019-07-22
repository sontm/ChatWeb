package sansanvn.web.chatweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sansanvn.web.chatweb.dao.DBChatRoomMapper;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBChatRoomExample;

@Service
public class ServiceChatRoom {

	@Autowired
	private DBChatRoomMapper mapper;
	
	public List<DBChatRoom> getAllJoinableRooms() {
		DBChatRoomExample query = new DBChatRoomExample();
		query.createCriteria();
		
		return mapper.selectByExample(query);
	}
}
