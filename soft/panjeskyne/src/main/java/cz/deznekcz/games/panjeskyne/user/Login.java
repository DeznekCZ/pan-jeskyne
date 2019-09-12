package cz.deznekcz.games.panjeskyne.user;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1649744180783722024L;
	private User user;

	public Login(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(user);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		user = (User) in.readObject();
	}
	
	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new ObjectStreamException() {
			private static final long serialVersionUID = 1L;
		};
	}

}