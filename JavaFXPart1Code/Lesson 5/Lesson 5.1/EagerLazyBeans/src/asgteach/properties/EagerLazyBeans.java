// EagerLazyBeans.java - JavaFXBean properties, eager/lazy evaluation
package asgteach.properties;

public class EagerLazyBeans {

    public static void main(String[] args) {
        EagerPerson eager = new EagerPerson();
        eager.setFullname("Joe Smith");    
        System.out.println(eager.fullnameProperty()); 
        
        LazyPerson lazy = new LazyPerson();
        lazy.setFullname("Jack Black");    
        System.out.println(lazy.fullnameProperty());
        
    }
}

/****************************************************

$ java EagerLazyBeans
StringProperty [bean: Joe Smith, name: fullname, value: Joe Smith]
StringProperty [bean: Jack Black, name: fullname, value: Jack Black]

*/
