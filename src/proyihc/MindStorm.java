/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import javafx.application.Application;
import javafx.scene.Scene;
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
        Scene scene = new Scene(l.getroot(), 800, 500);
        st.setTitle("MindStorm");
        st.setScene(scene);
        
        
//        st.setOpacity(0.94);
        st.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
