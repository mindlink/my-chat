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
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar [inputFilePath] [outputFilePath] ([modifier] [sub-modifier]*)*
```
Simple configuration:
```
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar chat.txt chat.json
```
E.g. filter sender bob
```
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar chat.txt chat.json -fu bob
```
E.g. filter sender bob and angus, hide words pie and there, obfuscate users and attach a report. Note: order of arguments does not matter.
```
java -jar target\my-chat-1.0-SNAPSHOT-jar-with-dependencies.jar chat.txt chat.json -ob -hw pie there -rp -fu bob angus
```
### Flags
Filter users
```
-fu [user]+
```
Filter key words
```
-fw [keyWord]+
```
Hide key words
```
-hw [keyWord]+
```
Hide phone/credit card numbers
```
-hn
```
Obfuscate users
```
-ob
```
Report most active users
```
-rp
```
my-chat
-------

### Features Completed

* Messages can be filtered by a specific user
* Messages can be filtered by a specific keyword
* Hide specific words
* Hide credit card and phone numbers
* Obfuscate user IDs
* Report most active users, sorted in order of message count

### Changelog
* Parser now allows for multiple features to be applied at a time 
* Features are now applied at a specific order, regardless of user input. The order is [Filter, Hide, Obfuscate, Report]. 
* Single abstraction for modifiers: ModifierBase
* Parser now checks for illegal arguments including missing sub modifiers, incorrect arguments etc. 
* Counting senders is now part of a separate “Report” feature, remove peek() from Reader 
* Message.parseLine has been moved to Reader class from Message class 
* Added logger to Main, Exporter, Reader and Writer
