#Usage
####Mandatory parameters:
```java -jar my-chat.jar <input_file_name> <output_file_name>```

####Optional parameters:
```
 [-u -username <the_username>                      ]
 [-k -keyword <the_keyword>                        ]
 [-b -blacklist :<token1> <token2> <token3> ...:   ]
 [-o -obfuscate                                    ]
 [-r -report                                       ]
```

####Explanation of optional parameters:

* The "-username" option will filter out all messages not sent by the specifed user.
* The "-keyword" option will filter out all messages not containing the keyword.
* The "-blacklist" option will remove all occurrences of a given set of tokens from the content of all messages.
* The "-obfuscate" option will encode all occurrences of usernames both in message senderId and content.
* The "-report" option will produce an activity report as an array of usernames and number of message sent for the five most active users (this can be changed by altering a constant value in the ReportFilter.java class).

