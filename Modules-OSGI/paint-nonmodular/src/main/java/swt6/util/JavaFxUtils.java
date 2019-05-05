package swt6.util;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
 import javafx.embed.swing.JFXPanel;

public class JavaFxUtils {
  
  private static JFXPanel jFxPanel;
  
  public static void initJavaFx() {
    if (jFxPanel == null) {
      jFxPanel = new JFXPanel(); // initialize JavaFX toolkit
      Platform.setImplicitExit(false);
    }
  }

  public static void exitJavaFx() {
    Platform.runLater(() -> Platform.exit());
  }

  public static void runAndWait(Runnable runnable)  {
    try {
			if (Platform.isFxApplicationThread()) {
			  runnable.run();
			}
			else {
			  FutureTask<Object> futureTask = new FutureTask<>(runnable, null);
			  Platform.runLater(futureTask);
			  futureTask.get();
			}
		}
		catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
  }

  public static void runLater(Runnable runnable) {
    Platform.runLater(runnable);
  }
}
