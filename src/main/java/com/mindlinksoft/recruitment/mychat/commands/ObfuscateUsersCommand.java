package com.mindlinksoft.recruitment.mychat.commands;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

public class ObfuscateUsersCommand implements IConversationExportCommand {
	
	private String algorithm = "MD5";

	@Override
	public Conversation doCommand(Conversation conversation) throws ExportCommandException {
		Collection<Message> allMessages = conversation.getMessages();
		Collection<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message msg : allMessages) {
			String obfuscatedId = obfuscate(msg.getSenderId());
			filteredMessages.add(new Message(msg.getTimestamp(), obfuscatedId, msg.getContent()));
		}
		
		return new Conversation(conversation.getName(), filteredMessages);
	}
	
	private String obfuscate(String name) throws ExportCommandException {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] mdBytes = md.digest(name.getBytes());
			BigInteger no = new BigInteger(1, mdBytes);
			String hashtext = no.toString(16); //convert to hex 
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new ExportCommandException(this, "Internal problem obfuscating usernames");
		}
	}

}
