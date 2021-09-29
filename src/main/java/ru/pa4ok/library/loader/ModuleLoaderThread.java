package ru.pa4ok.library.loader;

public class ModuleLoaderThread extends Thread
{
    private final ByteClassLoader byteClassLoader;

    public ModuleLoaderThread(ModuleByteData moduleByteData)
    {
        this.byteClassLoader = new ByteClassLoader(getClass().getClassLoader(),  moduleByteData);
        this.setDaemon(false);
    }

    public static void createAndStart(ModuleByteData moduleByteData)
    {
        new ModuleLoaderThread(moduleByteData).start();
    }

    @Override
    public void run()
    {
        try {
            this.byteClassLoader.loadModule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
