/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author medin
 */
public class LoadingScreen {
    VBox root = new VBox();
    File f;
    Button b;
    Label l;
    Dragboard db;
    FileChooser fc;
    public Parent getroot(){
        return root;
    }
    public LoadingScreen(){
        b=new Button("Cargar Archivo");
        fc= new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        l= new Label("¡Arrastra el Archivo!");
        l.setFont(new Font("Showcard Gothic", 10));
        try {
            BackgroundImage myBI= new BackgroundImage(new Image("img/3.png",true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
            root.setBackground(new Background(myBI));
        } catch (Exception e) {
            root.setBackground(new Background(new BackgroundFill(Color.DARKTURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        
        root.setPadding(new Insets(400, 300, 50, 300));
        root.getChildren().addAll(b,l);
        l.setOnMouseEntered(e-> l.setEffect(new DropShadow(10, Color.BLUE)));
        l.setOnMouseExited(e-> l.setEffect(null));
        b.setOnAction(e->{
            f=(File)fc.showOpenDialog(root.getScene().getWindow());
            if(f!=null){
                CambiarVentana();
            }
            
            
        });
        //drag();
        
    }
    
    public void drag(){
        root.setOnDragOver(e->{
             if (e.getGestureSource() != root
                        && e.getDragboard().hasFiles()) {
                    e.acceptTransferModes(TransferMode.ANY);
                }
            });
        root.setOnDragDropped((DragEvent event) -> {
            db = event.getDragboard();
            List<File> files=event.getDragboard().getFiles();
            l.setText(files.get(0).getPath());
            System.out.println(l.getText());
            CambiarVentana();
            
        });
    }
    public void CambiarVentana(){
        System.out.println(f.exists());
        MainScreen m= new MainScreen(f);
        Scene s= new Scene(m.getroot(), 800, 500);
        Stage st=(Stage)root.getScene().getWindow();
        Stage st2=new Stage(StageStyle.UNIFIED);
        st.setScene(s);
    }
}