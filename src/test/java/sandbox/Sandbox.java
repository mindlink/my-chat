package sandbox;

import java.util.HashMap;
import java.util.Map;

public class Sandbox {

	public static void main(String[] args) {
		String username = "bob";
		int hash = username.hashCode();

		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put(username, "'userId" + hash + "'");
		
		username = "angus";
		hash = username.hashCode();
		
		map.put(username, "'userId" + hash + "'");
		
		username = "mike";
		hash = username.hashCode();
		
		map.put(username, "'userId" + hash + "'");
		
		for(Object entry : map.entrySet().toArray()) {
			System.out.println(entry.toString());
		}
	}

}
