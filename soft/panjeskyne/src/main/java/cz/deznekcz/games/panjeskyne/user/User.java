package cz.deznekcz.games.panjeskyne.user;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6926338296196379444L;

	private static final String PASS = "pass"; 
	public static final String USERS = "/home/users/";
	
	private Properties properties;

	private File file;

	public User(String path) {
		read(path);
	}

	private void read(String path) {
		properties = new Properties();
		file = new File(USERS + path);
		
		BufferedInputStream fis;
		try {
			Scanner sc = new Scanner(file, "UTF8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				int eqpos = line.indexOf('=');
				if (eqpos != -1) {
					String key = StringUtils.substring(line, 0, eqpos);
					String data = StringUtils.substring(line, eqpos + 1);
					properties.put(key, StringUtils.replace(data, "\\n", "\n"));
				}
			}
			
			sc.close();
		} catch (IOException e) {
			// handled outside
		}
	}

	public static User parse(String path) {
		return new User(path);
	}

	public boolean matchPassword(String value) {
		return StringUtils.equals((String) properties.get(PASS), value);
	}

	public String getUserName() {
		return file.getName();
	}

	void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(file.getName());
	}
	
	void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		String username = (String) in.readObject();
		
		read(username);
	}
	
	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new ObjectStreamException() {
			private static final long serialVersionUID = 1L;
		};
	}

	public boolean exists() {
		return file.exists();
	}
}
