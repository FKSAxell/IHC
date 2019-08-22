/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import java.io.File;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author medin
 */
public class MainScreen {
    Label l;
    File f;
    Button texto,graficos,anexos,volver;
    VBox root,v;
    
    public MainScreen(File f){
        
        InicializarComponentes();
        Diseño();
        
    }
    public Parent getroot(){
        return root;
    }
    public void InicializarComponentes(){
        root= new VBox(20);
        this.f=f;
        texto= new Button("Ver Texto");
        graficos= new Button("Ver Graficos");
        anexos= new Button("Ver Anexos");
        volver= new Button("Volver");
        HBox h= new HBox(30);
        h.setPadding(new Insets(40, 50, 0, 80));
        ImageView im= new ImageView(new Image("img/logo.png"));
        im.setOpacity(0.94);
        im.setFitWidth(70);
        im.setFitHeight(70);
        //h.getChildren().addAll(im);
        v= new VBox(20);
        v.setPadding(new Insets(50, 300, 50, 300));
        v.getChildren().addAll(texto,graficos,anexos,volver);
        root.getChildren().addAll(h,v);
        
        
    }
    public void Diseño(){
        ObservableList<Node> o=v.getChildren();
        for (Node node : o) {
            if(node instanceof Button){
                ((Button) node).setMinSize(150, 60);
                ((Button) node).setFont(new Font(20));
//                node.setStyle(value);
                node.setOnMouseEntered(e->{
                    
                    ((Button) node).setFont(new Font(25));
                    ((Button) node).setEffect(new Glow(2));
                });
                node.setOnMouseExited(e->{
                    ((Button) node).setFont(new Font(20));
                    ((Button) node).setEffect(null);
                });
            }
        }
    }
}
