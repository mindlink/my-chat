My Chat
====================

----
## Conversation to JSON Exporter

An easy to use exporter that converts a conversation file into JSON. The exporter can also help you filter the conversation to find specific messages either by keyword or user.


----
## About

### What is a conversation file?
A conversation file contains a single conversation thread, i.e. a series of messages from different users.

*Get a conversation into the following format from your favourite chat service.*

    <conversation_name><new_line>
    (<unix_timestamp><space><username><space><message><new_line>)*


*...for example...*  

    My Conversation
    1448470901 bob Hello there!
    1448470905 mike how are you?
    1448470906 bob I'm good thanks, do you like pie?
    1448470910 mike no, let me ask Angus...
    1448470912 angus Hell yes! Are we buying some pie?
    1448470914 bob No, just want to know if there's anybody else in the pie society...
    1448470915 angus YES! I'm the head pie eater there...

---
### What does it output?
The exporter will output a JSON object representing the conversation. You can choose what messages to export as part of the configuration.

*Example output*

    {
        "name":"My Conversation",
        "messages":
            [
            {"timestamp":1448470901,"senderId":"bob","content":"Hello there!"},
            {"timestamp":1448470905,"senderId":"mike","content":"how are you?"},
            {"timestamp":1448470906,"senderId":"bob","content":"I\u0027m good thanks, do you like pie?"},
            {"timestamp":1448470910,"senderId":"mike","content":"no, let me ask Angus..."},
            {"timestamp":1448470912,"senderId":"angus","content":"Hell yes! Are we buying some pie?"},
            {"timestamp":1448470914,"senderId":"bob","content":"No, just want to know if there\u0027s anybody else in the pie society..."},
            {"timestamp":1448470915,"senderId":"angus","content":"YES! I\u0027m the head pie eater there..."}
            ]
    }


----
## How to Use

Simply run the exporter and configure the options via the command line.

**Options**

    -i    Input - path to the conversation file (Required)
    -o    Output - path to where you want the exporter to create the JSON file. (Required)
    -u    User - only return messages sent by this user
    -k    Keyword - only return messages with this keyword 
    -bl   Blacklist - comma separated list of words or phrases to redact within the conversation.

---
**Example: Basic use**

    java mychat  -i conversation.txt  -o conversation.json

---
**Example: Get all Bobs messages**

    java mychat  -i conversation.txt  -o conversation.json  -u Bob

---
**Example: Get all messages with the word 'pie'**

    java mychat  -i conversation.txt  -o conversation.json  -k cat

---
**Example: Redact any occurrences of the words 'cat' or 'squirrel'**

    java mychat  -i conversation.txt  -o conversation.json  -bl "cat, squirrel"

---
*Mix and match these options to retrieve the conversation that you want.*


----
## Coming Soon

The following features are next in line to be added to the exporter.

* Hide credit card and phone numbers
* Obfuscate user IDs
* User metric reports

---
**Need more power?**  
Checkout [MindLink](http://www.mindlinksoft.com/) for a full chat enabled collaboration tool.
