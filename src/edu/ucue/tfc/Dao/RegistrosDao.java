/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Dao;

import edu.ucue.tfc.Modelo.ControladorVideo;
import edu.ucue.tfc.Modelo.Video;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Paul
 */
public class RegistrosDao implements Serializable {
    
    private Map<String, Video> registrosRepositorio;
    ControladorVideo controladorVideo = null;
    private static RegistrosDao instanciaRegistros = null;

    private RegistrosDao() {
        registrosRepositorio = new HashMap<String, Video>();
    }
    
    public static RegistrosDao getInstanciaRegistrosDao() {
        if (instanciaRegistros == null) {
            instanciaRegistros = new RegistrosDao();
        }
        return instanciaRegistros;
    }
    
    public void addRegistro(Video video) {
        registrosRepositorio.put(video.getFilePath(), (Video) video.clone());
        guardarDatos();
    }
    
    public Video getRegistro(String filePath) {
        return registrosRepositorio.get(filePath);
    }

    public void setControladorVideo(ControladorVideo controladorVideo) {
        this.controladorVideo = controladorVideo;
    }

    public ControladorVideo getControladorVideo() {
        return controladorVideo;
    }
    
    public void eliminarRegistro(String filePath) {
        registrosRepositorio.remove(filePath);
        guardarDatos();
    }
    
    public Collection<Video> getVideos() {
        return registrosRepositorio.values();
    }
    
    public int[][] getTablaValores(String filePath) {
        double suma = 0;
        int vehiculos = 0;
        int index = 0;
        int tam = registrosRepositorio.get(filePath).getVehiculos().size();
        double factor = (registrosRepositorio.get(filePath).getTiempo()/10);
        
        int tabla[][] = new int[10][2];
        
        for (int i=0; i<10; i++) {
            tabla[i][0] = (int) suma;
            suma += factor;
            
            while (index < tam && registrosRepositorio.get(filePath).getVehiculos().get(index++) < suma) {
                vehiculos++;
            }
            
            tabla[i][1] = vehiculos;
            vehiculos = 0;
        }
        return tabla;
    }

    public void setRegistrosRepositorio(Map<String, Video> registrosRepositorio) {
        this.registrosRepositorio = registrosRepositorio;
    }
    
    public Map<String, Video> getRegistros() {
        return registrosRepositorio;
    }
    
    public void guardarDatos() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Registros.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(getRegistros());
            out.close();
            fileOut.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
    
    public Object cargarDatos() {
        Object ob;
        try {
            FileInputStream fileIn = new FileInputStream("Registros.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ob = in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
            return null;
        }
        return ob;
    }
    
}
