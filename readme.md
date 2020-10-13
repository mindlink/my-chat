Programming Exercise
====================

This repository contains a skeleton application to be used as part of the MindLink software development interview.

Application
-----------

### Overview

This application is meant to read a conversation from a plain text file and output the conversation as JSON.

We are looking for the application to be fixed so that it works as intended and extended to support the functionality described below.

### Features

* A user can export a conversation from a given file path stored in the following file format into a JSON file at the given output path:
```
<conversation_name><new_line>
(<unix_timestamp><space><username><space><message><new_line>)*
```
* Messages can be filtered by a specific user
    * The user can be provided as a command-line argument `--filterByUser=<user>`
    * All messages sent by the specified user appear in the JSON output
    * Messages sent by any other user do not appear in the JSON output
* Messages can be filtered by a specific keyword
    * The keyword can be specified as a command-line argument `--filterByKeyword=<keyword>`
    * All messages sent containing the keyword appear in the JSON output
    * Messages sent that do not contain the keyword do not appear in the JSON output
* Hide specific words
    * A blacklist can be specified as a command-line argument `--blacklist=<word1> --blacklist=<word2>`
    * Any blacklisted word is replaced with "\*redacted\*" in the JSON output.
* Include a report of the number of messages each user sent
    * Adding the report to the output can be specified as a command-line argument `--report`
    * The report is sorted in descending order of number of messages sent
    * The report should appear in the final JSON output under the `activity` property:
    ```
    {
     "name": "...",
     "messages": [...],
     "activity": [
        {
            "sender": "...",
            "count": 100
        },
        ...]
	}
    ```

### Building and running

* This solution uses maven (https://maven.apache.org/users/index.html), feel free to change it to a different build solution.

* Command line with the Java SDK and Maven
    - To build everything
        - `./build.cmd` from within the solution directory
        - OR `mvn package` from within the solution directory
    - To run the application
        - `./run.cmd` from within the solution directory
        - OR `java -jar target/my-chat-1.0-SNAPSHOT.jar -i <input> -o <output>` from within the solution directory
    - To run the unit tests
        - `./test.cmd` from within the solution directory
        - OR `mvn clean test` from within the solution directory

* IntelliJ Idea should detect the maven project and work as-is

### Dependencies

* picocli (https://picocli.info/) - used to parse the command line
* Google gson (https://github.com/google/gson) - used to read and write JSON
* JUnit 4 (https://junit.org/junit4/) - used for unit testing

Instructions
------------

1. Think about your architecture, maybe even sketch out a UML diagram!

2. Fork the repository into your own GitHub area

3. Install the Java 8 (or above) SDK from https://www.oracle.com/java/technologies/javase-downloads.html or if you prefer you can download the Open JDK from https://developers.redhat.com/products/openjdk/download

4. Download Maven from https://maven.apache.org/users/index.html, extract it somewhere and add it to your environment `PATH` for easy access (follow the maven usage instructions)

5. Identify and fix the existing issue

6. Make whatever changes you feel are necessary, it's your code now!

7. Make sure all features are implemented, the code works and all the tests you wrote pass!

8. When you are satisfied, submit back via a pull request. See details on GitHub's [Fork & Pull](https://help.github.com/articles/using-pull-requests) model

9. Notify our recruitment team at `careers <at> mindlinksoft.com` with a link to your pull request and your real name ;)

### What we are looking for

* Application of SOLID principles - https://en.wikipedia.org/wiki/SOLID
    * Imagine that there are another 20 features to implement, design with that in mind!
    * DON'T put everything in one method/one class
    * DO think about **abstractions** and commonality between features
    * DO think about separating concerns (IO vs business logic vs configuration and command line)

* A well-formed exception model
    * DON'T `throw new Exception("non-descript message here")`
    * DO throw appropriate exceptions e.g. is an argument `null` and shouldn't be? `IllegalArgumentException` then!
    * DO create and throw your own exception types **when it makes sense**
    * DO include inner exceptions, otherwise you lose your stack!

* Tidy code
    * Well named variables and methods
    * Consistent style

* Application of appropriate design patterns
    * There is scope to approach this challenge in different ways and design patterns can help you!

* Unit tests
    * DO write tests!
    * DO isolate your units under test (you don't need to read and write the file to test filtering behaviour if you have designed it well!)
    * DO include some end-to-end tests

### What I did
* Organised the structure of the conversation exporter into multiple files
* Added an Activity class which represents the summary of any given sender in the chat
* Added additional options to the ConversationExporterConfiguration class
* Fixed the issue of Conversations not recording the entire message content
* Implemented a ConversationFilter and ActivityFilter class which handle the filtering for words and reports respectively
* Implemented a ConversationConverter class which can determine which methods to run based on the configuration of the options provided
* Ensured that strings were normalized during comparisons, ie, What! = what
* Built tests which ensure the validity of those filters added and the converter which determines applied filters

### Edits
* Choose to create a superclass called Filter which abstracted the common method between conversation filters and supplied a normalizing method and constructor for both singular and multiple words
* Each additional filter could then extends this class and implement the method under runFilter on any given conversation in place
* This would then need to be added to the new FilterBuilder class within the HashMap property which maps options to filter classes
* This new FilterBuilder can decide which filters to apply given a parse result from the command line and a configuration, with only the hashmap needing changes for future additions
* Tests were made more to reflect unit testing rather than integration testing
* Activity filter remained the same as it was too disjoint from filters which alter conversations themselves