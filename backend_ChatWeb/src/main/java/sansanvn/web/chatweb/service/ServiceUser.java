package sansanvn.web.chatweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sansanvn.web.chatweb.dao.DBChatRoomMapper;
import sansanvn.web.chatweb.dao.DBUserMapper;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBChatRoomExample;
import sansanvn.web.chatweb.entity.DBUser;

@Service
public class ServiceUser {

	@Autowired
	private DBUserMapper mapper;
	
	public DBUser getUserFromID(int id) {
		return mapper.selectByPrimaryKey(id);
	}
}
