package reflectionAnalyser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.TreeSet;

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
   public void searchClass(){
        String searchFor = searchField.getText();
        TreeSet<String> results = model.searchClasses(searchFor);

       searchResContainer.getChildren().clear();
       searchResContainer.layout();
        for (String s : results){
            Button tempLabel = new Button(s);
            tempLabel.setOnAction(event -> reflectClass(((Button)event.getSource()).getText()));
            searchResContainer.getChildren().add(tempLabel);
        }



    }

    public void reflectClass(String text) {
        model.reflectClass(text);

    }
}
