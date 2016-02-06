package reflectionAnalyser;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    public void buildReflectionTree(String className, String defConstr, String[] constructors, String[] attributes, String[] methods){
        TreeItem<String> rootNode = new TreeItem<>(className);
        TreeItem<String> defaultConstructorNode = new TreeItem<>("Default-constructor");
        TreeItem<String> constructorNode = new TreeItem<>("Constructors");
        TreeItem<String> attributeNode = new TreeItem<>("Attributes");
        TreeItem<String> methodNode = new TreeItem<>("Methods");

        defaultConstructorNode.getChildren().add(new TreeItem<>(defConstr));
        for(String s : constructors){
            constructorNode.getChildren().add(new TreeItem<>(s));
        }
        for(String s : attributes){
            attributeNode.getChildren().add(new TreeItem<>(s));
        }
        for(String s : methods){
            methodNode.getChildren().add(new TreeItem<>(s));
        }
        rootNode.getChildren().addAll(defaultConstructorNode,constructorNode,attributeNode,methodNode);

        tree.setRoot(rootNode);
    }
}
