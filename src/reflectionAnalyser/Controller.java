package reflectionAnalyser;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.TreeSet;

public class Controller {
    //Klasse, die die Suche mit Reflection durchfuehrt (Einhalten von MVC)
    private ClassAnalyser model = new ClassAnalyser(this);

    private String currentClassName = "";

    @FXML
    private TextField searchField;
    @FXML
    private TreeView<String> tree;
    @FXML
    private VBox searchResContainer;
    @FXML
    private Label instanceOutput;


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

    public void buildReflectionTree(String fullClassName, String className, String defConstr, String[] constructors, String[] attributes, String[] methods){
        currentClassName = fullClassName;

        TreeItem<String> rootNode = new TreeItem<>(className);
        TreeItem<String> defaultConstructorNode = new TreeItem<>("Default-constructor");
        TreeItem<String> constructorNode = new TreeItem<>("Constructors");
        TreeItem<String> attributeNode = new TreeItem<>("Attributes");
        TreeItem<String> methodNode = new TreeItem<>("Methods");

        if(defConstr!=null) {
            TreeItem<String> defConstrItem= new TreeItem<>(defConstr);
            defaultConstructorNode.getChildren().add(defConstrItem);


        }
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

    public void createStandardInstance(){
        String instanceData = model.createInstance(currentClassName);
        instanceOutput.setText("Instance Created: "+instanceData);
    }
}
