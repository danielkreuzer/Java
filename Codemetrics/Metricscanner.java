package codemetrics;

import java.util.Scanner;

public class Metricscanner {

    public static void TestA() {
        System.out.println("====== 1. -> Test small .jar\n");
        //Init Test Jars
        String[] inputJars = new String[2];
        inputJars[0] = "C:\\Users\\danie\\Documents\\1_JAVA\\SWE\\UE5\\input\\argo-small-2.4.jar.zip";
        inputJars[1] = "C:\\Users\\danie\\Documents\\1_JAVA\\SWE\\UE5\\input\\tinylog.jar";
        JarReader jR1 = new JarReader(inputJars);
        Metricsmap metricsmap = jR1.Read();
        if(metricsmap != null) {
            System.out.println("\n  == Show Hierarchy");
            metricsmap.PrintHierarchy();
            System.out.println("\n  == Show details to example Package");
            metricsmap.PrintPackage("argo");
            System.out.println("\n  == Show details to example Package");
            metricsmap.PrintPackage("org.pmw.tinylog.labelers");
            System.out.println("\n  == Show details to example Class");
            metricsmap.PrintClassMetrics("argo.jdom");
            System.out.println("\n  == Show details to example Class");
            metricsmap.PrintClassMetrics("argo.staj");
        } else {
            System.out.println("Reading Problems!");
        }
    }

    public static void TestB() {
        System.out.println("====== 2. -> Test with springer framework");
        String[] inputJars = new String[1];
        inputJars[0] = "C:\\Users\\danie\\Documents\\1_JAVA\\SWE\\UE5\\input\\spring-framework-5.0.5.RELEASE\\libs\\spring-test-5.0.5.RELEASE.jar";
        //Read Test Jars
        JarReader jR1 = new JarReader(inputJars);
        Metricsmap metricsmap = jR1.Read();
        if(metricsmap != null) {
            System.out.println("\n  == Show Hierarchy");
            metricsmap.PrintHierarchy();
            System.out.println("\n  == Show details to example Package");
            metricsmap.PrintPackage("org.springframework.test");
            System.out.println("\n  == Show details to example Package");
            metricsmap.PrintPackage("org.springframework.test.context.support");
            System.out.println("\n  == Show details to example Class");
            metricsmap.PrintClassMetrics("org.springframework.test.context.support.ApplicationContextInitializerUtils");
            System.out.println("\n  == Show details to example Class");
            metricsmap.PrintClassMetrics("org.springframework.test.web.client.MockRestServiceServer$1");
        } else {
            System.out.println("Reading Problems!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=================  codemetrics  =================");
        System.out.println("Start prepared tests? (Type 'y' for Yes) when no, args will be analysed");
        String input = sc.nextLine();
        if(input.equals("y")) {
            TestA();
            TestB();
        } else {
            System.out.println("-- Analysing args -- ");
            JarReader jarReader = new JarReader(args);
            Metricsmap metricsmap = jarReader.Read();
            metricsmap.PrintHierarchy();
            input = "";
            while (!input.equals("quit")) {
                System.out.println("Enter package name for better analysis: (End with 'quit')");
                input = sc.nextLine();
                if(!input.equals("quit")) {
                    try {
                        metricsmap.PrintPackage(input);
                    } catch (Exception e) {
                        System.out.println("no valid input");
                    }
                }
            }
            input = "";
            while (!input.equals("quit")) {
                System.out.println("Enter class name for better analysis: (End with 'quit')");
                input = sc.nextLine();
                if(!input.equals("quit")) {
                    metricsmap.PrintClassMetrics(input);
                }
            }
        }

        System.out.println();
        System.out.println("==> metricscanner closed");
    }
}
