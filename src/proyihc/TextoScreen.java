/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import MODEL.Estudiante;
import MODEL.Version;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;


/**
 *
 * @author medin
 */
public class TextoScreen {
    Label l;
    File f;
    Button volver;
    VBox root,v,v1;
    HBox h1;
    ScrollPane menuBotones;
    TextArea text;
    int var=200;
    static int i=0;
    String textoori;
    DatosExcel datosE;
    
    public TextoScreen(File f){
        this.f=f;
        
        InicializarComponentes();
        Diseño();
        obtener(datosE.leerexcel());
    }
    public Parent getroot(){
        return root;
    }
    public void InicializarComponentes(){
        root= new VBox(05);
        l= new Label();
        l.setFont(new Font(20));
        text= new TextArea();
        text.setMinSize(600, 300);
        text.setEditable(false);
        text.setWrapText(true);
        volver = new Button("Volver");
        datosE = new DatosExcel(f);
        
        HBox h= new HBox(20);
        h.setPadding(new Insets(40, 50, 0, 80));
        ImageView im= new ImageView(new Image("img/logo.png"));
        im.setOpacity(0.94);
        im.setFitWidth(70);
        im.setFitHeight(70);
        h.getChildren().addAll(l);
        h1= new HBox();
        v1= new VBox(10);
        menuBotones = new ScrollPane();
        menuBotones.autosize();
        menuBotones.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        menuBotones.setContent(v1);
        v= new VBox(10);
        v.setPadding(new Insets(20, 50, 50, 50));
        v.getChildren().addAll(text,volver);
        h1.getChildren().addAll(menuBotones,v);
        root.getChildren().addAll(h,h1);
        volver.setOnAction(e-> volver());
        root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
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
                    ((Button) node).setEffect(new Glow(0.5));
                });
                node.setOnMouseExited(e->{
                    ((Button) node).setFont(new Font(20));
                    ((Button) node).setEffect(null);
                });
            }
        }
    }
    
    public void obtener(ArrayList<Version> versiones){
        for (i=0; i < versiones.size(); i++) {
            Button b= new Button("Version "+((Version)versiones.get(i)).getNo());
            v1.getChildren().add(b);
            b.setOnAction(new TextoScreen.botonEvent((Version) versiones.get(i)));
        }
    }

    private void volver() {
        MainScreen m= new MainScreen(f);
        Scene s= new Scene(m.getroot(), 800, 500);
        Stage st=(Stage)root.getScene().getWindow();
       
        st.setScene(s);
    }
    
    private class botonEvent implements EventHandler<ActionEvent> {
        Version v=new Version();
        public botonEvent(Version v){
            this.v=v;
        }
        public void handle(ActionEvent ke) {
            //Eliminar etiquetas html
            text.setText(Jsoup.parse(v.getVersionA()).wholeText());
           
        }
    }
}
