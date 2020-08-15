Programming Exercise
====================

This is a skeleton application to be used as part of a software development interview.

Build instructions
------------

Ensure terminal/command prompt is at the root of this directory.
```
mvn verify
```

Run instructions
------------
From the root of this directory:
```
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar [inputFilePath] [outputFilePath] [flag] [users/keywords]
```
E.g simple configuration:
```
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar chat.txt chat.json
```
E.g filter sender bob
```
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar chat.txt chat.json -fu bob
```
Flags:
```
Filter users
-fu [users]
Filter key words
-fw [keyWords]
Hide key words
-hw [keyWords]
Hide phone/credit card numbers
-hn
Obfuscate users
-ob
```
my-chat
-------

### Features Completed

* Messages can be filtered by a specific user
* Messages can be filtered by a specific keyword
* Hide specific words
* Hide credit card and phone numbers
* Obfuscate user IDs

### Feature in progress
* A report is added to the conversation that details the most active users
Currently, the report is always appended to the conversation, but is not sorted, and instead lists all users mapped to their frequency in random order.
