package sansanvn.web.chatweb;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.apache.ibatis.session.SqlSessionFactory;
//@SpringBootApplication
//public class WebanalyticApplication extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(WebanalyticApplication.class);
//    }
//
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(WebanalyticApplication.class, args);
//    }
//
//}

//// extends SpringBootServletInitializer for WAR file
@SpringBootApplication
//@ComponentScan("sansanvn.web.chatweb.service")
//@ComponentScan("fateit.java.webanalytic.db.entity")
//@ComponentScan("fateit.java.webanalytic.webmodel")
//@MapperScan("sansanvn.web.chatweb.client")
@ComponentScan("sansanvn.web.chatweb")
@ComponentScan("sansanvn.web.chatweb.service")
@ComponentScan("sansanvn.web.chatweb.entity")
@MapperScan("sansanvn.web.chatweb.dao")
public class ChatWebApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ChatWebApplication.class, args);
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	 registry.addMapping("/socket/**").allowedOrigins("http://localhost:3000");
            	 registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
            	 
//                registry.addMapping("/api/rooms").allowedOrigins("http://localhost:3000");
//                registry.addMapping("/api/messagesOfRoomID/{id}").allowedOrigins("http://localhost:3000");
//                registry.addMapping("/api/subcribe").allowedOrigins("http://localhost:3000");
            }
        };
    }
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mappers/*.xml"));
		//sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/sansanjav.web.chatweb.dao/*.xml"));

		return sqlSessionFactoryBean;
	}
	@Bean
	public DataSource dataSource() {
	   //return new org.apache.tomcat.jdbc.pool.DataSource();
		DriverManagerDataSource  dataSource = new DriverManagerDataSource ();
	   dataSource.setDriverClassName("org.postgresql.Driver");
	   dataSource.setUrl("jdbc:postgresql://localhost:5432/chatweb");
	   dataSource.setUsername("postgres");
	   dataSource.setPassword("postgres");
	   return dataSource;
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE + 80)
    public ChannelInterceptor messageChannelInterceptor() {
        ChannelInterceptor channelInterceptor = new ChannelInterceptor() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("Here----------------------: " + message);
                return message;
            }

        };
        return channelInterceptor;
    }
}

