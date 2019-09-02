/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

/**
 *
 * @author Axel
 */
public class Version {
    private String no;
    private String responsable;
    private String fecha;
    private String cambios;
    private String versionA;
    private int tamañoVersion;

    public String getSinEditar() {
        return sinEditar;
    }

    public void setSinEditar(String sinEditar) {
        this.sinEditar = sinEditar;
    }
    private String sinEditar;

    public Version() {
    }

    public Version(String no, String responsable, String fecha, String cambios, String versionA, String sinEditar) {
        this.no = no;
        this.responsable = responsable;
        this.fecha = fecha;
        this.cambios = cambios;
        this.versionA = versionA;
        this.sinEditar =sinEditar;
    }
    

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCambios() {
        return cambios;
    }

    public void setCambios(String cambios) {
        this.cambios = cambios;
    }

    public String getVersionA() {
        return versionA;
    }

    public void setVersionA(String versionA) {
        this.versionA = versionA;
    }

    public int getTamañoVersion() {
        return getVersionA().split(" ").length;
    }

    public void setTamañoVersion(int tamañoVersion) {
        this.tamañoVersion = tamañoVersion;
    }

    @Override
    public String toString() {
        return "Version{" + "no=" + no + ", responsable=" + responsable + ", fecha=" + fecha +"Cambios. "+ cambios + ", versionA=" + versionA + '}'+"\n";
    }
    
    
    
    
}
