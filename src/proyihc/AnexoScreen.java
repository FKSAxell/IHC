/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyihc;

import MODEL.Estudiante;
import MODEL.Version;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
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
import javax.swing.ToolTipManager;

/**
 *
 * @author kevin
 */
class AnexoScreen {
    Button cantPal,cantApo,cantAne,volver;
    GridPane  root;
    HBox hBotones,usuarios,hFotos;
    Label titulo;
    File f;
    Label caption;
    PieChart piePalabras, pieAportaciones;
    ArrayList<Estudiante> estudiantes;
    DatosExcel datosE;
    ArrayList<Version> versiones;
    ArrayList<String> estudiantesRepetidos;
    Button estudianteB;
    VBox todo;
    ScrollPane sp;
    

    public AnexoScreen(File f){
        this.f=f;
        datosE = new DatosExcel(f);
        versiones = datosE.leerexcel();
        estudiantesRepetidos = new ArrayList<>();
        estudiantes = new ArrayList<>();
        estudiantesRepetidos();
        listaEstudiantes();
        setearUrls();
        descargaFotos();
        
        InicializarComponentes();
        
        
    }
    public Parent getroot(){
        return todo;
    }
    public void InicializarComponentes(){
        /*cantPal= new Button("Cantidad de Palabras");
        cantApo= new Button("Cantidad de Aportaciones");
        cantAne= new Button("Cantidad de Anexos");*/

        
       
//        System.out.println(estudiantes.get(3).getUrls().get(0));
        volver= new Button("Volver");
        volver.setTextFill(Color.WHITE);
        volver.setStyle("-fx-background-color: #483D8B");
        volver.setTextFill(Color.WHITE);
        volver.setFont(new Font(20));
                volver.setStyle("-fx-background-color: #483D8B");
//                node.setStyle(value);
                volver.setOnMouseEntered(e->{
                    
                   
                   volver.setEffect(new Glow(0.5));
                });
                volver.setOnMouseExited(e->{
                    volver.setFont(new Font(20));
                    volver.setEffect(null);
                });
        todo=new VBox();
        root = new GridPane(); 
        hBotones= new HBox();
        volver.setOnAction(e-> volver());
        /*cantPal.setOnAction(e->insertar("Cantidad de Palabras"));
        cantApo.setOnAction(e->insertar("Cantidad de Aportaciones"));
        cantAne.setOnAction(e->insertar("Cantidad de Anexos"));*/
        //root.setMinSize(400, 200); 
        root.setPadding(new Insets(10, 10, 10, 10)); 
        root.setVgap(10); 
        root.setHgap(10);
        hFotos= new HBox();
        usuarios = new HBox();
        root.add(hFotos, 1, 2);
        titulo = new Label();
        titulo.setFont(new Font(20));
        int contUrl=0;
        for(Estudiante est: estudiantes){
            if(est.getUrls().size()>0){
                estudianteB = new Button(est.getNombre());
                estudianteB.setOnAction(e->colocarFotos(est));
                usuarios.getChildren().addAll(estudianteB);
                contUrl++;
                      
            }
            
        }
        if(contUrl ==0){
            /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.setTitle("NO HAY ANEXOS");
            alert.setHeaderText("");
            alert.setContentText("No existen anexos en el presente documento");
            


            alert.showAndWait();*/
            titulo.setText("No hay Anexos que mostrar");
            
            
        }else{
            inicializarFotos();
        }
        
        todo.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        //hBotones.getChildren().addAll(cantPal,cantApo,cantAne);
        hBotones.setAlignment(Pos.CENTER);
        root.add(hBotones,1,0);
        
        todo.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        todo.getChildren().addAll(titulo,usuarios,root,volver);
        

    }
     private void volver() {
        MainScreen m= new MainScreen(f);
        Scene s= new Scene(m.getroot(), 1200, 600);
        Stage st=(Stage)todo.getScene().getWindow();
        st.setScene(s);
    }
    private void colocarFotos(Estudiante est){
        root.getChildren().remove(sp);
        hFotos= new HBox();
        sp= new ScrollPane();
        titulo.setText("Anexos de "+est.getNombre());
        for (int i = 0; i<est.getUrls().size(); i++){
            Image image = new Image("File:./src/img/"+est.getNombre().split(" ")[2]+ Integer.toString(i)+".jpg");
            ImageView imageV= new ImageView(image);
             imageV.setFitHeight(250);
             imageV.setFitWidth(300);
            
            hFotos.getChildren().add(imageV);      
        }
        sp.setMinHeight(500);
        sp.setContent(hFotos);
        sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        sp.setVbarPolicy(ScrollBarPolicy.NEVER);
        root.add(sp, 1, 2);
        
        
    }
    private void inicializarFotos(){
        sp= new ScrollPane();
        hFotos= new HBox();
        titulo.setText("Todos los Anexos");
        for (Estudiante est: estudiantes){
            String nombre= est.getNombre().split(" ")[2];
            int idFoto=0;
            for (int i = 0; i<est.getUrls().size(); i++){
                Image image = new Image("File:./src/img/"+est.getNombre().split(" ")[2]+ Integer.toString(i)+".jpg");
                ImageView imageV= new ImageView(image);
                imageV.setFitHeight(250);
                imageV.setFitWidth(300);
                hFotos.getChildren().add(imageV);
                
            }
        }
        sp.setContent(hFotos);
        sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        sp.setVbarPolicy(ScrollBarPolicy.NEVER);
        root.add(sp, 1, 2);
        
    }
    private void descargaFotos(){
        for (Estudiante est : estudiantes){
            String nombre= est.getNombre().split(" ")[2];
            int idFoto=0;
            for (String urlR: est.getUrls()){
                try {
                // Url con la foto
                //URL url = new URL(
                  //              "https://www.sidweb.espol.edu.ec/groups/49627/files/2041714/download?download_frd=1");
                URL url = new URL(urlR);
                // establecemos conexion
                URLConnection urlCon = url.openConnection();
                urlCon.setRequestProperty("Cookie","_csrf_token=fz0Y4npHh95H4tr2oDnJgZjwRHYoN6O92jZEEjAal%2F0pSyyUSQDUvRCBsYLBDfHqypYcGVB489KPTyxjd0DxsA%3D%3D;"
                        + "_normandy_session=f_pjLN7t34gVyhWgrXqdRA+4ifnT65s_p0DCqKSpC5VY3VfU3x-3RxqHcPrg01_-0zm7s5vJJOOoHMgBkjgqMkGbica9NI12UHK_rZRy23FaNxVgQcCp_jm42nrZTzhsW7Zgmz8b2DIrLRyBd2CqHZNla-tbTdbLDCAZn9nK5tnwXfiUYFtcwnRGoRcMK1x4rVTVNjoMXSOG88EiNGotVBfWRnA1IJDGXzqLREZK856MWinIsoCuUGSsuAYyI4YyOid4OJNGtBSlY49trKKDu1TSkzAMTJo7nx8VHrJLVLWdGgU-k3S3tjh8JuJKuv3hRjUM5bEUhk_X2Ktli1ss5O1OE1bhjloCE6kOgHPH9yg7NNkCepR-3R7TG1o_i51V04.Tzmkoa8Up2Rq8SufP78OtTz8pXk.XWyClg;"
                        + "log_session_id=1664404c9cf4a101002b05eef74a8435;"
                        );

                // Sacamos por pantalla el tipo de fichero
                
                // Se obtiene el inputStream de la foto web y se abre el fichero
                // local.
                InputStream is = urlCon.getInputStream();
                FileOutputStream fos = new FileOutputStream("./src/img/"+nombre + Integer.toString(idFoto)+".jpg");
                idFoto++;

                // Lectura de la foto de la web y escritura en fichero local
                byte[] array = new byte[1000]; // buffer temporal de lectura.
                int leido = is.read(array);
                while (leido > 0) {
                        fos.write(array, 0, leido);
                        leido = is.read(array);
                }

                // cierre de conexion y fichero.
                is.close();
                fos.close();
            } catch (Exception e) {
                    e.printStackTrace();
            }

            }
        } 
    }
    public ArrayList<Estudiante> setearUrls(){
        boolean controlador = true;
        int contadorInicio = 0;
        int contadorFin = 0;
        int eliminado = 0;
        String url;
        for(Version v: versiones){
            String[] primer = v.getSinEditar().split("src=");
            if (primer.length >1){
                for (int i = 1; i<primer.length; i++){
                    String src = primer[i];
                    String[] segundo = src.split(" ");
                    //src = src.replace(" ", "-");
                    url =segundo[0];
                    url = url.replace("\"", "");
                    url= url.replace("preview", "");
                    url = "https://www.sidweb.espol.edu.ec"+ url +"download?download_frd=1";
                    for(Estudiante e: estudiantes){
                        if(v.getResponsable().equals(e.getNombre())){  
                            ArrayList<String> arreglo = e.getUrls();
                            arreglo.add(url);
                            e.setUrls(arreglo);
                           
                        }
                    }

                } 
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
}
