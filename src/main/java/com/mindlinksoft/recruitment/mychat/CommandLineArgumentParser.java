package mychat;

public final class CommandLineArgumentParser {

    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    String input = arguments[1];
    String output = arguments[3];

        return new ConversationExporterConfiguration(input, output, FormulateCommandOptions(arguments));

    }

    private CommandOptions FormulateCommandOptions (String[] arguments) {

        FilterOptions conversationFilterOptions = new FilterOptions();
        PostProcessOptions conversationPostProcessOptions = new PostProcessOptions();
        ReportOptions conversationReportOptions = new ReportOptions() ;

        if (arguments.length > 4) {
            for (int i = 4; i < arguments.length; i++) {

                if (arguments[i] == "-user") {
                       conversationFilterOptions.setUserId(arguments[i+1]);
                }

                else if (arguments[i] == "-keyword") {
                    conversationFilterOptions.setKeyword(arguments [i+1]);
                }

                else if (arguments[i] == "-creditCard") {
                    conversationPostProcessOptions.setHideCreditCard(true);
                }

                else if (arguments[i] == "-obfuscate") {
                    conversationPostProcessOptions.setObfuscate(true);
                    conversationPostProcessOptions.setUserIdToBeObfuscated(arguments [i+1]);
                    conversationPostProcessOptions.setUserIdToBeObfuscatedWith(arguments[i+2]);

                }

                else if (arguments[i] == "-hide") {
                    String word = arguments[i+1];
                    while (!word.startsWith("-")) {
                        conversationPostProcessOptions.addToHideWords(word);
                        word = arguments[i++];
                    }

                }

                else if (arguments[i] == "-report") {
                    conversationReportOptions.setIncludeReport(true);
                }
                else {
                    continue;
                }
            }
        }
    return new CommandOptions(conversationFilterOptions, conversationPostProcessOptions, conversationReportOptions);
    }



}