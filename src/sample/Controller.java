package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.HashSet;

public class Controller {
    //Klasse, die die Suche mit Reflection durchfuehrt (Einhalten von MVC)
    private ClassAnalyser model = new ClassAnalyser(this);


    @FXML
    private TextField searchField;
    @FXML
    private TreeView<String> tree;
    @FXML
    private VBox searchResContainer;


    /**
     * Gibt den zu suchenden Klassennamen an die Model-Klasse weiter
     */
    private void searchClass(){
        String searchFor = searchField.getText();

        HashSet<String> results = model.searchClasses(searchFor);


    }
}
