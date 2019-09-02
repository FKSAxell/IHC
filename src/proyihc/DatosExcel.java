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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.scene.paint.Color;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

/**
 *
 * @author Leonardo
 */
public class DatosExcel {
    final ArrayList<Estudiante> estudiantes = new ArrayList<>();
    ArrayList<String> estudiantesRepetidos = new ArrayList<>();
    ArrayList<Version> versiones = new ArrayList<>();
    private File f;

    public DatosExcel(File f) {
        this.f = f;
    }
    
    public ArrayList<Version> leerexcel(){
        try {
            FileInputStream fis= new FileInputStream(f);
            XSSFWorkbook wb= new XSSFWorkbook(fis);
            XSSFSheet sheet= wb.getSheetAt(0);
            Iterator rowit= sheet.rowIterator();
            //DOS PRIMERAS NO USAR
            rowit.next();
            rowit.next();
            while (rowit.hasNext()) {
                XSSFRow row =(XSSFRow) rowit.next();
                String cambios="";
                String versionA="";
                estudiantesRepetidos.add(row.getCell(0).toString());
                if(row.getCell(3)!=null){
                    cambios=row.getCell(3).toString();
                }
                if(row.getCell(4)!=null){
                    versionA=row.getCell(4).toString();
                }
                versiones.add(new Version(row.getCell(1).toString(), row.getCell(0).toString(), row.getCell(2).toString()
                        ,Jsoup.parse(cambios).wholeText(),Jsoup.parse(versionA).wholeText()));
            }
            //listaEstudiantes();
            //recorrerVersiones();
        } catch (Exception e) {
        }
        
        return versiones;
    }
    
    //Generamos lista con los estudiantes que participaron en la página
    public void limpiarListaEstudiantes(ArrayList<String> lista){
        Set<String> hs = new HashSet<>();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
    }
    
    public ArrayList<Estudiante> listaEstudiantes(){
        limpiarListaEstudiantes(estudiantesRepetidos);
        for(String estudiante: estudiantesRepetidos){
            estudiantes.add(new Estudiante(estudiante));
        }
        return estudiantes;
    }
}
