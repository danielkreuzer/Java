package swt6.clients;

import swt6.beans.Timer;
import swt6.beans.TimerEvent;
import swt6.beans.TimerFactory;
import swt6.beans.TimerListener;

public class TimerClient {
    public static void main(String[] args) throws InterruptedException {
        //Timer timer = new TimerImpl(200, 10); würd bei osgi funktionieren, erst zur laufzeit kaputto, osgi hat versionierung
        Timer timer = TimerFactory.createTimer(200, 10);
        // Delegate fires
        timer.addTimerListener(new TimerListener() {
            @Override
            public void expired(TimerEvent event) {
                System.out.printf("timer expired %d/%d %n", event.getTickCount(), event.getNoTicks());
            }
        });
        timer.start();

        /* // Tests
        Thread.sleep(1000);
        timer.stop();
        Thread.sleep(1000);
        timer.start();
        */
    }

//    @Override
//    public void start(BundleContext bundleContext) throws Exception {
//        //Timer timer = new TimerImpl(200, 10);
//        Timer timer = TimerFactory.createTimer(200, 10);
//        // Delegate fires
//        timer.addTimerListener(new TimerListener() {
//            @Override
//            public void expired(TimerEvent event) {
//                System.out.printf("timer expired %d/%d %n", event.getTickCount(), event.getNoTicks());
//            }
//        });
//        timer.start();
//    }
//
//    @Override
//    public void stop(BundleContext bundleContext) throws Exception {
//
//    }
}
