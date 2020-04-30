import UI.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "./"
                );
        context.getBean(Console.class).run();
    }
}
