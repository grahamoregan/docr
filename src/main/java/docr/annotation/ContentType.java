package docr.annotation;


public @interface ContentType {
	
	String value() default "application/json";

}
