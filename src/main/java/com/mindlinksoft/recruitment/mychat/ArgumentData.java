package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

public class ArgumentData {
	public boolean filterEnabled;
	public ArrayList<String> filterValue;
	
	public ArgumentData(boolean enabled, ArrayList<String> arrayList) {
		this.filterEnabled = enabled;
		this.filterValue = arrayList;
	}
}
