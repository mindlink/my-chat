package com.mindlinksoft.recruitment.mychat.conversation.exporter;

import com.mindlinksoft.recruitment.mychat.conversation.IConversation;
import com.mindlinksoft.recruitment.mychat.conversation.IConversationFormatter;
import com.mindlinksoft.recruitment.mychat.conversation.serialization.ISerializer;

import java.io.*;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

	private final ISerializer serializer;
	
	public ConversationExporter(ISerializer serializer) {
		this.serializer = serializer;
	}

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws IllegalArgumentException If the output path is invalid.
     * @throws IOException If it fails to write to the output file.
     * 
     */
    public void exportConversation(IConversation conversation, String outputFilePath, IConversationFormatter formatter) throws IllegalArgumentException, IOException {
        //Apply formatting to the conversation
        conversation = formatter.format(conversation);
        
        this.writeConversation(conversation, outputFilePath);

        System.out.println("Exported conversation to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IllegalArgumentException If the output path is invalid.
     * @throws IOException If it fails to write to the output file.
     * 
     */
    public void writeConversation(IConversation conversation, String outputFilePath) throws IllegalArgumentException, IOException {
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
        	bw.write(serializer.serialize(conversation));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid output path: " + outputFilePath, e);
        } catch (IOException e) {
            throw new IOException("Failed to write to output file.", e);
        }
    }

}
