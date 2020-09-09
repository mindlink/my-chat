# my-chat
Expanded on a skeleton application used as part of a software development interview.
## Usage
### Initially, a choice of one of these functions:
- Export with given paths (default feature):
```
chat.txt chat.json
```
- Filter messages by user name:
```
chat.txt chat.json -name bob
```
- Filter messages by a specific keyword:
```
chat.txt chat.json -keyword pie
```
- Hide specific keywords:
```
chat.txt chat.json -hide pie,hello,there
```
### Any of these functions can be followed up by the following flags:
(each flag can be used and in any order)
```
-details -obf -report
```
An example:
```
chat.txt chat.json -hide pie,hello,there -obf -report -details
```
Or:
```
chat.txt chat.json -obf
```
### Featured Implementations
- The decide() method in ConversationExporter class was implemented as a solution to successfully parse arguments (flags for additional features) whilst maintaining the correct processing order. The report has to be executed last as it uses existing information to generate a new section in the JSON file.
- The obfuscation process produces a "users.txt" with their the senders actual ID and 5 digit randomly generated ID. This could be used for admin purposes.
- The activity reporting process uses a new object class ConversationReport that contains a new User section. This section contains each unique sender with their counted activity in descending order.
- Regular expressions used for finding mobile phone numbers and credit card details. 
- The CommandLineArgumentParser design, all flags are obtained but optimally not all are considered. The program will detect declared flags and ignore the rest. This means that errors cannot be thrown unless the input path and output path are incorrect.

### Author
Alex Sikorski
