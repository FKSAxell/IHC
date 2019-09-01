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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

/**
 *
 * @author kevin
 */
public class GaleriaScreen {
    Button cantPal,cantApo,cantAne,volver;
    GridPane  root;
    HBox hBotones;
    File f;
    PieChart piePalabras, pieAportaciones;
    ArrayList<Estudiante> estudiantes;
    DatosExcel datosE;
    ArrayList<Version> versiones;
    ArrayList<String> estudiantesRepetidos;
    

    public GaleriaScreen(File f){
        this.f=f;
        datosE = new DatosExcel(f);
        versiones = datosE.leerexcel();
        estudiantesRepetidos = new ArrayList<>();
        estudiantes = new ArrayList<>();
        estudiantesRepetidos();
        listaEstudiantes();
        aporteEstudiantes();
        InicializarComponentes();
        
    }
    public Parent getroot(){
        return root;
    }
    
    public void InicializarComponentes(){
        /*cantPal= new Button("Cantidad de Palabras");
        cantApo= new Button("Cantidad de Aportaciones");
        cantAne= new Button("Cantidad de Anexos");*/
        volver= new Button("Volver");
        root = new GridPane(); 
        hBotones= new HBox();
        volver.setOnAction(e-> volver());
        /*cantPal.setOnAction(e->insertar("Cantidad de Palabras"));
        cantApo.setOnAction(e->insertar("Cantidad de Aportaciones"));
        cantAne.setOnAction(e->insertar("Cantidad de Anexos"));*/
        //root.setMinSize(400, 200); 
        root.setPadding(new Insets(10, 10, 10, 10)); 
        root.setVgap(5); 
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);
        //hBotones.getChildren().addAll(cantPal,cantApo,cantAne);
        hBotones.setAlignment(Pos.CENTER);
        root.add(hBotones,1,0);
        root.add(volver,0,5);
        root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        piePalabras = createPiePalabras("Cantidad de Palabras");
        pieAportaciones = createPieAportaciones("Cantidad de Aportaciones");
        root.add(piePalabras, 1, 3);
        root.add(pieAportaciones, 3, 3);
       
    }
    public PieChart createPiePalabras(String titulo) {
        for(Estudiante e: estudiantes){
            System.out.println(e.getNombre());
        }
        PieChart pie = new PieChart();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for(Estudiante e: contadorPalabras()){
            data.addAll(new PieChart.Data(e.getNombre(), e.getPalabras()));
        }
        pie.setData(data);
        pie.setTitle(titulo);
        pie.setLabelLineLength(5);
        pie.setLegendSide(Side.BOTTOM);
        return pie;
    }
    
    public PieChart createPieAportaciones(String titulo){
        PieChart pie = new PieChart();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> estudiante : aporteEstudiantes().entrySet()){
            data.addAll(new PieChart.Data(estudiante.getKey(), estudiante.getValue()));
        }
        pie.setData(data);
        pie.setTitle(titulo);
        pie.setLabelLineLength(5);
        pie.setLegendSide(Side.BOTTOM);
        return pie;
    }
    
    private void volver() {
        MainScreen m= new MainScreen(f);
        Scene s= new Scene(m.getroot(), 800, 500);
        Stage st=(Stage)root.getScene().getWindow();
       
        st.setScene(s);
    }
    
    public ArrayList<Estudiante> contadorPalabras(){
        boolean controlador = true;
        int contadorInicio = 0;
        int contadorFin = 0;
        int eliminado = 0;
        //Collections.reverse(versiones);
        //Collections.sort(versiones, (Version v2, Version v3) -> new Integer(v2.getTamañoVersion()).compareTo(new Integer(v3.getTamañoVersion())));
        for(Version v: versiones){
            String[] split = v.getVersionA().split(" ");
            if(controlador){
                contadorInicio = split.length;
                controlador = false;
            }else{
                contadorFin = split.length;
                for(Estudiante e: estudiantes){
                    if(v.getResponsable().equals(e.getNombre())){
                        int palabras = e.getPalabras();
                        if((contadorFin-contadorInicio) < 0){
                            e.setPalabras(palabras);
                            eliminado += contadorInicio-contadorFin;
                        }
                        else{
                            e.setPalabras((contadorFin-contadorInicio)+palabras);
                        }
                    }
                }
                //System.out.println("Persona: "+v.getResponsable()+" Palabras "+split.length);
                //System.out.println("Persona: "+v.getResponsable()+" ingreso "+(contadorFin-contadorInicio));
                contadorInicio = contadorFin;
            }
        }
        
        //Caso de estudiante retirado
        for(Estudiante e: estudiantes){
            if(e.getNombre().equals("KLEBER JONNATHAN PUMA ZARUMA")){
                e.setPalabras(e.getPalabras()-eliminado);
            }
            System.out.println("Estudiante:"+e.getNombre()+" Palabras:"+e.getPalabras());
        }
        return estudiantes;
    }
    
    public ArrayList<Estudiante> listaEstudiantes(){
        limpiarListaEstudiantes(estudiantesRepetidos());
        for(String estudiante: estudiantesRepetidos){
            estudiantes.add(new Estudiante(estudiante));
        }
        return estudiantes;
    }
    
    public void limpiarListaEstudiantes(ArrayList<String> lista){
        Set<String> hs = new HashSet<>();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
    }
    
    public ArrayList<String> estudiantesRepetidos(){
        for(Version v: versiones){
            estudiantesRepetidos.add(v.getResponsable());
        }
        return estudiantesRepetidos;
    }
    
    public Map<String, Integer> aporteEstudiantes(){
        System.out.println("Hole");
        Map<String, Integer> mapa = new HashMap();
        for(Version v: versiones){
            if(mapa.containsKey(v.getResponsable())){
                int contador = mapa.get(v.getResponsable());
                mapa.put(v.getResponsable(), contador+1);
                
            }else{
                mapa.put(v.getResponsable(), 1);
            }
        }
        
        for (Map.Entry<String, Integer> estudiante : mapa.entrySet()){
            String clave = estudiante.getKey();
            Integer valor = estudiante.getValue();
            System.out.println(clave+"  ->  "+valor);
        }
        return mapa;
    }
    
}
