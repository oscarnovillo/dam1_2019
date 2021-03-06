package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Explorer extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    //System.setProperty("log4j.configurationFile", "log4j2.xml");
    FXMLLoader loaderMenu = new FXMLLoader(
        getClass().getResource("/fxml/principal.fxml"));
    BorderPane root = null;

      root = loaderMenu.load();
      Scene scene = new Scene(root);
      primaryStage.setTitle("IES Quevedo");
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setResizable(false);


  }
}
