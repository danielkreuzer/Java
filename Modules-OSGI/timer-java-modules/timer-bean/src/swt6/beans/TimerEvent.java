package swt6.beans;

import java.util.EventObject;

public class TimerEvent extends EventObject {
    private int tickCount;
    private int noTicks;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TimerEvent(Object source) {
        super(source);
    }

    public TimerEvent(Object source, int tickCount, int noTicks) {
        super(source);
        this.tickCount = tickCount;
        this.noTicks = noTicks;
    }

    public int getTickCount() {
        return tickCount;
    }

    public int getNoTicks() {
        return noTicks;
    }
}
