import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Collection;

public class UI extends Application {

    IdeationSystem ideationSystem = new IdeationSystem();

    void setIdeationSystem(IdeationSystem is) {
        ideationSystem = is;
    }

    @Override
    public void start(Stage stage) {

        if (ideationSystem == null)
            System.out.println("HIHI");

        //--------------Main Window-------------------------------
        HBox display = new HBox();
        display.setMinHeight(320);
        display.setAlignment(Pos.CENTER);
        Text displayText = new Text();
        display.getChildren().addAll(displayText);
        displayText.setFont(new Font(20));

        HBox control = new HBox();
        control.setMaxHeight(30);
        control.setSpacing(8);
        control.setAlignment(Pos.CENTER);

        Text pathText = new Text("Path (.json):");

        TextField pathTF = new TextField();
        pathTF.setDisable(true);
        pathTF.setPrefWidth(90);
        pathTF.setPromptText("Browse...");

        Button browseBT = new Button();
        browseBT.setText("Browse");
        browseBT.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                pathTF.setText(file.getName());
                if (Utility.needUpdatePath(file.toString())) {
                    // Path changed, need to reload the cards and piles
                    ideationSystem.reloadCardPiles();
                }
            }
        });


        Button addWordBT = new Button();
        addWordBT.setText("Add Word");
        addWordBT.setOnAction(event -> showAddWordWindow(stage));

        Button changePatternBT = new Button();
        changePatternBT.setText("Change Pattern");
        changePatternBT.setOnAction(event -> showChangePatternWindow(stage));

        Button randomizeBT = new Button();
        randomizeBT.setText("Give Me A New One!");
        randomizeBT.setOnAction(event -> {
            if (ideationSystem.cardPilesReady()) {
                String result = ideationSystem.generateResult();
                displayText.setText(result);
            }
        });

        control.getChildren().addAll(pathText, pathTF, browseBT, addWordBT, changePatternBT, randomizeBT);

        VBox wholeContainer = new VBox();
        wholeContainer.setPadding(new Insets(0,0,15,0));
        wholeContainer.getChildren().addAll(display, control);


        Scene scene = new Scene(wholeContainer, 750, 350);
        stage.setMinHeight(390);
        stage.setMinWidth(770);
        scene.heightProperty().addListener( (InvalidationListener) observable -> {
            wholeContainer.setPrefHeight(scene.getHeight());
            display.setPrefHeight(scene.getHeight()-control.getHeight());
        });
        stage.setTitle("Idea Cards for Brainstorming!");
        stage.setScene(scene);
        //--------------End of Main Window-------------------------------

        stage.show();

    }

    void showAddWordWindow(Stage mainStage) {
        Stage stage = new Stage();
        VBox wholeContainer = new VBox();
        wholeContainer.setAlignment(Pos.CENTER);
        final float PADDING = 30;
        wholeContainer.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        wholeContainer.setSpacing(10);

        HBox typeBox = new HBox();
        typeBox.setSpacing(10);
        typeBox.setAlignment(Pos.CENTER_LEFT);
        Text typeText = new Text("Type: ");
        ComboBox typeInput = new ComboBox();
        typeInput.getItems().addAll("Adjective", "Noun", "Verb");
        typeBox.getChildren().addAll(typeText, typeInput);

        HBox wordBox = new HBox();
        wordBox.setSpacing(10);
        wordBox.setAlignment(Pos.CENTER_LEFT);
        Text wordText = new Text("Word:");
        TextField wordInput = new TextField();
        wordBox.getChildren().addAll(wordText, wordInput);

        Button addBT = new Button();
        addBT.setText("Add!");
        addBT.setAlignment(Pos.CENTER_RIGHT);
        addBT.setOnAction(event -> {
            if ( (String)typeInput.getValue() == null)
                alertAndWait("Error", "Please select a type!", stage);
            else
                stage.close();
        });

        wholeContainer.getChildren().addAll(typeBox, wordBox, addBT);
        Scene scene = new Scene(wholeContainer, 190, 130);
        stage.initStyle(StageStyle.UTILITY); // Remove the minimize/maximize buttons
        stage.setResizable(false);
        stage.setTitle("Add-a-Word!");
        stage.setScene(scene);
        stage.setX(mainStage.getX() + mainStage.getWidth()/2 - scene.getWidth()/2);
        stage.setY(mainStage.getY() + mainStage.getHeight()/2 - scene.getHeight()/2);
        stage.show();
    }

    void showChangePatternWindow(Stage mainStage) {
        Stage stage = new Stage();

        VBox wholeContainer = new VBox();
        wholeContainer.setAlignment(Pos.CENTER);
        wholeContainer.setSpacing(15);
        wholeContainer.setPadding(new Insets(20,20,20,20));

        Text rulesText = new Text("Only strings composed of A,N,V are allowed.");
        rulesText.setWrappingWidth(175);

        TextField patternInput = new TextField();

        Button changeBT = new Button();
        changeBT.setText("Change!");
        changeBT.setOnAction(event -> {
            String input = patternInput.getText();
            if (input != null && ideationSystem.setPattern(input)) {
                stage.close();
            } else {
                alertAndWait("Error", "Please input a legal pattern!", stage);
            }
        });

        wholeContainer.getChildren().addAll(rulesText, patternInput, changeBT);

        Scene scene = new Scene(wholeContainer, 200, 130);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY); // Remove the minimize/maximize buttons
        stage.setResizable(false);
        stage.setTitle("Change-Pattern!");
        stage.setX(mainStage.getX() + mainStage.getWidth()/2 - scene.getWidth()/2);
        stage.setY(mainStage.getY() + mainStage.getHeight()/2 - scene.getHeight()/2);
        stage.show();
    }

    void alertAndWait(String title, String content, Stage mainStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(false);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.setX(mainStage.getX() + mainStage.getWidth()/2 - 230);
        alert.setY(mainStage.getY() + mainStage.getHeight()/2 - 50);

        alert.showAndWait();
    }

    void callLaunch(String[] args) {
        launch(args);
    }
}
