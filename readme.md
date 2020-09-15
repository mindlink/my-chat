Note: Thanks for the opportunity to interview, I hope to contribute to your company in the near future. 
I have done the essential features and the obfuscate and report additional features too.
That said, I believe there are ways to improve my submission and I’m looking forward to ear them! 

I decided to send the informations in the ‘Program Arguments’:
<chat.txt path> <chat.json path> <command> <feature>

Essential Features:
•	Filter by a specific user:
- user:specificSenderId
- example: <chat.txt path> <chat.json path> <user:bob> <null>

•	Filter by specific word:
- word:specificWord
- example: <chat.txt path> <chat.json path> <word:pie> <null>

•	Hide specific words:
- redact:specificWord
- example: <chat.txt path> <chat.json path> <redact:pie> <null>

Additional Features:
•	Obfuscate user Ids:
-  example: <chat.txt path> <chat.json path> <null> <obfuscate>
•	Report added to the conversation including number of messages each user sent:
-  example: <chat.txt path> <chat.json path> <null> <report>

Note: It is possible to send either a command and a feature.

