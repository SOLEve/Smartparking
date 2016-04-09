package denkoviservidor;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DenkoviServidor extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Vista SmartParking");
        
        stage.getIcons().add(new Image("icono.png")); 
                
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);

    }
    
}
