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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    Button texto,graficos,anexos,volver;
    VBox root,v;
    
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
        root= new VBox(20);
        l= new Label();
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
        texto.setOnAction(e->leerexcel());
        graficos.setOnAction(e->System.out.println("Graficos"));
        anexos.setOnAction(e->System.out.println("Anexos"));
        volver.setOnAction(e->volver());
        
        
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

    private void volver() {
        LoadingScreen m= new LoadingScreen();
        Scene s= new Scene(m.getroot(), 800, 500);
        Stage st=(Stage)root.getScene().getWindow();
       
        st.setScene(s);
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
            for (int j = 0; j < cellTempList.size(); j++) {
                XSSFCell celda=(XSSFCell) cellTempList.get(j);
                String valorcelda= celda.toString();
                System.out.println(valorcelda);
            }
        }
    }
}
