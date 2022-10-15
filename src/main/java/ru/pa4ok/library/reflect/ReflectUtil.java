package ru.pa4ok.library.reflect;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class ReflectUtil
{
    public static final Unsafe UNSAFE;
    public static final Lookup LOOKUP;

    static
    {
        try {
            MethodHandles.publicLookup(); // Just to initialize class

            // Get unsafe to get trusted lookup
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafeField.get(null);

            // Get trusted lookup and other stuff
            Field implLookupField = Lookup.class.getDeclaredField("IMPL_LOOKUP");
            LOOKUP = (Lookup) UNSAFE.getObject(UNSAFE.staticFieldBase(implLookupField), UNSAFE.staticFieldOffset(implLookupField));
        } catch (Throwable t) {
            throw new RuntimeException("Something wrong...", t);
        }
    }

    public static void invokeShutdown(int code)
    {
        try {
            LOOKUP.findStatic(Class.forName("java.lang.Shutdown"), "halt0", MethodType.methodType(void.class, int.class)).invokeExact(code);
        } catch (Throwable exc) {
            throw new InternalError(exc);
        }
    }
}