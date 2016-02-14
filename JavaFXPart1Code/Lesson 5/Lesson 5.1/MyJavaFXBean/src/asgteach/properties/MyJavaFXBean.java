// MyJavaFXBean.java - JavaFXBean class
package asgteach.properties;

public class MyJavaFXBean {

    public static void main(String[] args) {
        Person male = new Person();
        male.setFullname("Joe Smith");
        System.out.println(male);
        
        Person female = new Person();
        female.setFullname("Susan Brown");
        female.setEmployed(true);
        System.out.println(female);
        
        System.out.println(female.fullnameProperty());
        System.out.println(female.fullnameProperty().getBean());
        System.out.println(female.fullnameProperty().getName());
        
        System.out.println(female.employedProperty());
        System.out.println(female.employedProperty().getBean());
        System.out.println(female.employedProperty().getName());
    }
}

/***************************************************************

$ java MyJavaFXBean
Joe Smith is not employed
Susan Brown is employed
StringProperty [bean: Susan Brown is employed, name: fullname, value: Susan Brown]
Susan Brown is employed
fullname
BooleanProperty [bean: Susan Brown is employed, name: employed, value: true]
Susan Brown is employed
employed

*/
