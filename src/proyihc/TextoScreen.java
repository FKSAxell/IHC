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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    TextArea text;
    
    public TextoScreen(File f){
        this.f=f;
        
        InicializarComponentes();
        Diseño();
        leerexcel();
    }
    public Parent getroot(){
        return root;
    }
    public void InicializarComponentes(){
        root= new VBox(20);
        l= new Label();
        l.setFont(new Font(20));
        text= new TextArea();
        text.setMinSize(600, 300);
        text.setEditable(false);
        text.setWrapText(true);
        volver= new Button("Volver");
        HBox h= new HBox(30);
        h.setPadding(new Insets(40, 50, 0, 80));
        ImageView im= new ImageView(new Image("img/logo.png"));
        im.setOpacity(0.94);
        im.setFitWidth(70);
        im.setFitHeight(70);
        h.getChildren().addAll(l);
        h1= new HBox();
        v1= new VBox(10);
        v= new VBox(20);
        v.setPadding(new Insets(50, 50, 50, 50));
        v.getChildren().addAll(text,volver);
        h1.getChildren().addAll(v1,v);
        root.getChildren().addAll(h,h1);
        volver.setOnAction(e-> volver());
        
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
        MainScreen m= new MainScreen(f);
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
                XSSFRow row =(XSSFRow) rowit.next();
                Iterator column= row.cellIterator();
                while (column.hasNext()) {
                    XSSFCell celda = (XSSFCell)column.next();
                    Celdat.add(celda);
                }
                celldata.add(Celdat);
            
            }
        } catch (Exception e) {
        }
        obtener(celldata);
    }
    public void obtener(List CellDataList){
        List fila=new ArrayList();
        List fin=new ArrayList() ;
        System.out.println(CellDataList.size());
        for (int i = 3; i < CellDataList.size(); i++) {
            Button b= new Button("Version "+(i-3));
            v1.getChildren().add(b);
        }
        for (int i = 0; i < CellDataList.size(); i++) {
            fila= (List) CellDataList.get(i);
            l.setText(fila.get(0).toString());
            System.out.println("<<<<<<"+fila.get(3));
            fin=(List) CellDataList.get(6);
            
            for (int j = 0; j < fila.size(); j++) {
                XSSFCell celda=(XSSFCell) fila.get(j);
//                System.out.println(celda.toString()+"\n");
//                String valorcelda= celda.toString();
//                text.setText(text.getText()+"\n"+valorcelda);
            }
        }
        XSSFCell celda=(XSSFCell) fila.get(fin.size()-1);
        text.setText(l.getText()+""+text.getText()+"\n\n"+Jsoup.parse(celda.toString()).wholeText());
//        
//        text.setText(l.getText()+""+text.getText()+"\n"+celda.toString());
        System.out.println(celda.toString());
        
    }
}
