package sansanvn.web.chatweb.dto;

public class ChatRoom {
	private int id;
	private String roomname;
	
	public ChatRoom(int pid, String name) {
		id = pid;
		roomname = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	
	
}
