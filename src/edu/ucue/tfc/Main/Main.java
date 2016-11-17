/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Main;
import edu.ucue.tfc.Dao.RegistrosDao;
import org.opencv.core.Core;

import edu.ucue.tfc.GUI.Principal;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 *
 * @author Paul
 */
public class Main {

    public static Object cargarDatos() {
        Object ob;
        try {
            FileInputStream fileIn = new FileInputStream("Registros.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ob = in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException i) {
            System.out.println("Archivo no existente aún");
            return null;
        } catch(ClassNotFoundException c) {
            System.out.println("Clase no encontrada");
            return null;
        }
        return ob;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary("opencv_ffmpeg310_64");
        
        Object obj = cargarDatos();
        if (obj!= null)
            RegistrosDao.getInstanciaRegistrosDao().setRegistrosRepositorio((Map) obj);
        
        Principal principal = new Principal();
        principal.setVisible(true);
    }
    
}
