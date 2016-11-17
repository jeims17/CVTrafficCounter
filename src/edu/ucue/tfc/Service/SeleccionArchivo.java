/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Service;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Paul
 */
public class SeleccionArchivo {
    
    JFileChooser chooser = new JFileChooser();

    public void configurarFileChooser()
    {
            chooser.setDialogTitle("Seleccione su video");
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter aviFilter = new FileNameExtensionFilter(".avi","avi");
            FileNameExtensionFilter mp4Filter = new FileNameExtensionFilter(".mp4","mp4");
            FileNameExtensionFilter pngFilter = new FileNameExtensionFilter(".png","png");

            chooser.setFileFilter(mp4Filter);
            chooser.addChoosableFileFilter(aviFilter);
            chooser.addChoosableFileFilter(pngFilter);
    }

    public String getPathArchivo(Component parent)
    {
            configurarFileChooser();
            int returnVal = chooser.showOpenDialog(parent);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                File archivo = chooser.getSelectedFile();
                if (archivo != null)
                {
                    System.out.println("Archivo cargado satisfactoriamente!");
                    return archivo.toString();
                }
                else
                {
                    System.out.println("No hay un archivo seleccionado!");
                    return null;
                }
            }
            else
            {
                System.out.println("No hay un archivo seleccionado!");
                return null;
            }      
    }
    
}
