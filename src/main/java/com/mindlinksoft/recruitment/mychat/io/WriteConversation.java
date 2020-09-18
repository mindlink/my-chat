package com.mindlinksoft.recruitment.mychat.io;

import com.mindlinksoft.recruitment.mychat.exceptions.IOProblemException;
import com.mindlinksoft.recruitment.mychat.exceptions.WrongArgumentsException;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.utils.GsonConverter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class WriteConversation {

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @throws Exception Thrown when something bad happens.
     */
    public static void write(Conversation conversation, ConversationExporterConfiguration configuration) throws WrongArgumentsException, IOProblemException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(configuration.outputFilePath))) {
            bw.write(GsonConverter.buildGsonSerializer().toJson(conversation));
        } catch (FileNotFoundException e) {
            throw new WrongArgumentsException();
        } catch (IOException e) {
            throw new IOProblemException(e.getMessage(), e.getCause());
        }
    }
}
