package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

class ConversationReader extends BufferedReader {

	public ConversationReader(Reader in) {
		super(in);
		
	}

	public ConversationReader(Reader in, int sz) {
		super(in);
		
	}
	
	Conversation readConversation() {
		return null;
	}

}
