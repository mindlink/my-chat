Mindlink Test Chat App
=======================
This application was created by Marcus Watts. It allows users to view messages from conversations with the power to filter messages based on multiple factors. 

Instructions
------------
This command line application enables users to view conversations on the my-chat app. The results of the user search is outputted into json format on the chat.json file. 

There are various filter options which require the user to edit their configurations when running the application. The user must enter four space-separated words, the first two words being whether to search for a specific user, a specific word, to hide words or to have no filter at all. The last two space separated words must be either 'yes' or 'no' answers to the question of whether to, first, 'hide bank card and phone numbers', and second, 'obfuscate user Ids'.

Examples
------------
To filter by:

- username with bank card and phone numbers hidden but usernames not hidden; 
1. run with: 
        [username]<space>[name of user]<space>

2. followed by: 
        [yes]<space>[no]
(i.e [username]<space>[name of user]<space>[yes]<space>[no] )

- specific word with usernames hidden and bank card and phone numbers not hidden;
1. run with: 
        [specific_word]<space>[word_to_be_in_messages]<space>

2. followed by: 
        [no]<space>[yes]
(i.e [specific_word]<space>[pie]<space>[no]<space>[yes] )

- hiding a word or multiple words, with usernames hidden and bank card and phone numbers not hidden;
1. run with: 
        [hide_word]<space>[specific_word1,specific_word2,specific_word3]<space>

2. followed by: 
        [no]<space>[yes]

(i.e [hide_word]<space>[pie,shop]<space>[no]<space>[yes] )          
---------------------------------------------------------

Or, to use no filter;
1. run with:
        [no_filter]<space>[nil]
2. Followed by a yes/no and another yes/no answer (depending oon whether the user wants to hide important numbers or hide user IDs).

