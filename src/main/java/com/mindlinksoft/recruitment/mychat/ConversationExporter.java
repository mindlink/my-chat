package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.chatFeatures.ChatFeature;

import java.io.*;
import java.time.Instant;
import java.util.*;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 *
 * Input Format: InputFilePath.txt OutputFilePath.JSON <Arguments>
 *
 * Arguments:
 * -i file - Input file path.
 * -o file - Output file path.
 * -fk keyword - Filter results by keyword.
 * -fu userID - Filter results by userID.
 * -sbl blacklist - Sanitize results to redact words in blacklist.
 * -spc - Sanitize results by redacting phone or bank card numbers.
 * -suid - Sanitize results by obfuscating userIDs.
 * -uar - Appends a report of user activity to the exported chat log.
 */
class ConversationExporter {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws IOException Thrown when an I/O error occurs on file read/write.
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @param arguments      Command-line arguments
     * @throws IOException Thrown when an I/O error occurs on file read/write.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, ArrayList<ChatFeature> arguments) throws IOException {
        Conversation conversation = this.readConversation(inputFilePath);
        System.out.println("Conversation imported from file at: " + inputFilePath);

        Collection<Message> messages = conversation.messages;
        Iterator<Message> messagesIterator = messages.iterator();
        if(!arguments.isEmpty()) {
            while (messagesIterator.hasNext()) {
                for (ChatFeature argument : arguments) {
                    Message message = messagesIterator.next();

                    if (argument.beforeExport(message) != null) {
                        message = argument.beforeExport(message);
                    } else {
                        messagesIterator.remove();
                    }


                }

            }

            for (ChatFeature argument : arguments) {
                argument.duringExport(conversation);
            }
        }
        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'");
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IOException Thrown when an I/O error occurs on file read/write.
     */
    private void writeConversation(Conversation conversation, String outputFilePath) throws IOException {
        System.out.println("Writing conversation to JSON.");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath))){
            bufferedWriter.write(JSONHandler.toJSON(conversation));
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("No output file found at: " + outputFilePath);
        } catch (IOException e) {
            throw new IOException("I/O Error occurred writing to: " + outputFilePath);
        }

        System.out.println("Conversation written to JSON.");
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    private Conversation readConversation(String inputFilePath) throws IOException {
        try (InputStream fileInputStream = new FileInputStream(inputFilePath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = bufferedReader.readLine();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");

                String messageBody = String.join(" ", Arrays.copyOfRange(split, 2, split.length));

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], messageBody));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No input file found at:" + inputFilePath);
        } catch (IOException e) {
            throw new IOException("I/O error occurred reading from: " + inputFilePath);
        }
    }
}
