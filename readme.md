Programming Exercise - Andrew Zandt-Valentine
====================
my-chat
-------

### How to run the program:
* Load into a java editor (I used Eclipse).
* Run the program from the Main class with the following arguments: *INPUTFILE*.txt *OUTPUTFILE*.json
* Optional arguments can be added on to the compulsory arguments using the following commands:
   * fun-*USERNAME* = Filtering the ouput by a username
   * fkw-*KEYWORD* = Filtering the output with a given keyword
   * blk-*WORDONE,WORDTWO* = Redact any word given. (must be in the format of a comma separated list with no spaces).
   * hcc = Redact credit card numbers (define to be those in the format xxxx-xxxx-xxxx-xxxx)
   * hpn = Redact phone numbers (define to be those in the format +xxxxxxxxxxxx)
   * oun = Apply a ROT13 Encryption to all usernames
* An example of arguments could be: *chat.txt output.json fun-bob fkw-time blk-tree,grass hcc hpn oun*

* A list is also given at the end of the messages detailing the order of the users sending the most messages.

* N.B: I have included some Unit Testing, but did not have time to thoroughly unit test all functions.
* N.B: I didn't have time to fully Javadoc all classes but tried to give comments where possible

### Program Layout:
* Main Classes
   * CommandLineArgumentParser - Deals with the arguments input by the user.
   * ConversationExporter - Key class of the program, takes in command-line arguments and creates a JSON output
   * ConversationReader - Takes an input file string and returns a conversation which has any optional arguments applied
   * ConversationWriter - Writes a Conversation to a json file
   * Main - The Main class containing the main function, creates a ConversationExporter and passes command-line arguments
   * MessageFilter - Class containing the redaction functions which are applied to messages
   * OptionalArgumentManager - Decides which optional arguments to apply to a message
   * OptionalArgumentValidator - Validates that the optional arguments given by the user have been entered correctly.
   
* Model Classes
   * Conversation - Model class for a Conversation.
   * ConversationExporterConfiguration - Model class to store command-line inputs
   * Message - Model class for a message
   * OptionalArguments - Model class storing which optional arguments the user has given
   
* Helper Classes
   * ConversationAndJsonConverter - Helper class to build a GsonBuilder and convert between a Conversation and JSON.
   * MapToStringConverter - Converts a map containing usernames and the number of messages they've sent, sorts it and outputs it as a string.
