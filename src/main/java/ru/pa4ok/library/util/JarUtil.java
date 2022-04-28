package ru.pa4ok.library.util;

import java.awt.*;
import java.io.*;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarUtil
{
    public static List<String> readAllLines(String fileName, Class<?> jarCls) throws Exception
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

    public static File getFile(String path) throws IOException {
        return new File(JarUtil.class.getClassLoader().getResource(path).getFile());
    }

    public static Image getImage(String path) throws IOException {
        return Toolkit.getDefaultToolkit().getImage(JarUtil.class.getClassLoader().getResource(path));
    }

    public static Font getFont(String path) throws Exception {
        return Font.createFont(Font.TRUETYPE_FONT, JarUtil.class.getClassLoader().getResourceAsStream(path));
    }

    public static void extractFromClasspath(String source, String destination) throws IOException
    {
        new File(destination).mkdirs();

        final URL dirURL = JarUtil.class.getResource(source);
        final String path = source.substring(1);

        if (dirURL == null) {
            throw new IllegalStateException(String.format("Can't find %s on the classpath.", source));
        }

        if (!dirURL.getProtocol().equals("jar")) {
            throw new IllegalStateException(String.format("%s is not located in a jar file... aborting.", source));
        }

        final JarURLConnection jarConnection = (JarURLConnection) dirURL.openConnection();
        final ZipFile jar = jarConnection.getJarFile();
        final Enumeration<? extends ZipEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            final String name = entry.getName();

            if (!name.startsWith(path))
                continue;

            final String entryTail = name.substring(path.length());
            final File file = new File(destination + File.separator + entryTail);

            if (entry.isDirectory()) {
                final boolean directoryMade = file.mkdir();
                if (!directoryMade) {
                    System.out.println("Unable to create the directory... aborting.");
                }
            } else {
                final InputStream inputStream = jar.getInputStream(entry);
                final OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                final byte[] buffer = new byte[4096];

                int readCount;
                while ((readCount = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readCount);
                }

                outputStream.close();
                inputStream.close();
            }
        }
    }
}
