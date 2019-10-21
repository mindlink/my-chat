package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.optionClasses.ChatOption;
import java.io.*;
import java.time.Instant;
import java.util.*;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws IOException Thrown when an error occurs whilst writing/reading to output/input file, respectively.
     *
     * FORMAT:
     * mychat InputFile.txt OutputFile.JSON [OPTIONS]
     *
     * OPTIONS:
     * -u, Filter results by user.     Example: 'mychat InputFile.txt OutputFile.JSON -u=charlie'
     * -k, Filter results by keyword.  Example: 'mychat InputFile.txt OutputFile.JSON -k=cake'
     * -b, Hide word from results.     Example: 'mychat InputFile.txt OutputFile.JSON -b=cheese'
     * -c, Hide card/phone numbers.    Example: 'mychat InputFile.txt OutputFile.JSON -c'
     * -o, Obfuscate user IDs.         Example: 'mychat InputFile.txt OutputFile.JSON -o'
     * -a, Include active user report. Example: 'mychat InputFile.txt OutputFile.JSON -a'
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.options);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @param options Commandline options.
     * @throws IOException Thrown when an error occurs whilst writing/reading to output/input file, respectively.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, ArrayList<ChatOption> options) throws IOException {
        //instantiate conversation from input path file
        Conversation conversation = this.readConversation(inputFilePath);

        System.out.println("Conversation imported from '" + inputFilePath + "'");

        //apply options to conversation in the order in which they were given on the commandline
        //use an iterator to apply each option to each message so that messages can be removed in place where appropriate
        Collection<Message> messages = conversation.messages;
        Iterator<Message> messageIterator = messages.iterator();

        while (messageIterator.hasNext()) {
            Message currentMessage = messageIterator.next();
            for (ChatOption option : options) {
                currentMessage = option.applyDuring(currentMessage);

                //if the current option has removed this message (by returning null), remove the message from the
                //conversation and move on to the next message
                if (currentMessage == null) {
                    messageIterator.remove();
                    break;
                }
            }
        }

        //invoke methods for options that require action after message processing has occurred
        for (ChatOption option : options) {
            conversation = option.applyAfter(conversation);
        }

        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation exported to '" + outputFilePath + "'");
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IOException Thrown error occurs whilst writing to output file.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws IOException {
        System.out.println("Exporting conversation...");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            bw.write(JSONConverter.convertToJSON(conversation));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Couldn't find output file at '" + outputFilePath + "'");
        } catch (IOException e) {
            throw new IOException("Error occurred whilst writing to '" + outputFilePath + "'");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws IOException Thrown error occurs whilst reading from input file.
     */
    public Conversation readConversation(String inputFilePath) throws IOException {
        System.out.println("Importing conversation...");

        try (BufferedReader r = new BufferedReader( new FileReader(inputFilePath) )){

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                String messageContent = String.join(" ", Arrays.copyOfRange(split, 2, split.length));

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], messageContent));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Couldn't find input file at '" + inputFilePath + "'");
        } catch (IOException e) {
            throw new IOException("Error occurred whilst reading from '" + inputFilePath + "'");
        }
    }
}
