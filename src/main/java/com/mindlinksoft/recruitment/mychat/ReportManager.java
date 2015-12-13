package com.mindlinksoft.recruitment.mychat;

import java.util.*;

/**
 * Created by jcneves on 13/12/15.
 */
public class ReportManager {

  private HashMap<String, SenderReport> report = new HashMap<>();

  private static ReportManager instance = null;

  private ReportManager() {
  }

  public static ReportManager sharedInstance() {
    if (instance == null) instance = new ReportManager();
    return instance;
  }

  public void addToReport(String senderId) {
    if(instance.report.containsKey(senderId)) {
      instance.report.get(senderId).incrementTotal();
    } else {
      instance.report.put(senderId, new SenderReport(senderId));
    }
  }

  public ArrayList<SenderReport> getReport() {
    ArrayList<SenderReport> reports = new ArrayList<>(instance.report.values());
    Collections.sort(reports);
    return reports;
  }
}
