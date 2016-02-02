package mychat;

public class CommandOptions {

    public CommandOptions(FilterOptions conversationFilterOptions, PostProcessOptions conversationPostProcessOptions, ReportOptions conversationReportOptions) {
        this.conversationFilterOptions = conversationFilterOptions;
        this.conversationPostProcessOptions = conversationPostProcessOptions;
        this.conversationReportOptions = conversationReportOptions;
    }

    private FilterOptions conversationFilterOptions;
    private PostProcessOptions conversationPostProcessOptions;
    private ReportOptions conversationReportOptions;
    private String input;
    private String output;

    public void setConversationReportOptions(ReportOptions conversationReportOptions) {
        this.conversationReportOptions = conversationReportOptions;
    }

    public ReportOptions getConversationReportOptions() {
        return conversationReportOptions;
    }


    public PostProcessOptions getConversationPostProcessOptions() {
        return conversationPostProcessOptions;
    }

    public void setConversationFilterOptions(FilterOptions conversationFilterOptions) {
        this.conversationFilterOptions = conversationFilterOptions;
    }

    public void setConversationPostProcessOptions(PostProcessOptions conversationPostProcessOptions) {
        this.conversationPostProcessOptions = conversationPostProcessOptions;
    }

    public FilterOptions getConversationFilterOptions() {
        return conversationFilterOptions;
    }




}
