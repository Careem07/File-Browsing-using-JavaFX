package org.example.javafxlab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FileBrowsing.fxml"));

        Parent root = loader.load();
        MyController c = loader.getController();
        c.setStage(primaryStage);
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main( String[] args )
    {
        launch();
    }

   
}
