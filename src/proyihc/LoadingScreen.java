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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
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
import javax.swing.ToolTipManager;

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
        Tooltip to=new Tooltip("¡Carga un archivo!");
        to.setFont(Font.font(20));
        Tooltip.install(root, to);
        Tooltip.install(b, to);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setReshowDelay(1);
        b=new Button("Cargar Archivo");
        b.setFont(new Font(20));
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-background-color: #483D8B");
//                node.setStyle(value);
        b.setOnMouseEntered(e->{
                    
            b.setFont(new Font(20));
            b.setEffect(new Glow(0.5));
                });
            b.setOnMouseExited(e->{
                b.setFont(new Font(20));
                b.setEffect(null);
                });
        fc= new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        l= new Label("");
        //¡Arrastra el Archivo!
        l.setFont(new Font("Showcard Gothic", 10));
        try {
            BackgroundImage myBI= new BackgroundImage(new Image("img/6.png",true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
            root.setBackground(new Background(myBI));
        } catch (Exception e) {
            root.setBackground(new Background(new BackgroundFill(Color.DARKTURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        
        root.setPadding(new Insets(400, 300, 10, 530));
        root.getChildren().addAll(b);
      
        l.setOnMouseEntered(e-> l.setEffect(new DropShadow(10, Color.BLUE)));
        l.setOnMouseExited(e-> l.setEffect(null));
        b.setOnAction(e->{
            f=(File)fc.showOpenDialog(root.getScene().getWindow());
            
            if(f.getName().contains(".xlsx")){
                CambiarVentana();
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("¡Solo puedes abrir archivos de Excel!");
                a.showAndWait();
            }
            
            
        });
        drag();
        
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
        Scene s= new Scene(m.getroot(), 1200, 600);
        Stage st=(Stage)root.getScene().getWindow();
        Stage st2=new Stage(StageStyle.UNIFIED);
        st.setScene(s);
    }
}