package reflectionAnalyser;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

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

    }
}
