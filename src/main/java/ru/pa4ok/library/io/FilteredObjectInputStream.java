package ru.pa4ok.library.io;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilteredObjectInputStream extends ObjectInputStream
{
    public static final Set<String> DEFAULT_CLASSES = Stream.of(
            int.class, float.class, double.class, long.class, byte.class, char.class, boolean.class,
            int[].class, float[].class, double[].class, long[].class, byte[].class, char[].class, boolean[].class,
            String.class, String[].class, List.class, ArrayList.class, Set.class, HashSet.class, Map.class, HashMap.class
    ).map(Class::getName).collect(Collectors.toSet());

    public final Set<String> userClasses;

    public FilteredObjectInputStream(InputStream in, Set<Class<?>> classes) throws IOException
    {
        super(in);
        this.userClasses = classes.stream().map(Class::getName).collect(Collectors.toSet());
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException
    {
        String cls = desc.getName();

        if(!DEFAULT_CLASSES.contains(cls) && !userClasses.contains(cls)) {
            System.out.println("Received disallowed component class: " + cls);
            throw new InvalidClassException(cls);
        }

        return super.resolveClass(desc);
    }
}
