package sansanvn.web.chatweb;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 30)
public class WebSocketChannelInterceptor implements ChannelInterceptor{

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		System.out.println("------Socket Security:333333");
		StompHeaderAccessor accessor =
				MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		List<String> tokenList1 = accessor.getNativeHeader("Authorization");
		System.out.println("------Socket Security:s5z:" + tokenList1.size() + "," + tokenList1.get(0));
		
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			List<String> tokenList = accessor.getNativeHeader("Authorization");
			System.out.println("------Socket Security:sz:" + tokenList.size() + "," + tokenList.get(0));
		      String token = null;
		      if(tokenList == null || tokenList.size() < 1) {
		        return message;
		      } else {
		        token = tokenList.get(0);
		        if(token == null) {
		          return message;
		        }
		      }
		      
			//Authentication user = authenticationManager.authenticate(authentication); // access authentication header(s)
			//accessor.setUser(user);
		}
		return message;
	}
}
