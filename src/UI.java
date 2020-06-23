import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collection;

public class UI extends Application {

    IdeationSystem is;

    @Override
    public void start(Stage stage) {


        //--------------Main Window-------------------------------
        HBox display = new HBox();
        display.setMinHeight(320);
        display.setAlignment(Pos.CENTER);


        HBox control = new HBox();
        control.setMaxHeight(30);
        control.setSpacing(8);
        control.setAlignment(Pos.CENTER);

        Text pathText = new Text("Path (.json):");

        TextField pathTF = new TextField();
        pathTF.setDisable(true);
        pathTF.setPromptText("Browse...");

        Button browseBT = new Button();
        browseBT.setText("Browse");

        browseBT.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                pathTF.setText(file.toString());
            }
        });


        Button addWordBT = new Button();
        addWordBT.setText("Add Word");

        Button changePatternBT = new Button();
        changePatternBT.setText("Change Pattern");

        Button randomizeBT = new Button();
        randomizeBT.setText("Give Me A New One!");

        control.getChildren().addAll(pathText, pathTF, browseBT, addWordBT, changePatternBT, randomizeBT);

        VBox wholeContainer = new VBox();
        wholeContainer.getChildren().addAll(display, control);


        Scene scene = new Scene(wholeContainer, 750, 350);
        scene.heightProperty().addListener( (InvalidationListener) observable -> {
            wholeContainer.setPrefHeight(scene.getHeight());
        //    display.setPrefHeight(scene.getHeight()-control.getHeight());
        });
        stage.setTitle("Idea Cards for Brainstorming!");
        stage.setScene(scene);
        //--------------End of Main Window-------------------------------

        stage.show();

    }

    void callLaunch(String[] args) {
        launch(args);
    }
}
