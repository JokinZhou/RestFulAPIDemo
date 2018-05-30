package comm;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Api {
	String name() default "";

	String version() default "";

	String category() default "";
}
