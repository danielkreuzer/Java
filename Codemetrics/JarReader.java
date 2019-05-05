package codemetrics;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarReader {
    private JarFile[]   jarFiles;
    private File[]      files;
    private int         length;
    private Metricsmap  returnMap = new Metricsmap();

    private void LoadClass(String file, String className) {
        try {
            URLClassLoader cl = new URLClassLoader(new URL[] {new URL("file:///" + file)}, null);
            Class<?> clazz = cl.loadClass(className);
            returnMap.add(clazz);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL!");
        } catch (ClassNotFoundException e) {
            //Do nothing
        } catch (NoClassDefFoundError e) {
            //NoClassDefFoundError exception - you may have an dependency to an not existing class
        }
    }

    private void AddPackage(String packageName) {
        returnMap.add(packageName);
    }

    private void InitArrays(String[] inputFiles) {
        this.jarFiles = new JarFile[inputFiles.length];
        this.files = new File[inputFiles.length];
        this.length = inputFiles.length;

        try {
            for (int i = 0; i < length; i++) {
                jarFiles[i] = new JarFile(inputFiles[i]);
                files[i]    = new File(inputFiles[i]);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public JarReader(String[] inputFiles) {
       InitArrays(inputFiles);
    }

    public Metricsmap Read() {
        for (int i = 0; i < length; ++i) {
            if(jarFiles[i] != null) {
                for (Enumeration<JarEntry> entryEnumeration = jarFiles[i].entries(); entryEnumeration.hasMoreElements(); ) {
                    String entryName = entryEnumeration.nextElement().getName();
                    if (!entryName.startsWith("META-INF")) {
                        if (entryName.endsWith("/")) {
                            entryName = entryName.substring(0, entryName.length() - 1);
                        }
                        entryName = entryName.replaceAll("/", ".");
                        if (entryName.endsWith(".class")) {
                            String className = entryName.substring(0, entryName.lastIndexOf(".class"));
                            LoadClass(files[i].getAbsolutePath().replaceAll("\\\\", "/"), className);
                        } else {
                            AddPackage(entryName);
                        }
                    }
                }
            }
        }
        return returnMap;
    }
}
