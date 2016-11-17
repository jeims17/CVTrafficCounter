/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Service;

import java.io.File;

/**
 *
 * @author Paul
 */
public class NombreArchivo
{
    public static String getCiudad(String pathArchivo)
    {
        String nombreArchivo = new File(pathArchivo).getName();

        if (nombreArchivo.indexOf("_") == -1)
        {
            System.out.println("Ciudad no especificada!");
            return "Ciudad no especificada";
        }
        return nombreArchivo.substring(0, nombreArchivo.indexOf("_"));
    }

    public static String getCalle(String pathArchivo)
    {
        String nombreArchivo = new File(pathArchivo).getName();

        if (nombreArchivo.indexOf("_") == -1 || ((nombreArchivo.indexOf("_") +1) > nombreArchivo.lastIndexOf(".")) )
        {
            System.out.println("Calle no especificada!");
            return "Calle no especificada";
        }
        return nombreArchivo.substring(nombreArchivo.indexOf("_")+1, nombreArchivo.lastIndexOf("."));
    }

    public static String getNombreArchivo(String pathArchivo)
    {
        return pathArchivo == null ? null : new File(pathArchivo).getName();
    }

    public static String getExtension(String pathArchivo)
    {
        String nombreArchivo = new File(pathArchivo).getName();
        return nombreArchivo.substring(nombreArchivo.lastIndexOf(".")+1);
    }
}
