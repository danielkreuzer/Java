package swt6.it.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import swt6.it.domain.LogbookEntry;
import swt6.it.logic.interfaces.LogbookEntryManager;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"applicationContextTest.xml"})
public class LogbookEntryManagerImplTest {

    @Autowired
    private LogbookEntryManager logbookEntryManager;

    @Test
    public void addLogbookEntry() {
        LogbookEntry logbookEntry1 = new LogbookEntry("Waste time of colleges123",
                LocalDateTime.of(2000, 1, 1, 9, 0),
                LocalDateTime.of(2000, 1, 1, 9, 30));
        logbookEntry1 = logbookEntryManager.addLogbookEntry(logbookEntry1, null, null);
        assertNotNull(logbookEntry1.getId());
    }
}