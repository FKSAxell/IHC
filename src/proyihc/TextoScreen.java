/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import MODEL.Version;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
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
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.jsoup.Jsoup;


/**
 *
 * @author medin
 */
public class TextoScreen {
    Label l,caption;
    File f;
    Button volver,info;
    VBox root,v,v1;
    HBox h1,menuaba;
    ScrollPane menuBotones,scrtext;
    TextArea text;
    TextFlow textt;
    Text t;
    int var=200;
    static int i=0;
    String nombrepag;
    DatosExcel datosE;
    
    public TextoScreen(File f,String nombrepag){
        this.f=f;
        this.nombrepag=nombrepag;
        InicializarComponentes();
        Diseño();
        obtener(datosE.leerexcel());
    }
    public Parent getroot(){
        return root;
    }
    public void InicializarComponentes(){
        
        root= new VBox(05);
        l= new Label(nombrepag);
        caption=new Label();
        l.setFont(new Font(20));
        t=new Text();
        t.setWrappingWidth(200);
        textt= new TextFlow();
        textt.getChildren().addListener(
                (ListChangeListener<Node>) ((change) -> {
                    textt.layout();
                    scrtext.layout();
                    scrtext.setVvalue(1.0f);
                }));
        
        
        text= new TextArea();
        text.setMinSize(920, 360);
        text.setEditable(false);
        text.setWrapText(true);
        volver = new Button("Volver");
        datosE = new DatosExcel(f);
        
        HBox h= new HBox(20);
        h.setPadding(new Insets(10, 20, 0, 10));
        ImageView im= new ImageView(new Image("img/logo.png"));
        im.setOpacity(0.94);
        im.setFitWidth(70);
        im.setFitHeight(70);
        h.getChildren().addAll(l);
        h1= new HBox(5);
        v1= new VBox(5);
        v1.setPadding(new Insets(5, 0, 10, 0));
        
        scrtext= new ScrollPane();
        scrtext.setMinSize(1020, 380);
        scrtext.setMaxSize(1020, 380);
        scrtext.setStyle("-fx-background-color: transparent");
        scrtext.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrtext.setContent(textt);
        scrtext.setFitToWidth(true);
        scrtext.setFitToHeight(true);
        
        
        menuBotones = new ScrollPane();
        menuBotones.setStyle("-fx-background-color: transparent");
        menuBotones.autosize();
        menuBotones.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        menuBotones.setContent(v1);
        v= new VBox(10);
        root.setOnMouseClicked(e->{
            System.out.println(e.getX()+" "+ e.getY());
        });
        menuaba= new HBox(200);
        info= new Button("Ver Info");
        info.setDisable(true);
        menuaba.getChildren().addAll(info);
        v.setPadding(new Insets(10, 20, 05, 10));
        
//        v.getChildren().addAll(text,volver);
        
        v.getChildren().addAll(menuaba,scrtext,volver);
        scrtext.setOnMouseEntered(e->{
            info.setEffect(new Glow(05));
        });
        scrtext.setOnMouseExited(e->{
            info.setEffect(null);
        });
        
        h1.getChildren().addAll(v,menuBotones);
        root.getChildren().addAll(h,h1);
        volver.setOnAction(e-> volver());
        root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        volver.setMaxSize(100, 05);
        volver.setFont(new Font(20));
        volver.setTextFill(Color.WHITE);
        volver.setStyle("-fx-background-color: #483D8B");
        volver.setOnMouseEntered(e->{
            volver.setFont(new Font(20));
            volver.setEffect(new Glow(0.5));
        });
        volver.setOnMouseExited(e->{
            volver.setFont(new Font(20));
            volver.setEffect(null);
        });
    }
    public void Diseño(){
        ObservableList<Node> o=menuaba.getChildren();
        for (Node node : o) {
            if(node instanceof Button){
                ((Button) node).setMaxSize(100, 05);
                ((Button) node).setFont(new Font(20));
                ((Button) node).setTextFill(Color.WHITE);
                ((Button) node).setStyle("-fx-background-color: #483D8B");
//                node.setStyle(value);
                node.setOnMouseEntered(e->{
                    
                    ((Button) node).setFont(new Font(20));
                    ((Button) node).setEffect(new Glow(0.5));
                });
                node.setOnMouseExited(e->{
                    ((Button) node).setFont(new Font(20));
                    ((Button) node).setEffect(null);
                });
            }
        }
        
    }
    public void DiseñoVersiones(){
        ObservableList<Node> o=v.getChildren();
        o.add(volver);
        for (Node node : o) {
            if(node instanceof Button){
                ((Button) node).setMinSize(80, 20);
                ((Button) node).setFont(new Font(20));
//                ((Button) node).setTextFill(Color.WHITE);
//                ((Button) node).setStyle("-fx-background-color: #483D8B");
//                node.setStyle(value);
                node.setOnMouseEntered(e->{
                    
                    ((Button) node).setFont(new Font(22));
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
            String ver=((Version)versiones.get(i)).getNo();
//          Button b= new Button("Version "+ver.substring(0, ver.length()-2));
            Button b= new Button(((Version)versiones.get(i)).getFecha());
//            b.setTextFill(Color.WHITE);
//            b.setStyle("-fx-background-color: #483D8B");
            b.setOpacity(0.95);
            v1.getChildren().add(b);
            v1.setStyle("-fx-background-color: transparent");
            b.setOnAction(new TextoScreen.botonEvent((Version) versiones.get(i)));
            
        }
        
    }
    public void appendBold(String msg) { //similar for italic and regular
    append(msg, "-fx-font-weight: bold");
}

private synchronized void append(String msg, String style) {
    Platform.runLater(() -> {
        textt.getChildren().remove(t);
        t.setText(msg);
        
        t.setFont(Font.font(13));
        if (!style.equals("")) {
            t.setStyle(style);
        }
        
        textt.getChildren().add(t);
    });
}
    private void volver() {
        MainScreen m= new MainScreen(f);
        Scene s= new Scene(m.getroot(), 1200, 600);
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
            l.setText(nombrepag+"\t"+v.getResponsable());
//            t.setText(Jsoup.parse(v.getVersionA()).wholeText());
            append(Jsoup.parse(v.getVersionA()).wholeText(),"");
            text.appendText(Jsoup.parse(v.getVersionA()).wholeText());
            
            
            info.setDisable(false);
            info.setOnMouseClicked(e->{
                MostrarInfo(v);
            });
            
            
           
        }
    }
    public void MostrarInfo(Version v){
        InfoReporte i= new InfoReporte(v);
        Stage st=new Stage();
        Scene s=new Scene(i.getroot(),600, 200);
        st.setTitle(nombrepag);
        st.setScene(s);
        st.showAndWait();
    }
}
