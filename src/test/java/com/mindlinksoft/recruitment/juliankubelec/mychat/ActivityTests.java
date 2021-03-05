package com.mindlinksoft.recruitment.juliankubelec.mychat;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Activity} class.
 */
public class ActivityTests {

    /**
     * Test whether extractStats can generate a report based on a provided conversation
     */
    @Test
    public void testExtractStats() {
        Conversation c = TestHelper.prepareConversation().build();
        Activity activity = new Activity();
        activity.extractStats(c);
        ArrayList<Report> reports= (ArrayList<Report>)activity.getReports();
        assertEquals(3, reports.size());
        assertEquals("bob", reports.get(0).getSender());
        assertEquals(3, reports.get(0).getCount());

        assertEquals("angus", reports.get(1).getSender());
        assertEquals(2, reports.get(1).getCount());

        assertEquals("mike", reports.get(2).getSender());
        assertEquals(2, reports.get(2).getCount());
    }
}
