Author: MindLink Software 
Contributors: Carl Visser

Date: 19 January 2016

Name: myChat

Description: 

Program which takes in a text file of a conversation and filters it to desired effect 
depending on the identifiers before saving it as a .json object file.

SetUp:
In an IED (eg. Netbeans):
1. Import project using git/gitHub
2. Input arguments through menu options
    eg Netbeans, Project Properties->RunNode->Arguments
3. Run project.
 
Using the Command Line (Windows) or Terminal (Linux/Mac):

1. Navigate to the mychat directory.
2. Type in make command.

Help Instructions:

General argument format:
-----------------------------
[file.txt]
[identifiers] [file.txt] *[filtervalues]
Identifier Options: [u|k|h]
-----------------------------
u - Filter conversation as per user/s
k - Filter conversation as per keywords
h -  Retract or hide words in the conversations
Identifier Format: [di|di|di] 
------------------------------
d - placeholder for a digit from 1-9
i - placeholder for Identifier option
*The digit before the identifier indicates how many options are intended
*The ordering of the identifiers and coresponding digits indicates the order
of the arguments
Example
------------------------------
1k2h3u chat.txt keyword1 hiddenword1 hiddenword2 username1 username2 username3";

List of files: 
Package: com.mindlink.recruitment.mychat 
Sources: 
CommandLineArgumentParser.java, Conversation.java, ConversationExporter.java, 
ConversationExporterConfiguration.java, Message.java, Filter.java, Writers.java
Test: 
CommandLineArgumentParserTest.java, ConversationExporterTest.java, FilterTest.java
TesterData.java, WritersTest.java

Other Files: 
.gitattributes, .gitignore, chat.txt, pom.xml, readme.md 