package sansanvn.web.chatweb.dto;

public class ChatMessage {
	private int id;
	private int fromUserID;
	private String from;
	private int toRoomID;
	private String content;
	
	public ChatMessage(int pid,String pf,int pt, String pc, int f) {
		id = pid;
		from = pf;
		toRoomID = pt;
		content = pc;
		fromUserID = f;
	}
	
	public int getFromUserID() {
		return fromUserID;
	}

	public void setFromUserID(int fromUserID) {
		this.fromUserID = fromUserID;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getToRoomID() {
		return toRoomID;
	}

	public void setToRoomID(int toRoomID) {
		this.toRoomID = toRoomID;
	}
	
	
}
