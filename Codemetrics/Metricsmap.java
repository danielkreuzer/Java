package codemetrics;

import java.lang.reflect.Method;
import java.util.*;

import static java.lang.Math.round;

public class Metricsmap {
    private NavigableMap metrics = new TreeMap();

    public void add(Class c) {
        //CALCULATE CLASS METRICS
        int         nrMPublic = 0;
        int         nrMPrivate = 0;
        int         nrMProtected = 0;
        int         nrInterfaces = 0;
        int         derivationDepth = 0;
        double      averageParameters = 0;
        int         nrOfParams = 0;
        Method[] methods = null;
        try {
            methods = c.getMethods();
        } catch (NoClassDefFoundError e) {
            //do nothing
        }

        if(methods != null) {
            for (int i = 0; i < methods.length; i++) {
                // 1 - public 2 - private 4 - protected 8 - static
                nrOfParams += methods[i].getParameterCount();
                char x = String.valueOf(Math.abs((long) methods[i].getModifiers())).charAt(0);
                if (x == '1') {
                    nrMPublic++;
                } else if (x == '2') {
                    nrMPrivate++;
                } else if (x == '4') {
                    nrMProtected++;
                }
            }

            Class dervivationCheck = c.getSuperclass();
            while (dervivationCheck != null) {
                dervivationCheck = dervivationCheck.getSuperclass();
                derivationDepth++;
            }

            Class[] interf = c.getInterfaces();
            nrInterfaces = interf.length;

            averageParameters = round(((double) nrOfParams / (double) methods.length) * 100.0) / 100.0;
            ClassMetrics classMetrics = new ClassMetrics(c.getName(), c.getSimpleName(), nrMPublic, nrMPrivate, nrMProtected, nrInterfaces, derivationDepth, averageParameters);
            metrics.put(c.getName(), classMetrics);
        }
    }

    public void add(String PackageName) {
        metrics.put(PackageName, null);
    }

    public ClassMetrics get(String className) {
        return (ClassMetrics) metrics.get(className);
    }

    public void PrintClassMetrics(String className) {
        ClassMetrics classMetrics = get(className);
        if(classMetrics != null) {
            System.out.println("   Class metrics for class: " + className + "\n" +
                    "   Class contains " + classMetrics.getNrMPrivate() + " private methodes, " +
                    classMetrics.getNrMPublic() + " public methodes " +
                    classMetrics.getNrMProtected() + " protected methodes \n" +
                    "   #interfaces: " + classMetrics.getNrInterfaces() + "\n" +
                    "   derivation depth: " + classMetrics.getDerivationDepth() + "\n" +
                    "   average parameters: " + classMetrics.getAverageParameters() + "\n");
        } else {
            System.out.println("---");
            System.out.println("-> PACKAGE: " + className);
            System.out.println("---\n");
        }
    }

    public void PrintAllClasses() {
        Iterator it = metrics.entrySet().iterator();
        while (it.hasNext()) {
            NavigableMap.Entry pair = (NavigableMap.Entry)it.next();
            PrintClassMetrics((String) pair.getKey());
        }
    }

    private double getAverage(int x, int sum) {
        return round(((double) x / (double) sum) * 100.0) / 100.0;
    }

    public void PrintPackage(String packageName) {
        HashSet<Integer>         nrMPublic = new HashSet<Integer>();
        HashSet<Integer>         nrMPrivate = new HashSet<Integer>();
        HashSet<Integer>         nrMProtected = new HashSet<Integer>();
        HashSet<Integer>         nrInterfaces = new HashSet<Integer>();
        HashSet<Integer>         derivationDepth = new HashSet<Integer>();
        HashSet<Double>          averageParameters = new HashSet<Double>();
        int                      sumInterfaces = 0;
        int                      nrClasses = 0;
        int                      nrPackages = 0;

        String start = "";
        String end = "";
        try {
            start = metrics.higherKey(packageName).toString();
        } catch (NullPointerException e) {
            //do nothing
        }
        if(!start.equals("")) {
            Iterator find = metrics.entrySet().iterator();
            while (find.hasNext()) {
                NavigableMap.Entry pair = (NavigableMap.Entry) find.next();
                if (pair != null && pair.getKey().toString().startsWith(packageName)) {
                    end = pair.getKey().toString();
                }
            }
            if (!end.equals("")) {
                NavigableMap temp = metrics.subMap(start, true, end, true);
                Iterator it = temp.entrySet().iterator();
                while (it.hasNext()) {
                    NavigableMap.Entry pair = (NavigableMap.Entry) it.next();
                    ClassMetrics value = (ClassMetrics) pair.getValue();
                    if(value != null) {
                        nrClasses++;
                        nrMPublic.add(value.getNrMPublic());
                        nrMPrivate.add(value.getNrMPrivate());
                        nrMProtected.add(value.getNrMProtected());
                        nrInterfaces.add(value.getNrInterfaces());
                        sumInterfaces += value.getNrInterfaces();
                        derivationDepth.add(value.getDerivationDepth());
                        averageParameters.add(value.getAverageParameters());
                    } else {
                        nrPackages++;
                    }
                }

                int sumOfParams = nrMPublic.size() + nrMPrivate.size() + nrMProtected.size();
                System.out.println("   --Metrics for package: " + packageName + "--\n" +
                        "   #packeges:              " + nrPackages + "    #classes    " + nrClasses + "\n" +
                        "   Package contains    min " + Collections.min(nrMPublic) + "    max " + Collections.max(nrMPublic) + "  avg " + getAverage(nrMPublic.size(), sumOfParams) + " sum " + nrMPublic.size() + " public methodes, \n" +
                        "                       min " + Collections.min(nrMPrivate) + "    max " + Collections.max(nrMPrivate) + "   avg " + getAverage(nrMPrivate.size(), sumOfParams) + " sum " + nrMPrivate.size()+" private methodes, \n" +
                        "                       min " + Collections.min(nrMProtected) + "    max " + Collections.max(nrMProtected) + "   avg " + getAverage(nrMProtected.size(), sumOfParams) + " sum " + nrMProtected.size()+" protected methodes, \n" +
                        "   #interfaces:        min " + Collections.min(nrInterfaces) + "    max " + Collections.max(nrInterfaces) + "   sum " + sumInterfaces + "\n" +
                        "   derivation depth:   min " + Collections.min(derivationDepth) + "    max " + Collections.max(derivationDepth) +"\n" +
                        "   average parameters: min " + Collections.min(averageParameters) + "  max " + Collections.max(derivationDepth) + "\n"
                        );
            }
        }
    }

    public void PrintHierarchy() {
        Iterator it = metrics.entrySet().iterator();
        while (it.hasNext()) {
            NavigableMap.Entry pair = (NavigableMap.Entry)it.next();
            if(pair.getValue() == null) {
                String tab = "";
                int count =  (pair.getKey().toString().length()) - (pair.getKey().toString().replaceAll("\\.","").length());
                for (int i = 0; i < count; i++) {
                    tab += "   ";
                }
                System.out.println();
                System.out.println(tab + " --| PACKAGE: " + pair.getKey().toString());
            } else {
                String tab = "";
                int count =  pair.getKey().toString().length() - pair.getKey().toString().replaceAll("\\.","").length();
                for (int i = 0; i < count; i++) {
                    tab += "   ";
                }
                ClassMetrics classMetrics = (ClassMetrics) pair.getValue();
                if(classMetrics.getSimpleName().equals("")) {
                    System.out.println(tab + "| class: " + classMetrics.getName());
                } else {
                    System.out.println(tab + "| class: " + classMetrics.getSimpleName());
                }
            }
        }
    }
}




































