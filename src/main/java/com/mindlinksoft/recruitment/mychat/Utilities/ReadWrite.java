package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReadWrite {
    public Conversation readConversation(String inputFilePath) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

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

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Input file path argument '" + inputFilePath + "' was not found.");
        } catch (IOException e) {
            throw new IOException("Could not read this file. Cause: " + e.getCause());
        }
    }

    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable... // DONE
            BuildCreateGson gson = new BuildCreateGson();
            bw.write(gson.g.toJson(conversation));

        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information? // DONE
            throw new IllegalArgumentException("Output file path argument '" + outputFilePath + "' was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/ // DONE
            throw new IOException("Could not write this file. Cause: " + e.getCause());
        }
    }
}
