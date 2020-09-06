package com.mindlinksoft.recruitment.mychat.Objects;

public class FlagContainer {
    public Boolean detailFlag;
    public Boolean obfFlag;
    public Boolean reportFlag;

    public FlagContainer(Boolean detailFlag, Boolean obfFlag, Boolean reportFlag){
        this.detailFlag = detailFlag;
        this.obfFlag = obfFlag;
        this.reportFlag = reportFlag;
    }
}
