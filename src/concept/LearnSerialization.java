package concept;

import dtos.Person;

import java.io.*;


public class LearnSerialization {

    /*
        Serialization is the conversion of the state of an object into a byte stream;
        deserialization does the opposite.
        Stated differently, serialization is the conversion of a Java object into a static stream (sequence) of bytes,
        which we can then save to a database or transfer over a network.

        Classes that are eligible for serialization need to implement a special marker interface, Serializable.

        Static attributes belongs to a class and hence are not serialized
        Any attribute marked transient is also not serialized

        When a class implements the java.io.Serializable interface, all its sub-classes are serializable as well.
        public class Person implements Serializable {
            private int age;
            private String name;
            private Address country; // must be serializable too
        }

        The JVM associates a version (long) number with each serializable class.
        We use it to verify that the saved and loaded objects have the same attributes,
        and thus are compatible on serialization.

        Most IDEs can generate this number automatically,
        and it’s based on the class name, attributes, and associated access modifiers.
        Any changes result in a different number, and can cause an InvalidClassException.

        If a serializable class doesn’t declare a serialVersionUID,
        the JVM will generate one automatically at run-time.
        However, it’s highly recommended that each class declares its serialVersionUID,
        as the generated one is compiler dependent and thus may result in unexpected InvalidClassExceptions.

        For custom serialization view - https://www.baeldung.com/java-serialization
        custom serialization allows serialization of objects with transient or non serialized fields
     */

    /**
     * This method saves object to a file by serializing it, then reads it back from the file into another object
     * by deserialization.
     * <p>
     * Note that we have to explicitly cast the loaded object to a Person type.
     *
     * @throws IOException thrown by streams
     * @throws ClassNotFoundException thrown by readObject()
     */
    public void whenSerializingAndDeserializing_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
        Person person = new Person();
        person.setAge(30);
        person.setName("Aayush");
        person.setHeight(189);

        FileOutputStream fileOutputStream
                = new FileOutputStream("yourfile.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(person);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream
                = new FileInputStream("yourfile.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Person p2 = (Person) objectInputStream.readObject();
        objectInputStream.close();

        System.out.println(p2.getAge() == person.getAge());
        System.out.println(p2.getName().equals(person.getName()));
        System.out.println(p2.getHeight() == person.getHeight());
    }
}
