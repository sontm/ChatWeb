package sansanvn.web.chatweb.entity;

import java.util.Date;

public class DBChatMessage {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column public.dbchat_message.id
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column public.dbchat_message.from_userid
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	private Integer fromUserid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column public.dbchat_message.content
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column public.dbchat_message.roomid
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	private Integer roomid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column public.dbchat_message.timestamp
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	private Date timestamp;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column public.dbchat_message.id
	 * @return  the value of public.dbchat_message.id
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column public.dbchat_message.id
	 * @param id  the value for public.dbchat_message.id
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column public.dbchat_message.from_userid
	 * @return  the value of public.dbchat_message.from_userid
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public Integer getFromUserid() {
		return fromUserid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column public.dbchat_message.from_userid
	 * @param fromUserid  the value for public.dbchat_message.from_userid
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public void setFromUserid(Integer fromUserid) {
		this.fromUserid = fromUserid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column public.dbchat_message.content
	 * @return  the value of public.dbchat_message.content
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column public.dbchat_message.content
	 * @param content  the value for public.dbchat_message.content
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column public.dbchat_message.roomid
	 * @return  the value of public.dbchat_message.roomid
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public Integer getRoomid() {
		return roomid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column public.dbchat_message.roomid
	 * @param roomid  the value for public.dbchat_message.roomid
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public void setRoomid(Integer roomid) {
		this.roomid = roomid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column public.dbchat_message.timestamp
	 * @return  the value of public.dbchat_message.timestamp
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column public.dbchat_message.timestamp
	 * @param timestamp  the value for public.dbchat_message.timestamp
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}