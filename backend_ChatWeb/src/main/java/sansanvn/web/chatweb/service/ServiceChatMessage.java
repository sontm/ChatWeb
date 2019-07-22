package sansanvn.web.chatweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.DataBindingMethodResolver;
import org.springframework.stereotype.Service;

import sansanvn.web.chatweb.dao.DBChatMessageMapper;
import sansanvn.web.chatweb.entity.DBChatMessage;
import sansanvn.web.chatweb.entity.DBChatMessageExample;

@Service
public class ServiceChatMessage {

	@Autowired
	private DBChatMessageMapper mapper;
	
	public List<DBChatMessage> getAllMessageOfRoom(Integer id) {
		DBChatMessageExample query = new DBChatMessageExample();
		query.createCriteria().andRoomidEqualTo(id);
		
		return mapper.selectByExample(query);
	}
	
	// Dont care about ID
	public int insertMessage(DBChatMessage msg) {
		return mapper.insert(msg);
	}
}
