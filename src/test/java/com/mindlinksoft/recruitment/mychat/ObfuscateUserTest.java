package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import com.mindlinksoft.recruitment.mychat.filter.ObfuscateUser;

/**
 * This class tests the obfuscation of user names.
 * 
 * @author Mohamed Yusuf
 *
 */
public class ObfuscateUserTest {

	@Test
	public void testObfuscateUser() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {};
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setUsersToObfuscate(filter);

		convo.addAll(new ObfuscateUser().filter(new GenerateMockMessages().genMockMessages(), config));
		
		for(Message mess : convo) {	
			//System.out.println("Original: " + mess.getUserObject().getOrigianlUsername());
			//System.out.println("Obfuscated: " + mess.getUsername());
			assertNotEquals(mess.getUsername(), mess.getUserObject().getOrigianlUsername());
		}
	}
}
