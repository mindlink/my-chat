package com.mindlinksoft.recruitment.mychat;

/**
 * Created by jcneves on 13/12/15.
 */
public class SenderReport implements Comparable<SenderReport> {
  String name;
  int totalMessages;

  public SenderReport(String name) {
    this.name = name;
    this.totalMessages = 1;
  }

  public void incrementTotal() {
    ++this.totalMessages;
  }

  public int getTotalMessages() {
    return totalMessages;
  }

  public String getName() {
    return name;
  }

  @Override
  public int compareTo(SenderReport o) {
    if (this.totalMessages < o.totalMessages) return 1;
    else if (this.totalMessages > o.totalMessages) return -1;
    else return 0;
  }
}
