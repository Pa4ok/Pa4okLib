package ru.pa4ok.library.loader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class ByteClassLoader extends URLClassLoader
{
    private final Map<String, byte[]> extraClasses = new HashMap<>();

    public ByteClassLoader(ClassLoader parent, ModuleByteData moduleByteData)
    {
        super(new URL[0], parent);
        moduleByteData.getClasses().forEach(cls -> extraClasses.put(cls.getName(), cls.getBytes()));
    }

    public void loadModule() throws Exception
    {
        for(String name : extraClasses.keySet())
        {
            Class<?> cls = this.findClass(name);
            try {
                Method method = cls.getDeclaredMethod("staticModuleInit");
                method.invoke(null);
                return;
            } catch (NoSuchMethodException e) {
            }
        }

        throw new RuntimeException("Can't load module, no class or main method");
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException
    {
        byte[] bytes = extraClasses.get(name);
        if(bytes != null) {
            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }
}
