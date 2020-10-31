package ru.pa4ok.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Test
{
    public static List<File> getFiles(String path, Function<File, File> filter)
    {
        File dir = new File(path);
        if(!dir.exists() || dir.isFile()) {
            return null;
        }

        List<File> list = new ArrayList<>();
        for(File f : dir.listFiles()) {
            if(f.isFile()) {
                File result = filter.apply(f);
                if(result != null) {
                    list.add(result);
                }
            }
        }

        return list;
    }

    public static void main(String[] args)
    {
        List<File> list = getFiles("/test", new Function<File, File>() {
            @Override
            public File apply(File file) {
                if(file.getName().endsWith(".txt")) {
                    return file;
                }
                return null;
            }
        });
    }
} 
