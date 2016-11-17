/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
 
/**
 *
 * @author paul
 */
public class BackgroundImage implements Border{
    public BufferedImage back;
 
    public BackgroundImage(){
        try {
            URL imagePath = new URL("C:\\Users\\Paul\\Documents\\NetBeansProjects\\CVTrafficCounter\\src\\edu\\ucue\\tfc\\GUI\\icons\\j.jpg");
            back = ImageIO.read(imagePath);
        } catch (Exception ex) {
             
        }
    }
 
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(back,0,0,1920,1080,null);
        //g.drawImage(back, (x + (width - back.getWidth())/2),(y + (height - back.getHeight())/2), null);
    }
 
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
 
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
 
}