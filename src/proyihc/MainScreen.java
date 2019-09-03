/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author medin
 */
public class MainScreen {
    Label l;
    File f;
    Button texto,anexos,volver;
    VBox v2,v;
    GridPane root;
    public MainScreen(File f){
        this.f=f;
        InicializarComponentes();
        System.out.println(this.f.getPath());
        Diseño();
        leerexcel();
        
    }
    public Parent getroot(){
        return root;
    }
    public void InicializarComponentes(){
        v2= new VBox(5);
        root=new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10)); 
        root.setVgap(10); 
        root.setHgap(10);
        
        l= new Label();
        l.setFont(new Font(20));
        root.setAlignment(Pos.CENTER);
        texto= new Button("Ver Texto del Documento");
        anexos= new Button("Ver Imagenes Adjuntas");
        volver= new Button("Cargar otro Documento");
        HBox h= new HBox(5);
        h.setPadding(new Insets(0, 0, 0, 0));
        ImageView im= new ImageView(new Image("img/logo.png"));
        im.setOpacity(0.94);
        im.setFitWidth(70);
        im.setFitHeight(70);
        h.getChildren().addAll(l);
        
        GaleriaScreen m= new GaleriaScreen(f);
        
        root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        v= new VBox(20);
        v.setPadding(new Insets(50, 50, 50, 50));
        v.getChildren().addAll(texto,anexos,volver);
        v2.getChildren().addAll(h,v);
        texto.setOnAction(e->vertexto());
        anexos.setOnAction(e->verAnexo());
        
        
        root.add(v2,0,0);
        root.add(m.getroot(),1,0);
        /*graficos.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
        alert.setTitle("VER GRAFICOS");
        alert.setHeaderText("");
        alert.setContentText("No se encontraron graficos en el presente documento");
        
 
        alert.showAndWait();
        });*/
        /*anexos.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
        alert.setTitle("NO HAY ANEXOS");
        alert.setHeaderText("");
        alert.setContentText("No existen anexos en el presente documento");
        
 
        alert.showAndWait();
       
        });*/
        volver.setOnAction(e->volver());
        
    }
    public void Diseño(){
        ObservableList<Node> o=v.getChildren();
        for (Node node : o) {
            if(node instanceof Button){
                ((Button) node).setMinSize(350, 70);
                ((Button) node).setFont(new Font(20));
                ((Button) node).setTextFill(Color.WHITE);
                ((Button) node).setStyle("-fx-background-color: #483D8B");
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
    public void leerarchivo(File f){
        if(!f.exists()) System.out.println("No existe el directorio");
        try {
            Scanner sc=new Scanner(f);
            String buf;
            while (sc.hasNext()) {
                buf=sc.nextLine();
                System.out.println(buf);
        }
        }catch(IOException e){        System.out.println("No se pudo leer el archivos");
            }
    }

    private void volver(){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            alert.setTitle("PRECAUCION");
            alert.setHeaderText("");
            alert.setContentText("¿Deseas salir?\n Perderas el presente documento!");
            alert.showAndWait();
            if (alert.getResult()== ButtonType.OK) {
                LoadingScreen m= new LoadingScreen();
                 Scene s= new Scene(m.getroot(), 1200, 600);
                Stage st=(Stage)root.getScene().getWindow();
       
                st.setScene(s);
            }
            else{
                
            }
        
    }
    public void leerexcel(){
        List celldata= new ArrayList();
        List Celdat=new ArrayList();
        try {
            FileInputStream fis= new FileInputStream(f);
            XSSFWorkbook wb= new XSSFWorkbook(fis);
            XSSFSheet sheet= wb.getSheetAt(0);
            Iterator rowit= sheet.rowIterator();
            while (rowit.hasNext()) {
                XSSFRow celdaDeFila =(XSSFRow) rowit.next();
                Iterator it= celdaDeFila.cellIterator();
                while (it.hasNext()) {
                    XSSFCell celda = (XSSFCell)it.next();
                    Celdat.add(celda);
                }
                celldata.add(Celdat);
            
            
            }
        } catch (Exception e) {
        }
        obtener(celldata);
    }
    public void obtener(List CellDataList){
        for (int i = 0; i < CellDataList.size(); i++) {
            List cellTempList= (List) CellDataList.get(i);
            l.setText("\t\t"+cellTempList.get(0).toString());
//            System.out.println("<<<<<<"+cellTempList.get(i));
            for (int j = 0; j < cellTempList.size(); j++) {
                XSSFCell celda=(XSSFCell) cellTempList.get(j);
                String valorcelda= celda.toString();
//                System.out.println(valorcelda);
               
            }
        }
    }

    private void vertexto() {
        TextoScreen m= new TextoScreen(f,l.getText());
        Scene s= new Scene(m.getroot(), 1200, 600);
        Stage st=(Stage)root.getScene().getWindow();
       
        st.setScene(s);
    }
    
    private void verAnexo(){
        AnexoScreen m= new AnexoScreen(f);
        Scene s= new Scene(m.getroot(), 1200, 600);
        Stage st=(Stage)root.getScene().getWindow();
       
        st.setScene(s);
        
    }
}
