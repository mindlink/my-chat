# Programming Exercise

Author: Jimmy Fidiles

## Instructions

The command line arguments are build in such way that it can have different parameters from two to 7. If we have less than two it'll throw a custom Exception (InvalidArgumentsException).

Full command line arguments to include all filtres, blacklist and flags: "chat.txt output.json username=bob keyword=you blacklist=pie hide-cc hide-id include-report" where:
	- chat.txt: input file
	- output.json: output file
	- username=bob: username - filter, bob - value
	- keyword=you: keyword - filter, you - value
	- blacklist=pie: blacklist - flag to replace, pie - value to replace
	- hide-cc: flag to hide credit card
	- hide-id: flag to obfuscate
	- include-report: flag to include report

## Comments:
    - Obfuscation with md5 and a salt
    - Optimisation for GsonBuilder to be more testable
    - Completed all tasks including tests for all tasks
