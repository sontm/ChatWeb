package sansanvn.web.chatweb.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sansanvn.web.chatweb.entity.DBUser;
import sansanvn.web.chatweb.service.ServiceUser;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ServiceUser serviceUser;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
    	System.out.println("[DBG] CustomUserDetailsService, loadUserByUsername:" + usernameOrEmail);
        DBUser user = serviceUser.getByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user != null) {
        	return UserPrincipal.create(user);
        } else {
        	throw (new UsernameNotFoundException("[BackEnd] User not found with username or email : " + usernameOrEmail));
        }
    }

    @Transactional
    public UserDetails loadUserById(int id) throws Exception {
    	System.out.println("[DBG] CustomUserDetailsService, loadUserById:" + id);
    	DBUser user = serviceUser.getUserFromID(id);
    	if (user != null) {
    		return UserPrincipal.create(user);
    	} else {
    		throw(new Exception("[BackEnd] Exception, Cannot found User by ID:" + id));
    	}
    }
}