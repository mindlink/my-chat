package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReportTest {

	private static final String[] sampleConversation1 = {
			"1448470901 mike Hello there!",
			"1448470905 mike how are you?",
			"1448470906 bob I'm good thanks, do you like pie?",
			"1448470910 mike no, let me ask Angus...",
			"1448470912 angus Hell yes! Are we buying some pie?",
			"1448470914 bob No, just want to know if there's anybody else in the pie society..."
	};
	
	private static final String[] sampleConversation2 = {
			"1448470901 Susan Hello there!",
			"1448470905 Gertrude how are you?",
			"1448470906 Gertrude I'm good thanks, do you like pie?",
			"1448470910 Lewis no, let me ask Angus...",
			"1448470912 Gertrude Hell yes! Are we buying some pie?",
			"1448470914 Lewis No, just want to know if there's anybody else in the pie society..."
	};
	
	@Test
	public void testGenerateMostActiveUserReport() {
		Conversation c1 = TestUtilities.getSampleConversation(sampleConversation1);
		Conversation c2 = TestUtilities.getSampleConversation(sampleConversation2);
		
		c1 = Conversation.addReport(c1, Report.getUserReport(c1));
		c2 = Conversation.addReport(c2, Report.getUserReport(c2));
		
		String report1 = c1.getReport().toString();
		String report2 = c2.getReport().toString();

		assertEquals("{mike=3, bob=2, angus=1}", report1);
		assertEquals("{Gertrude=3, Lewis=2, Susan=1}", report2);
	}

}
