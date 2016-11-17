/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Modelo;

import edu.ucue.tfc.GUI.RegistroVideo;
import edu.ucue.tfc.Service.NombreArchivo;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul
 */
public class ControladorVideo {
    
    Component panel = null;
    
    Video video = null;
    
    private VideoProcessor videoProcessor = new VideoProcessor();
    private Timer timer = new Timer();
    
    String pathArchivo;
    
    private List<Double> cars = new ArrayList<Double>();
    
    private int trafficCount;
   
    private boolean pauseVideo = false;
    private boolean startButtonClicked = false;
    private boolean otherFileSelected = false;
    private String currentlyPlaying;

    public ControladorVideo(Component panel) {
        this.panel = panel;
    }

    public String getPathArchivo() {
        return pathArchivo;
    }

    public void setPathArchivo(String pathArchivo) {
        this.pathArchivo = pathArchivo;
    }

    public boolean isPauseVideo() {
        return pauseVideo;
    }

    public void setPauseVideo(boolean pauseVideo) {
        this.pauseVideo = pauseVideo;
    }

    public boolean isStartButtonClicked() {
        return startButtonClicked;
    }

    public void setStartButtonClicked(boolean startButtonClicked) {
        this.startButtonClicked = startButtonClicked;
    }

    public boolean isOtherFileSelected() {
        return otherFileSelected;
    }

    public void setOtherFileSelected(boolean otherFileSelected) {
        this.otherFileSelected = otherFileSelected;
    }

    public String getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(String currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public VideoProcessor getVideoProcessor() {
        return videoProcessor;
    }

    public Video getVideo() {
        return video;
    }

    public void stop() {
        timer.cancel();
	videoProcessor.getVideoCap().release();   
    }
    
    public void setTrafficCount(int trafficCount) {
        this.trafficCount = trafficCount;
    }
    
    public int loadVideo() throws IOException {
        if (pathArchivo != null) {
            if (videoProcessor.initVideo(pathArchivo) == 0) {
                Image im = videoProcessor.getImageAtPos(1);
                BufferedImage buff = (BufferedImage) im;
                Graphics g = panel.getGraphics();

                if (g.drawImage(buff, 0, 0, 640, 480, 0, 0, buff.getWidth(), buff.getHeight(), null))

                System.out.println("Video " + pathArchivo + " Cargado!");
                
                videoProcessor.setDetectedCarsCount(0);
                setTrafficCount(0);
                video = new Video(0, 0, videoProcessor.getFrameCount(),
                                        videoProcessor.getSeconds(),
                                        videoProcessor.getFPS(),
                                        videoProcessor.getFileName(),
                                        NombreArchivo.getCiudad(pathArchivo), 
                                        NombreArchivo.getCalle(pathArchivo),
                                        NombreArchivo.getExtension(pathArchivo),
                                        videoProcessor.getLengthFormatted());
                return 0;
            }
        } else if (pathArchivo == null) {
            System.out.println("No se ha seleccionado un archivo!");
            return 1;
        }
        return 0;
    }
    
    public void startProcessing(RegistroVideo component) {
        TimerTask frame_grabber = new TimerTask() {
            Image tmp;

            @Override
            public void run() {
                if (!pauseVideo) {
                    videoProcessor.processVideo();
                    
                    if (videoProcessor.isWasDetected()) {
                        video.addVehiculo(videoProcessor.getSecondsActualFrame());
                        videoProcessor.setWasDetected(false);
                    }
                    
                    videoProcessor.writeOnFrame("Carros detectados: " + videoProcessor.getDetectedCarsCount());
                    video.setnVehiculos(videoProcessor.getDetectedCarsCount());
                    video.setCarrosMinuto(videoProcessor.getCarsPerMinute());
                    
                    try {
                        tmp = videoProcessor.convertCvMatToImage();
                        
                        component.setLabelProgreso(videoProcessor.getActualLengthFormatted());
                        component.setBarrarProgreso((int) ((videoProcessor.getFramePos() / videoProcessor.getFrameCount()) * 100));
                    } catch (IOException ex) {
                        Logger.getLogger(RegistroVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (videoProcessor.isFinished()) {
                        
                            System.out.println(currentlyPlaying + ": " + videoProcessor.getDetectedCarsCount() + " carros detectados, "
                                            + videoProcessor.getCarsPerMinute() + " carros por minuto.");
                            
                            video.setnVehiculos(videoProcessor.getDetectedCarsCount());
                            video.setCarrosMinuto(videoProcessor.getCarsPerMinute());
                            setTrafficCount(0);
                            videoProcessor.setDetectedCarsCount(0);
                            videoProcessor.setFinished(false);
                            
                            component.setBtnReproducirText("Reproducir");
                            component.setEnableBtnReproducir(false);
                            pauseVideo = true;
                            startButtonClicked = false;
                            stop();

                            component.setEnableBtnReproducir(false);
                            component.setEnableBtnParar(false);
                            component.setEnableBtnGuardar(true);
                            component.setEnableBtnAbrir(true);
                            component.finVideo();
                    } else {
                        
                    }
                    component.setTexto(video.toString());
                    BufferedImage buff = (BufferedImage) tmp;
                    Graphics g = panel.getGraphics();

                    if (g.drawImage(buff, 0, 0, 640, 480, 0, 0, buff.getWidth(), buff.getHeight(), null));
                }
            }
        };
        timer = new Timer();

        System.out.println(videoProcessor.getFPS());
        System.out.println(videoProcessor.getFrameCount());
        Double period = 1000 / videoProcessor.getFPS() * 1.5;
        System.out.println(period.longValue());
        this.timer.schedule(frame_grabber, 0, period.longValue());

    }
}
