package swt6.beans.impl;

import swt6.beans.Timer;
import swt6.beans.TimerEvent;
import swt6.beans.TimerListener;

import java.util.Vector;

public class TimerImpl implements Timer {
    private int noTicks;
    private int interval;
    private Thread tickerThread;
    // volatile java cacht das nicht!, nur einfache abfragen werden dadurch synchronisiert
    private volatile boolean stopTimer;
    private Vector<TimerListener> listeners = new Vector<>();

    public TimerImpl() {
    }

    public TimerImpl(int interval, int noTicks) {
        this.noTicks = noTicks;
        this.interval = interval;
    }

    @Override
    public void start() {
        if(isRunning()) {
            throw new IllegalStateException("TimerImpl already running!");
        }

        stopTimer = false;
        // Lamda very easy, just () -> {}
        tickerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int tickCount = 0;
                while(!stopTimer && tickCount < noTicks) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                    }

                    if(!stopTimer) {
                        tickCount++;
                        fireTimerEvent(tickCount);
                    }

                }

                tickerThread = null;
            }

            // Fire Delegates
            private void fireTimerEvent(int tickCount) {
                var listenersClone = (Vector<TimerListener>)listeners.clone();
                var te = new TimerEvent(this, tickCount, noTicks);
                for (var l: listenersClone) {
                    l.expired(te);
                }
            }
        });

        tickerThread.start();
    }

    @Override
    public void stop() {
        stopTimer = true;
    }

    @Override
    public boolean isRunning() {
        return tickerThread != null;
    }

    public int getNoTicks() {
        return noTicks;
    }

    public void setNoTicks(int noTicks) {
        this.noTicks = noTicks;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public void addTimerListener(TimerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTimerListener(TimerListener listener) {
        listeners.remove(listener);
    }


}
