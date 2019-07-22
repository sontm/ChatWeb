package sansanvn.web.chatweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sansanvn.web.chatweb.dto.User;


@RestController
public class UserController {

	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public List<User> getAllPlaces(){
		System.out.println("[API] Get All Place");
		List<User> ret = new ArrayList<User>();
		
		ret.add(new User(1, "sontm", "123"));
		ret.add(new User(2, "cuongnv", "123"));
		
		return ret;
    }
	
}
