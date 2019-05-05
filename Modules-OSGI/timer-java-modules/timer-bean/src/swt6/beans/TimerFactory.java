package swt6.beans;

import swt6.beans.impl.TimerImpl;

public class TimerFactory {
    public static Timer createTimer(int interval, int noTicks) {
        return new TimerImpl(interval, noTicks);
    }
}
