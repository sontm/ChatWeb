package sansanvn.web.chatweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sansanvn.web.chatweb.dao.DBChatRoomMapper;
import sansanvn.web.chatweb.dao.DBUserMapper;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBChatRoomExample;
import sansanvn.web.chatweb.entity.DBUser;
import sansanvn.web.chatweb.entity.DBUserExample;

@Service
public class ServiceUser {

	@Autowired
	private DBUserMapper mapper;
	
	public DBUser getUserFromID(int id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public DBUser getByUsernameOrEmail(String username, String email) {
		DBUserExample query = new DBUserExample();
		if (username.isEmpty() == false) {
			query.createCriteria().andUsernameEqualTo(username);
			
			List<DBUser> ret = mapper.selectByExample(query);
			if (ret != null && ret.size() == 1) {
				return  ret.get(0);
			}
		} else if (email.isEmpty() == false) {
			query.createCriteria().andEmailEqualTo(email);
			
			List<DBUser> ret = mapper.selectByExample(query);
			if (ret != null && ret.size() == 1) {
				return  ret.get(0);
			}
		}
		
		return null;
	}
}
