/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import MODEL.Estudiante;
import MODEL.Version;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.ToolTipManager;

/**
 *
 * @author kevin
 */
public class GaleriaScreen {
    Button cantPal,cantApo,cantAne,volver;
    GridPane  root;
    HBox hBotones;
    File f;
    Label caption;
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
        root = new GridPane(); 
        hBotones= new HBox();
        /*cantPal.setOnAction(e->insertar("Cantidad de Palabras"));
        cantApo.setOnAction(e->insertar("Cantidad de Aportaciones"));
        cantAne.setOnAction(e->insertar("Cantidad de Anexos"));*/
        //root.setMinSize(400, 200); 
        root.setPadding(new Insets(10, 10, 10, 10)); 
        root.setVgap(10); 
        root.setHgap(10);
        caption= new Label("");
        caption.setTextFill(Color.WHITE);
        root.setAlignment(Pos.CENTER);
        //hBotones.getChildren().addAll(cantPal,cantApo,cantAne);
        hBotones.setAlignment(Pos.CENTER);
        root.add(hBotones,1,0);
        root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        piePalabras = createPiePalabras("Cantidad de Palabras");
        pieAportaciones = createPieAportaciones("Cantidad de Aportaciones");
        root.add(piePalabras, 1, 3);
        root.add(pieAportaciones, 3, 3);
        root.add(caption,1,0);
        Tooltip tool=new Tooltip("Da clic en el grafico para ver el porcentaje!");
        
        
        ToolTipManager.sharedInstance().setInitialDelay(1);
        tool.setFont(Font.font(15));
        Tooltip.install(root, tool);
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
        pie.setLabelsVisible(false);
        data.forEach(datos ->
                datos.nameProperty().bind(
                        Bindings.concat(datos.getName()+" ingresó "+datos.getPieValue()+" palabras")
                )
        );
        DoubleBinding total = Bindings.createDoubleBinding(() ->
        data.stream().collect(Collectors.summingDouble(PieChart.Data::getPieValue)), data);
        for (final PieChart.Data data1 : pie.getData()) {
        data1.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
            e -> {
                caption.setTranslateX(e.getSceneX()-10);
                caption.setTranslateY(e.getSceneY()-10);
                String text = String.format("%.1f%%", 100*data1.getPieValue()/total.get()) ;
                
                caption.setText(text);
             }
            );
    }
        pie.setLegendSide(Side.BOTTOM);
        return pie;
    }
    
    public PieChart createPieAportaciones(String titulo){
        TableView table = new TableView();
        PieChart pie = new PieChart();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> estudiante : aporteEstudiantes().entrySet()){
            data.addAll(new PieChart.Data(estudiante.getKey(), estudiante.getValue()));
        }
        
        pie.setData(data);
        
        pie.setLabelsVisible(true);
        pie.setTitle(titulo);
        data.forEach(datos ->
                datos.nameProperty().bind(
                        Bindings.concat(datos.getName()+":  "+datos.getPieValue()+" Aportaciones")
                )
        );
        DoubleBinding total = Bindings.createDoubleBinding(() ->
        data.stream().collect(Collectors.summingDouble(PieChart.Data::getPieValue)), data);
        for (final PieChart.Data data1 : pie.getData()) {
        data1.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
            e -> {
                caption.setTranslateX(e.getSceneX()-10);
                caption.setTranslateY(e.getSceneY()-10);
                String text = String.format("%.1f%%", 100*data1.getPieValue()/total.get()) ;
                caption.setText(text);
             }
            );
    }
        pie.setLegendSide(Side.BOTTOM);
        return pie;
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
            //System.out.println("Estudiante:"+e.getNombre()+" Palabras:"+e.getPalabras());
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
            //System.out.println(clave+"  ->  "+valor);
        }
        return mapa;
    }
    
}
