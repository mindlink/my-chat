package com.mindlinksoft.recruitment.mychat;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.StringJoiner;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
  /**
   * Parses the given {@code arguments} into the exporter configuration.
   *
   * @param arguments The command line arguments.
   * @return The exporter configuration representing the command line arguments.
   */
  public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    HashMap<String, String> config = validateArguments(arguments);

    if (config == null) throw new IllegalArgumentException();

    return new ConversationExporterConfiguration(config);
  }

  private HashMap<String, String> validateArguments(String[] args) {

    HashMap<String, String> configuration = new HashMap<>();

    // Essential Features -  Odd number of arguments = incorrect set of arguments.
    //if (args.length % 2 != 0) return null;

    for(int i = 0; i < args.length; ++i) {
      String flag = args[i];
      String value = "";

      switch (flag) {
        case "-i":
        case "--source-file":
          flag = "-i";
          if (!args[i + 1].startsWith("-")) value = args[++i];
          else return null;
          break;

        case "-o":
        case "--destination-file":
          flag = "-o";
          if (!args[i + 1].startsWith("-")) value = args[++i];
          else return null;
          value = value.replace(".", "_" + (new Date().getTime()) + ".");
          break;

        case "-x":
        case "--exclude-id":
          flag = "-x";
          if (!args[i + 1].startsWith("-")) value = args[++i];
          else return null;
          break;

        case "-k":
        case "--filter-by":
          flag = "-k";
          if (!args[i + 1].startsWith("-")) value = args[++i];
          else return null;
          break;

        case "-b":
        case "--blacklist":
          flag = "-b";
          if (!args[i + 1].startsWith("-")) value = args[++i];
          else return null;
          break;

        case "-h":
        case "--hide-sensible-numbers":
          flag = "-h";
          value = "true";
          break;

        case "-s":
        case "--obfuscate-ids":
          flag = "-s";
          value = "true";
          break;

        default: break;
      }
      configuration.put(flag, value);
    }
    return configuration;
  }
}
