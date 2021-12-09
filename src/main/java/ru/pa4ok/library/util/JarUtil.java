package ru.pa4ok.library.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JarUtil
{
    public static List<String> readAllLines(String fileName, Class<?> jarCls) throws IOException, URISyntaxException, NullPointerException
    {
            URI uri = jarCls.getClassLoader().getResource(fileName).toURI();
            if("jar".equals(uri.getScheme())){
                Map<String, String> env = new HashMap<>();
                String uriString = uri.toString();
                int index = uriString.indexOf("!");
                try(FileSystem fs = FileSystems.newFileSystem(URI.create(uriString.substring(0, index)), env)) {
                    return Files.readAllLines(fs.getPath(uriString.substring(index + 1).replaceAll(Pattern.quote("!"), "")));
                }
            }
            return Files.readAllLines(Paths.get(uri));
    }
}
