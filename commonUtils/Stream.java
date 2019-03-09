package commonUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Stream {
	private User user;
	private ObjectInputStream read;
	private ObjectOutputStream write;
	public Stream(User user, ObjectOutputStream sendObjects,ObjectInputStream recieveObjects) {
		this.setUser(user);
		this.read = recieveObjects;
		this.write = sendObjects;
	}
	public void setRead(ObjectInputStream read) {
		this.read = read;
	}
	public ObjectInputStream getRead() {
		return read;
	}
	public void setWrite(ObjectOutputStream write) {
		this.write = write;
	}
	public ObjectOutputStream getWrite() {
		return write;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
