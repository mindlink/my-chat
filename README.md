# Basil Regi - Coding Test Submission
## How to use
The following application was implemented as part of the interview process for MindLink.

The ConversationExporter can be used as a library in a larger application and offers the following functions:
* `export(ConfigurationExporterConfiguration config)` - Function to export conversation using given config
* `readConversation(String inputFilePath)` - Function to read in conversation given an input file path
* `writeConversation(Conversation convo, String outputFilePath)` - Function to write conversation to output file path
* `applyFeatures(ConversationExporterConfiguration config, Conversation convo)` - Function to apply the features given in a config object to a conversation

Here is an example of how to use the library:

`ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);`
`ConversationExporter.exportConversation(configuration);`

As part of this implementation I have also included MyChatCLI which allows you to use the ConversationExporter functionality through the command line. This can be done in one of two ways:
* Arguments provided when running the file:
`java ConversationExporter <input-file> <output-file> -u=bob -b=Hello,pie,yes -h`

* Running the program and providing input during runtime
** This allows the user to specify various pipleines of features/configurations to export in various formats
** Stops collecting user input when empty input/configuration is given
Example Run:
`>> Enter input file path 1: chat.txt`
`>> Enter configuration 1: -h`
`>> Enter output file path 1: chat1.json`
`>> Enter configuration 2: -h -o`
`>> Enter output file path 2: chat2.json`
`>> Enter configuration 3: -u=bob`
`>> Enter output file path 3: chat3.json`
`>> Enter configuration 4:`
`>> Enter input file path 2:` 

# Flags:
-u Filter messages by a specific user
-k Filter messages by specific keyword
-b Redact blacklisted words in messages - blacklist provided as a comma delimited list
-o Obfuscate user ids
-h Redact phone and credit card numbers (specific formats)

User activity report is always included.
