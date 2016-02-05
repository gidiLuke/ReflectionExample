package reflectionAnalyser;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ObjDoubleConsumer;

/**
 * Created by Lukas Heidegger on 05.02.2016 - 10:37.
 */
public class ClassAnalyser {
    private Controller controller;

    public ClassAnalyser(Controller c){
        controller = c;
    }

    public HashSet<String> searchClasses(String searchFor) {





        Reflections reflections = new Reflections();



        Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
        HashSet<String> allClassNames = new HashSet<>();

        for(Object c :allClasses){
            String s = c.getClass().getName();
            if(s.contains(searchFor)){
                allClassNames.add(c.getClass().getName());
            }
        }
        return allClassNames;
    }
}
