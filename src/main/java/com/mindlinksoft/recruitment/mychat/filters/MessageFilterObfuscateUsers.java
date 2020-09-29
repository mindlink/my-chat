package com.mindlinksoft.recruitment.mychat.filters;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public class MessageFilterObfuscateUsers implements MessageFilter {

	private static final Logger LOGGER = Logger.getLogger(MessageFilterObfuscateUsers.class.getName());
	private SecretKeySpec secretKey;

	/**
	 * All user/sender Id's are encrypted. This gives all users privacy and
	 * security, while keeping their Id's unique.
	 */
	@Override
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config) {
		boolean obfuscateUsers = config.isObfuscateUsersOn();
		if (obfuscateUsers) {
			setupEncryptionKey();
			for (Message message : messages) {
				String userId = message.senderId;
				String bytesEncoded = encryptUser(userId);
				message.senderId = new String(bytesEncoded);
			}
		}
		return messages;
	}

	private String encryptUser(String userId) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(userId.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (NoSuchPaddingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (InvalidKeyException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (IllegalBlockSizeException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (BadPaddingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return userId;
	}

	private void setupEncryptionKey() {
		final String defaultKey = "MindLinkSecurity"; // TODO: Change to something more secure.
		try {
			byte[] key = defaultKey.getBytes("UTF-8");
			MessageDigest secureHash = MessageDigest.getInstance("SHA-1");
			key = secureHash.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

}
