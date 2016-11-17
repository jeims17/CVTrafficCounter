/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Modelo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Paul
 */
public class Video implements Cloneable, Serializable {
    
    private int nVehiculos;
    private int carrosMinuto;
    private Double NumeroFrames;
    private Double duracion;
    private Double FPS;
    private String filePath;
    private String ciudad;
    private String calle;
    private String Extension;
    private String tiempo;
    private List<Double> vehiculos;

    public Video(int nVehiculos, int carrosMinuto, Double NumeroFrames, Double duracion, Double FPS, 
                    String filePath, String ciudad, String calle, String Extension, String tiempo) {
        this.NumeroFrames = NumeroFrames;
        this.carrosMinuto = carrosMinuto;
        this.nVehiculos = nVehiculos;
        this.duracion = duracion;
        this.FPS = FPS;
        this.filePath = filePath;
        this.ciudad = ciudad;
        this.calle = calle;
        this.Extension = Extension;
        this.tiempo = tiempo;
        this.vehiculos = new ArrayList<Double>();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public int getnVehiculos() {
        return nVehiculos;
    }

    public Double getTiempo() {
        return duracion;
    }

    public int getCarrosMinuto() {
        return carrosMinuto;
    }

    public void setCarrosMinuto(int carrosMinuto) {
        this.carrosMinuto = carrosMinuto;
    }
    
    public void setnVehiculos(int nVehiculos) {
        this.nVehiculos = nVehiculos;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }
         
    public List<Double> getVehiculos() {
        return vehiculos;
    }
    
    public void addVehiculo(double t) {
        vehiculos.add(t);
    }
    
    @Override
    public String toString() {
        return "\n Archivo:\t" + filePath + "\n" +
               "\n Ciudad:\t" + ciudad + "\n" +
               "\n Calle:\t" + calle + "\n" +
               "\n Duración:\t" + tiempo + "\n" +
               "\n Cuadros por Segundo: " + String.valueOf(FPS) + "\n" +
               "\n Número de Vehículos:  " + String.valueOf(nVehiculos) + "\n" +
               "\n Vehículos por minuto:   " + String.valueOf(carrosMinuto);
    }
    
    @Override
    public Object clone(){
        Object obj = null;
        try {
            obj = super.clone();
        } catch(CloneNotSupportedException ex){
            System.out.println(" No se puede duplicar");
        }
        return obj;
    }
    
}
