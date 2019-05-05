package swt6.spring.basics.hello;

import java.util.StringJoiner;

public class GreetingServiceHtmlImpl implements GreetingService {

    private String message = "default message";

    @Override
    public void sayHello() {
        System.out.println(new StringJoiner("\n", "<html>", "</html>")
                .add("<h1>" + message + "</h1>"));
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
