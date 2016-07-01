Programming Exercise
====================
Asmaa Ahmed - Submitted in fulfilment of MindLink Programming exercise 

Example of uses:
* Conversation Exporting 

		java mychat  -in chat.txt  -out chat2.json
	
* Filtering

	** By user 
	
		java mychat  -in chat.txt  -out chat2.json -u "angus"
		
	** By Keyword
	
		java mychat  -in chat.txt  -out chat2.json -k "pie"
		
* Blacklisting

		java mychat  -in chat.txt  -out chat2.json -bl "Hello, there"
	
----------------------------------------------------------------------------

Test package:

	this package includes the JUnit testing of the main requirements of the program which consist of:
	
		1. Conversation exporting 
		
		2. Word blacklisting 
		
		3. Conversation Filtering 
		
		4. File input reader and output writer 

		
[NOTE: The project was cloned before clicking Fork on Github. This caused an issue when trying to Push the project into Github. Therefore, I went back and Forked the project then copy and pasted the changes I have previously made.]