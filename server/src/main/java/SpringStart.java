import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringStart {
    public static void main(String[] args) {
        //System.out.println("asd");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "config"
                );
        //System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
        //System.out.println("asd");
    }
}
