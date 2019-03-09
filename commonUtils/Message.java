package commonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Message implements Serializable{
	
	private String type;
	private Vector message;
	private String destination;
	private String source;
	
	public Message(String type, Vector vector){
		this.type = type;
		this.message = vector;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setMessage(Vector message) {
		this.message = message;
	}

	public Vector getMessage() {
		return message;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestination() {
		return destination;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}
}
