package swt6.osgi.paint;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.ServiceTracker;
import swt6.osgi.shape.ShapeFactory;
import swt6.util.JavaFxUtils;

public class PaintActivator implements BundleActivator {
    private PaintWindow paintWindow;
    private ServiceTracker shapeFactoryServiceTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        JavaFxUtils.initJavaFx();
        startUI(context);

        shapeFactoryServiceTracker = new ServiceTracker<>(context, ShapeFactory.class, new ShapeTrackerCurstomizer(context, paintWindow));
        shapeFactoryServiceTracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        stopUI(context);
        shapeFactoryServiceTracker.close();
    }

    private void startUI(BundleContext context) {
        paintWindow = new PaintWindow();
        paintWindow.show();
        paintWindow.addOnCloseEventHandler(evt -> {
            try {
                context.getBundle().stop();
            } catch (BundleException e) {
                e.printStackTrace();
            }
        });
        // Wollen dynamisch einpflegen das etwas verfügbar ist!
        // paintWindow.addShapeFactory(new RectangleFactory());
    }

    private void stopUI(BundleContext context) {
        if(paintWindow != null) {
            paintWindow.close();
        }
    }
}
