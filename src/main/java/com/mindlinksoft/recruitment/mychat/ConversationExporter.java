package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

  protected static GsonBuilder gsonBuilder = new GsonBuilder();

  /**
   * The application entry point.
   *
   * @param args The command line arguments.
   * @throws Exception Thrown when something bad happens.
   */
  public static void main(String[] args) throws Exception {
    /**
     * List of args
     *  -i, --source-file <filePath>
     *  -o, --destination-file <filePath>
     *  -x, --exclude-id <senderId>
     *  -k, --filter-by <keyword>
     *  -b, --blacklist <keyword_1|...|keyword_n>
     */

    ConversationExporter exporter = new ConversationExporter();
    ConversationExporterConfiguration configuration = null;
    try {
      configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    } catch (IllegalArgumentException e) {

      System.out.println("Invalid parameters!\n" +
          "Usage: java ConversationExporter.class <args>\n\n\t" +
          "[-i, --source-file] <source file> " +
          "[-o, --destination-file] <destination file> " +
            "[[-x, --exclude-id] <senderId> " +
            "[-k, --filter-by] <keyword> " +
            "[-b, --blacklist] <keyword_1|...|keyword_n> " +
            "[-h, --hide-sensible-numbers] " +
            "[-s, --obfuscate-ids]]");
      System.exit(-1);
    }

    exporter.exportConversation(configuration);
  }

  /**
   * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
   *
   * @param configuration The application's configuration set by the command arguments
   * @throws Exception Thrown when something bad happens.
   */
  public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
    Conversation conversation = this.readConversation(configuration);

    if (conversation == null) {
      System.err.println("An error has occurred while reading the input file");
    }
    this.writeConversation(conversation, configuration.getOutputFilePath());
    this.writeReport(configuration.getOutputFilePath());

    // TODO: Add more logging...
    System.out.println("Conversation exported from '" + configuration.getInputFilePath() + "' to '" + configuration.getOutputFilePath() + "'");
  }

  public void writeReport(String outputFilePath) throws Exception {
    JsonParser jsonParser = new JsonParser();

    try {
      JsonObject chat = (JsonObject) jsonParser.parse(new FileReader(outputFilePath));
      Gson g = new Gson();
      JsonElement jsonElement = g.toJsonTree(ReportManager.sharedInstance().getReport());
      chat.add("Report", jsonElement);

      OutputStream os = new FileOutputStream(outputFilePath, false);
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

      bw.write(g.toJson(chat));
      bw.flush();
      bw.close();

    } catch (FileNotFoundException e) {
      System.err.println("The output file was not found");
    } catch (IOException e) {
      System.err.print("An error occurred while writing JSON data to the file");
    }
  }

  /**
   * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
   *
   * @param conversation   The conversation to write.
   * @param outputFilePath The file path where the conversation should be written.
   * @throws Exception Thrown when something bad happens.
   */
  public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
    // TODO: Do we need both to be resources, or will buffered writer close the stream?
    try {
      OutputStream os = new FileOutputStream(outputFilePath, true);
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

      // TODO: Maybe reuse this? Make it more testable...
      // GsonBuilder gsonBuilder = new GsonBuilder();
      // gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

      Gson g = gsonBuilder.create();

      bw.write(g.toJson(conversation));
      bw.flush();
      bw.close();
    } catch (FileNotFoundException e) {
      // TODO: Maybe include more information?
      System.err.println("The output file was not found");
    } catch (IOException e) {
      // TODO: Should probably throw different exception to be more meaningful :/
      System.err.print("An error occurred while writing JSON data to the file");
    }
  }

  /**
   * Represents a helper to read a conversation from the given {@code inputFilePath}.
   *
   * @param configuration The application's configuration set by the command arguments.
   * @return The {@link Conversation} representing by the input file.
   * @throws Exception Thrown when something bad happens.
   */
  public Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception {

    ReportManager reportManager = ReportManager.sharedInstance();

    String excludedSenderID = configuration.getExcludedSenderID();
    String filteringKeyword = configuration.getFilteringKeyword();
    String blacklist = configuration.getBlacklist();
    String hideNumbers = configuration.getParameterFromFlag("-h");
    String obfuscateIds = configuration.getParameterFromFlag("-s");

    try {
      InputStream is = new FileInputStream(configuration.getInputFilePath());
      BufferedReader r = new BufferedReader(new InputStreamReader(is));

      List<Message> messages = new ArrayList<>();

      String conversationName = r.readLine();
      String line;

      while ((line = r.readLine()) != null) {
        String timestamp = line.split(" ")[0];
        String senderID = line.split(" ")[1];
        String content= line.split(" ", 3)[2];

        // Excluded sender ID has priority over filtering keywords
        if (excludedSenderID != null && excludedSenderID.equalsIgnoreCase(senderID)) {
          continue;
        }
        if (filteringKeyword != null && !content.contains(filteringKeyword)) {
          continue;
        }
        if (blacklist != null) {

          Pattern regex = Pattern.compile(blacklist);
          Matcher matcher = regex.matcher(content);
          if (matcher.find()) {
            content = matcher.replaceAll("*redacted*");
          }
        }
        if (obfuscateIds != null) {
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(senderID.getBytes());
          byte[] digestedSenderID = md.digest();
          senderID = "";
          for (byte b : digestedSenderID) {
            senderID += String.format("%02x", b & 0xff);
          }
        }
        if (hideNumbers != null) {
          String pattern = "\\d{16}|\\d{5} \\d{6}|\\d{11}";
          Pattern regex = Pattern.compile(pattern);
          Matcher matcher = regex.matcher(content);
          if (matcher.find()) {
            content = matcher.replaceAll("*redacted*");
          }
        }

        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), senderID, content));
        reportManager.addToReport(senderID);
      }

      r.close();
      is.close();

      return new Conversation(conversationName, messages);
    } catch (FileNotFoundException e) {
      System.err.println("The output file was not found");
      return null;
    } catch (IOException e) {
      System.err.println("An error occurred while reading the input file");
      return null;
    }
  }

  class InstantSerializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
      return new JsonPrimitive(instant.getEpochSecond());
    }
  }
}
