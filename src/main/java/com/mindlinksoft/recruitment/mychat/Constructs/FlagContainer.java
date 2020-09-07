package com.mindlinksoft.recruitment.mychat.Constructs;

public class FlagContainer {

    public Boolean detailsFlag;
    public Boolean obfFlag;
    public Boolean reportFlag;

    public FlagContainer(Boolean detailsFlag, Boolean obfFlag, Boolean reportFlag) {
        this.detailsFlag = detailsFlag;
        this.obfFlag = obfFlag;
        this.reportFlag = reportFlag;
    }
}
