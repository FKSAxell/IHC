/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

/**
 *
 * @author kevin
 */
public class GaleriaScreen {
    Button cantPal,cantApo,cantAne,volver;
    GridPane  root;
    HBox hBotones;
    File f;
    PieChart pie;

    public GaleriaScreen(File f) {
        this.f=f;
        InicializarComponentes();
    }
    public Parent getroot(){
        return root;
    }
    
    public void InicializarComponentes(){
        cantPal= new Button("Cantidad de Palabras");
        cantApo= new Button("Cantidad de Aportaciones");
        cantAne= new Button("Cantidad de Anexos");
        volver= new Button("Volver");
        root = new GridPane(); 
        hBotones= new HBox();
        volver.setOnAction(e-> volver());
        cantPal.setOnAction(e->insertar("Cantidad de Palabras"));
        cantApo.setOnAction(e->insertar("Cantidad de Aportaciones"));
        cantAne.setOnAction(e->insertar("Cantidad de Anexos"));
        //root.setMinSize(400, 200); 
        root.setPadding(new Insets(10, 10, 10, 10)); 
        root.setVgap(5); 
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);
        hBotones.getChildren().addAll(cantPal,cantApo,cantAne);
        hBotones.setAlignment(Pos.CENTER);
        root.add(hBotones,1,0);
        root.add(volver,0,5);
        root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        pie=createPieChart("Cantidad de Aportaciones");
        root.add(pie, 1, 3);
       
    }
    public PieChart createPieChart(String titulo) {
      PieChart pie = new PieChart();
      ObservableList<PieChart.Data> data =
         FXCollections.observableArrayList();
        data.addAll(new PieChart.Data("Axell Concha", 30.0),
         new PieChart.Data("Jordy Medina", 20.3),
         new PieChart.Data("Axel Auza", 16.3),
         new PieChart.Data("Leonardo Gomez", 12.0));

      pie.setData(data);
      pie.setTitle(titulo);
      return pie;
    }
    private void volver() {
        MainScreen m= new MainScreen(f);
        Scene s= new Scene(m.getroot(), 800, 500);
        Stage st=(Stage)root.getScene().getWindow();
       
        st.setScene(s);
    }
    private void insertar(String titulo) {
        root.getChildren().remove(pie);
        pie=createPieChart(titulo);
        root.add(pie, 1, 3);
       
    }
   
    
}
