/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import MODEL.Version;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.swing.ToolTipManager;
import org.jsoup.Jsoup;

/**
 *
 * @author medin
 */
public class InfoReporte {
    GridPane root;
    Label lblNombre,lblUsuario,lblFecha,lu,lf,la;
    TextArea Cambios;
    Version v;
    public InfoReporte(Version v){
        this.v=v;
        IniciarComponentes();
        lblNombre.setText("Cantidad:");
    }

    private void IniciarComponentes() {
        root=new GridPane();
        lblNombre= new Label();
        root.setPadding(new Insets(8));
        root.setVgap(10);
        root.setHgap(10);
        lblUsuario= new Label();
        lblFecha= new Label();
        Cambios= new TextArea();
        Cambios.setEditable(false);
        Cambios.setStyle("-fx-background-color: transparent");
        Cambios.setWrapText(true);
        lu=new Label("Usuario:");
        lf=new Label("Fecha:");
        la=new Label("Aporte:");
        Tooltip t= new Tooltip("[+] Texto Agregado\n [-] Texto Eliminado");
        t.setFont(Font.font(20));
        t.install(root, t);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        lu.setMinSize(100, 20);
        lu.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lf.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        la.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        root.add(lu, 0, 1);
        root.add(lf, 0, 2);
        root.add(la, 0, 3);
        root.add(lblNombre, 0, 0);
        root.add(new Label("\t"+v.getCambios().trim().length()+" Palabras editadas"),1,0);
        root.add(lblUsuario, 1, 1);
        root.add(lblFecha, 1, 2);
        root.add(Cambios, 1, 3);
        lblUsuario.setText("\t"+v.getResponsable());
        lblFecha.setText("\t"+v.getFecha());
        Cambios.setText(Jsoup.parse(v.getCambios()).wholeText());
       
    }
    public Parent getroot(){
        return root;
    }
}
