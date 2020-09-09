package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReadWrite {
    public ConversationDefault readConversation(String inputFilePath) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                StringBuilder content = new StringBuilder();

                for (int i = 0; i < split.length; i++) {
                    if (i == 2) {
                        content.append(split[i]);
                    }
                    if (i > 2) {
                        content.append(" ").append(split[i]);
                    }
                }
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content.toString()));
            }

            return new ConversationDefault(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Input file path argument: '" + inputFilePath + "' was not found. Cause:" + e.getCause());
        } catch (IOException e) {
            throw new IOException("Could not read this file. Cause: " + e.getCause());
        }
    }

    public void writeConversation(Object conversation, String outputFilePath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            BuildCreateGson buildCreateGson = new BuildCreateGson();
            bw.write(buildCreateGson.convert(conversation));

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Output file path argument: '" + outputFilePath + "' was not found. Cause:" + e.getCause());
        } catch (IOException e) {
            throw new IOException("Could not write this file. Cause: " + e.getCause());
        }
    }
}
