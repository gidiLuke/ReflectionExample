package sample;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lukas Heidegger on 05.02.2016 - 10:37.
 */
public class ClassAnalyser {
    private Controller controller;

    public ClassAnalyser(Controller c){
        controller = c;
    }

    public HashSet<String> searchClasses(String searchFor) {





        Reflections reflections = new Reflections("sample");

        Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);


        return null;
    }
}
