/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author medin
 */
public class MindStorm extends Application {
    
    @Override
    public void start(Stage st) {
        LoadingScreen l= new LoadingScreen();
        Scene scene = new Scene(l.getroot(), 800, 550);
        st.setTitle("MindStorm");
         try {
            st.getIcons().add(new Image(this.getClass().getResource("/img/fondo.png").toString()));
        } catch (Exception e) {
             System.err.println(e.getMessage());
        }
        st.setScene(scene);
       
        
        st.setOpacity(0.96);
        st.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
