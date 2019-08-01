package sansanvn.web.chatweb;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import sansanvn.web.chatweb.security.CustomUserDetailsService;
import sansanvn.web.chatweb.security.JwtTokenProvider;
import sansanvn.web.chatweb.security.UserPrincipal;

// Set Order to Highest to make this come before Spring Security HIGHEST_PRECEDENCE=-2147483648
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 70)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic", "/user");
        config.setApplicationDestinationPrefixes("/api");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
        //.setWebSocketEnabled(false)
        //.setSessionCookieNeeded(false);
    }

    
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		System.out.println("WebSocketConfig configureClientInboundChannel called");
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor =
						MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				List<String> tokenList1 = accessor.getNativeHeader("Authorization");
				if (tokenList1 != null && tokenList1.size() > 0) {
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					List<String> tokenList = accessor.getNativeHeader("Authorization");
					System.out.println("------Socket Security:sz:" + tokenList.size() + "," + tokenList.get(0));
				      String bearerToken = null;
				      if(tokenList == null || tokenList.size() < 1) {
				        return message;
				      } else {
				    	  bearerToken = tokenList.get(0);
				        if(bearerToken == null) {
				          return message;
				        }
				      }
				      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
				    	  String jwt = bearerToken.substring(7, bearerToken.length());
				    	  
				    	  if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				                int userId = tokenProvider.getUserIdFromJWT(jwt);

				                UserDetails userDetails;
								try {
									userDetails = customUserDetailsService.loadUserById(userId);
									UsernamePasswordAuthenticationToken authentication = 
					                		new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

					                accessor.setUser(authentication);
					                accessor.setLeaveMutable(true);
					               // return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
					                
					                System.out.println("------Send Message:" + accessor.getSessionId());
					                return message;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				                
				            }
				    	 // Authentication user = authenticationManager.authenticate() // access authentication header(s)
									//accessor.setUser(user);
				        }
				}
				}
				return message;
			}
		});
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}