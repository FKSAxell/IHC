/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import MODEL.Version;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.jsoup.Jsoup;

/**
 *
 * @author medin
 */
public class InfoReporte {
    GridPane root;
    Label lblNombre,lblUsuario,lblFecha;
    TextArea Cambios;
    Version v;
    public InfoReporte(Version v){
        this.v=v;
        IniciarComponentes();
        lblNombre.setText("Informacion");
    }

    private void IniciarComponentes() {
        root=new GridPane();
        lblNombre= new Label();
        
        lblUsuario= new Label();
        lblFecha= new Label();
        Cambios= new TextArea();
        Cambios.setEditable(false);
        Cambios.setStyle("-fx-background-color: transparent");
        Cambios.setWrapText(true);
        root.add(new Label("Usuario:"), 0, 1);
        root.add(new Label("Fecha:"), 0, 2);
        root.add(new Label("Aporte:"), 0, 3);
        root.add(lblNombre, 0, 0);
        root.add(new Label("\t"+v.getCambios().trim().length()+" Palabras añadidas"),1,0);
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
