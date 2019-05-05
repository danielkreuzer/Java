package swt6.spring.basics.ioc.util;

import org.springframework.stereotype.Component;

import javax.inject.Named;

@Component
@Log(Log.Type.STANDARD)
public class ConsoleLogger implements Logger {

  private String prefix = "";

  public void log(String msg) {
    System.out.println(prefix + msg);
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
}
