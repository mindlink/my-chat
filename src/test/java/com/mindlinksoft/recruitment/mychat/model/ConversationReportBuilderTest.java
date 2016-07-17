package com.mindlinksoft.recruitment.mychat.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.bean.Message;
import com.mindlinksoft.recruitment.mychat.bean.Report;

public class ConversationReportBuilderTest extends ConversationTestBase {

	@Test
	public void testBuildReport() {
		List<Message> list = new ArrayList<>();
		list.add(createMessage(0, "bob", ""));
		list.add(createMessage(0, "john", ""));
		list.add(createMessage(0, "jane", ""));
		list.add(createMessage(0, "jake", ""));
		list.add(createMessage(0, "sally", ""));
		list.add(createMessage(0, "sally", ""));
		list.add(createMessage(0, "sally", ""));
		list.add(createMessage(0, "sally", ""));
		list.add(createMessage(0, "sally", ""));
		list.add(createMessage(0, "sally", ""));
		list.add(createMessage(0, "john", ""));
		list.add(createMessage(0, "jane", ""));
		list.add(createMessage(0, "jane", ""));
		list.add(createMessage(0, "jake", ""));
		list.add(createMessage(0, "jake", ""));
		list.add(createMessage(0, "jake", ""));
		ConversationReportBuilder builder = new ConversationReportBuilder(list);
		Report report = builder.build();
		Assert.assertEquals(5, report.getTopUsers().size());
		checkUserActivity(report, 0, "sally", 6);
		checkUserActivity(report, 1, "jake", 4);
		checkUserActivity(report, 2, "jane", 3);
		checkUserActivity(report, 3, "john", 2);
		checkUserActivity(report, 4, "bob", 1);
	}

	private void checkUserActivity(Report report, int index, String user, int activity) {
		Assert.assertEquals(user, report.getTopUsers().get(index).getUser());
		Assert.assertEquals(activity, report.getTopUsers().get(index).getMessages());
	}

}
