package cz.deznekcz.games.panjeskyne.user;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class User {

	private static final String PASS = "pass";

	private static final Object USERNAME = null;
	
	private Properties properties;

	private File file;

	public User(String path) {
		properties = new Properties();
		file = new File(path);
		
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

}
