package swt6.beans;

import java.util.EventListener;

// Mark that we want only one function in this interface
@FunctionalInterface
public interface TimerListener extends EventListener {
    void expired(TimerEvent event);
}
