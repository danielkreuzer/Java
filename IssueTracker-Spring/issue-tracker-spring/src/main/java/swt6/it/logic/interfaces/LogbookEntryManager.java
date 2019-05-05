package swt6.it.logic.interfaces;

import com.sun.istack.Nullable;
import org.hibernate.annotations.common.util.impl.Log;
import swt6.it.domain.Issue;
import swt6.it.domain.LogbookEntry;
import swt6.it.domain.Project;

public interface LogbookEntryManager {
    LogbookEntry addLogbookEntry(LogbookEntry logbookEntry, @Nullable Issue issue, @Nullable Project project);
}
