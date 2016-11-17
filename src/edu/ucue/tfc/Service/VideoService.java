/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Service;

import edu.ucue.tfc.Dao.RegistrosDao;
import edu.ucue.tfc.Modelo.ControladorVideo;
import edu.ucue.tfc.Modelo.Video;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Paul
 */
public class VideoService {
    
    public static void guardarRegistro(Video video) {
        RegistrosDao.getInstanciaRegistrosDao().addRegistro(video);
    }
    
    public static Video getRegistro(String filePath) {
        return RegistrosDao.getInstanciaRegistrosDao().getRegistro(filePath);
    }
    
    public static void eliminarRegistro(String filePath) {
        RegistrosDao.getInstanciaRegistrosDao().eliminarRegistro(filePath);
    }
    
    public static Map<String, Video> getMapRegistros() {
        return RegistrosDao.getInstanciaRegistrosDao().getRegistros();
    }
    
    public static Collection<Video> getVideos() {
        return RegistrosDao.getInstanciaRegistrosDao().getVideos();
    }
    
    public static void setControladorVideo(ControladorVideo controladorVideo) {
        RegistrosDao.getInstanciaRegistrosDao().setControladorVideo(controladorVideo);
    }
    
    public static ControladorVideo getControladorVideo() {
        return RegistrosDao.getInstanciaRegistrosDao().getControladorVideo();
    }
    
    public static String getRegistros() {
        StringBuilder str = new StringBuilder();
        Collection<Video> videos = RegistrosDao.getInstanciaRegistrosDao().getVideos();
        System.out.println(videos.size());
        str.append("\n*******************************\n");
        for (Video video : videos) {
            str.append(video.toString());
            str.append("\n\n*******************************\n");
        }
        return str.toString();
    }
    
    public static int[][] getTabla(String filePath) {
        return RegistrosDao.getInstanciaRegistrosDao().getTablaValores(filePath);
    }
}
