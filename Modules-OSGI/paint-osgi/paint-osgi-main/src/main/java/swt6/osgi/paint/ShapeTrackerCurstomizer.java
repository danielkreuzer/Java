package swt6.osgi.paint;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import swt6.osgi.shape.ShapeFactory;

public class ShapeTrackerCurstomizer implements ServiceTrackerCustomizer<ShapeFactory, ShapeFactory> {
    private BundleContext context;
    private PaintWindow window;

    public ShapeTrackerCurstomizer(BundleContext context, PaintWindow window) {
        this.context = context;
        this.window = window;
    }

    @Override
    public ShapeFactory addingService(ServiceReference<ShapeFactory> reference) {
        ShapeFactory sf = context.getService(reference);
        window.addShapeFactory(sf);
        return sf;
    }

    @Override
    public void modifiedService(ServiceReference<ShapeFactory> reference, ShapeFactory service) {
        window.removeShapeFactory(service);
        window.addShapeFactory(service);
    }

    @Override
    public void removedService(ServiceReference<ShapeFactory> reference, ShapeFactory service) {
        window.removeShapeFactory(service);
    }
}
