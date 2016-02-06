package reflectionAnalyser;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import sun.reflect.Reflection;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.ObjDoubleConsumer;

/**
 * Created by Lukas Heidegger on 05.02.2016 - 10:37.
 */
public class ClassAnalyser {
    private Controller controller;

    private Reflections reflections;
    private List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();

    public ClassAnalyser(Controller c){
        controller = c;
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        reflections = new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(false),
                new ResourcesScanner()).setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(""))));
    }

    public TreeSet<String> searchClasses(String searchFor) {
        Set<String> allClasses = reflections.getAllTypes();
        TreeSet<String> allClassNames = new TreeSet<>();


        for(String s :allClasses){
            if(s.contains(searchFor)){
                allClassNames.add(s);
            }
        }
        return allClassNames;
    }

    public void reflectClass(String text) {

        try {
            Class c = Class.forName(text);
            String defConstr = null;
            try {
                defConstr = c.getConstructor().getName()+"()";
            } catch (NoSuchMethodException e) {
                System.out.println("No such Method found!");
            }


            String[] constructors = new String[c.getDeclaredConstructors().length];
            String[] attributes = new String[c.getDeclaredFields().length];
            String[] methods = new String[c.getDeclaredMethods().length];

            int i = 0;
            for (Constructor constr : c.getDeclaredConstructors()) {
                if(constr.getParameterCount()!=0){
                    constructors[i]=constr.getName()+"(";
                    Parameter[] tempParams = constr.getParameters();
                    for (Parameter p:tempParams) {
                        constructors[i]+=p.toString()+", ";
                    }
                    constructors[i]+=")";
                    i++;
                }
            }

            i = 0;
            for (Field field : c.getDeclaredFields()) {
                attributes[i]=field.getType().getName()+" "+field.getName();
                i++;
            }

            i = 0;
            for (Method meth : c.getDeclaredMethods()) {

                methods[i]=meth.getReturnType().toString()+" "+meth.getName()+"(";
                Parameter[] tempParams = meth.getParameters();
                for (Parameter p:tempParams) {
                    methods[i]+=p.toString()+", ";
                }
                methods[i]+=")";
                i++;
            }

            controller.buildReflectionTree(c.getName(), c.getSimpleName(),defConstr,constructors,attributes,methods);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




    }


    public String createInstance(String className) {
        try {
            Class c = Class.forName(className);
            Constructor con = c.getConstructor();
            Object obj = (Object)con.newInstance();
            return obj.getClass().getName()+ " Hash: "+obj.getClass().hashCode();


        } catch (ClassNotFoundException e) {
            return "Class not available";
        } catch (NoSuchMethodException e) {
            return "No StandardConstructor Available";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "Something went wrong";
    }

}
