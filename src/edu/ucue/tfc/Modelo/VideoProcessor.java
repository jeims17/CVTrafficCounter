/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.Modelo;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.awt.Image;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Paul
 */
public class VideoProcessor {
    
    static
    {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private VideoCapture video =  new VideoCapture();
    
    private Image image = null;
    private boolean finished = false;
    
    private Mat frame = new Mat();
    
    private Mat firstGrayImage = new Mat();
    private Mat secondFrame = new Mat();
    private Mat secondGrayImage = new Mat();
    private Mat differenceOfImages = new Mat();
    private Mat thresholdImage = new Mat();
    private Mat hierarchy = new Mat();
    private Rect boundingRectangle = new Rect();
    
    private String fileName;
    private List<Point> controlPoints = new ArrayList<Point>();
    private int controlPointsHeight = 300; 
    private int previousControlPointsHeight;
    private boolean wasAtCenterPoint = false;
    private boolean wasAtLeftPoint = false;
    private boolean wasAtRightPoint = false;
    private boolean wasDetected = false;
    
    private int carsPerMinute;
    private int minutes;
    private int seconds;
    private int hours;
    
    private MatOfByte buffer = new MatOfByte();
    private final MatOfInt params = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 20);
        
    private final Point textPosition = new Point(10, 15);
    private final Scalar fontColor =   new Scalar(0, 0, 255);
    
    private final Size frameSize = new Size(640, 480);
    private List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    private List<MatOfInt> hullPoints = new ArrayList<MatOfInt>();
    
    private int frameCounter = 0;
    private int detectedCarsCount = 0;
    
    private final Rect imageArea = new Rect(15, 15, 640, 480);
    
    public VideoProcessor()
    {
        initControlPoints();
    }

    public boolean isOpened()
    {
        return video.isOpened();
    }
    
    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }
    
    public int getDetectedCarsCount()
    {
        return detectedCarsCount;
    }

    public double getFrameCount()
    {
        return (getVideoCap().isOpened() ? getVideoCap().get(7) : 0);
    }

    public int getFramePos()
    {
        return (getVideoCap().isOpened() ? (int) getVideoCap().get(1) : 0);
    }

    public Mat getFrame()
    {
        return this.frame;
    }

    public double getFPS()
    {
        return (getVideoCap().isOpened() ?  getVideoCap().get(5) : 1);
    }

    public int getMinutes()
    {
        return minutes;
    }
    
    public int getControlPointsHeight()
    {
        return controlPointsHeight;
    }

    public int getPreviousControlPointsHeight()
    {
        return previousControlPointsHeight;
    }

    public void setPreviousControlPointsHeight(int previousControlPointsHeight)
    {
        this.previousControlPointsHeight = previousControlPointsHeight;
    }
    
    public int getCarsPerMinute()
    {
        return carsPerMinute;
    }
  
    public void setCarsPerMinute(int carsPerMinute)
    {
        this.carsPerMinute = carsPerMinute;
    }
    
    public void setDetectedCarsCount(int detectedCarsCount)
    {
        this.detectedCarsCount = detectedCarsCount;
    }

    public Rect getImageArea()
    {
        return imageArea;
    }

    public double getHeightOfAControlPoint()
    {
        return controlPoints.get(6).y;
    }

    public boolean isWasDetected() {
        return wasDetected;
    }

    public void setWasDetected(boolean wasDetected) {
        this.wasDetected = wasDetected;
    }
    
    public String getFileName()
    {
        return fileName;
    }
        
    public VideoCapture getVideoCap()
    {
        return this.video;
    }

    public Double getSecondsActualFrame() {
        return (getFramePos() / getFrameCount()) * (getFrameCount() / getFPS());
    }
    
    public void setHeightOfTheControlPoints(double height)
    {
        controlPoints.get(6).y = height;
        controlPoints.get(7).y = height;
        controlPoints.get(8).y = height;
    }
    
    public void setFramePos(double pos)
    {
        if (pos >= 0 && pos <= getFrameCount())
        {
            try
            {
                getVideoCap().set(1, (int)pos);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("Número posicional invalido!");
        }
    }

    public void writeOnFrame(String text)
    {
        Imgproc.putText(getFrame(), text, textPosition , Core.FONT_HERSHEY_SIMPLEX , 0.7, fontColor, 2);
    }

    public Double getSeconds() {
        return getFrameCount() / getFPS();
    }

    public String getActualLengthFormatted()
    {
        int minutes, hours;
        int seconds = getSecondsActualFrame().intValue();
        minutes = (int)(seconds / 60);
        hours = (int)(minutes / 60);

        minutes %= 60;
        seconds %= 60;
        
        return  (hours > 9 ? hours : "0" + hours)      + ":" +
                (minutes > 9 ? minutes : "0"+ minutes) + ":" +
                (seconds > 9 ? seconds : "0"+ seconds);
    }
    
    public String getLengthFormatted()
    {
        seconds = (int)(getFrameCount() / getFPS());
        minutes = (int)(seconds / 60);
        hours = (int)(minutes / 60);

        minutes %= 60;
        seconds %= 60;
        
        return  (hours > 9 ? hours : "0" + hours)      + ":" +
                (minutes > 9 ? minutes : "0"+ minutes) + ":" +
                (seconds > 9 ? seconds : "0"+ seconds);
    }
    
    private void resetCheckPoints()
    {
        wasAtCenterPoint = false;
        wasAtLeftPoint = false;
        wasAtRightPoint = false;
    }
    
    private void initControlPoints()
    {
        try
        {
            controlPoints.add(new Point(80,100));
            controlPoints.add(new Point(80,frameSize.height - 100));

            controlPoints.add(new Point(frameSize.width / 2,100));
            controlPoints.add(new Point(frameSize.width / 2,frameSize.height - 100));

            controlPoints.add(new Point(frameSize.width - 80,100));
            controlPoints.add(new Point(frameSize.width - 80,frameSize.height - 100));

            controlPoints.add(new Point(80, controlPointsHeight));
            controlPoints.add(new Point(frameSize.width - 80,controlPointsHeight));

            controlPoints.add(new Point(frameSize.width / 2 ,controlPointsHeight));

            System.out.println("Puntos de control inicializados satisfactoriamente!");

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public Image convertCvMatToImage(Mat frameToConvert) throws IOException
    {
            if (!buffer.empty())
            {
                buffer.release();
            }
            try
            {
                Imgproc.resize(frameToConvert, frameToConvert, frameSize);
                Imgcodecs.imencode(".png", frameToConvert, buffer, params);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            image = ImageIO.read(new ByteArrayInputStream(buffer.toArray()));
            if (!frameToConvert.empty()) {
                frameToConvert.release();
            }

            return image;
    }
    
    public Image convertCvMatToImage() throws IOException
    {
        return convertCvMatToImage(getFrame());
    }
    
    public Image getImageAtPos(int pos) throws IOException
    {
        if (video.isOpened())
        {
            if (pos < getFrameCount() && pos > 0)
            {
                setFramePos(pos);
                Mat tmp = new Mat();
                video.retrieve(tmp);
                setFramePos(0);
                try
                {
                    Imgproc.resize(tmp, tmp, frameSize);
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
                return convertCvMatToImage(tmp);
            }  
            else
            {
                return getImageAtPos(1);
            }
        }
        else
        {
            System.out.println("VideoCapture no está abierto!");
            return null;
        }
    }
    
    public int initVideo(String filename)
    {
        if (filename != null)
        {
            try
            {
                video.open(filename);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if (video.isOpened())
            {
                resetCheckPoints();

                fileName = filename;
                finished = false;
                frameCounter = 0;
                return 0;
            }
            System.out.println("No se pudo abrir el VideoCapture!");
            return 1;
        }
        else
        {
            System.out.println("Archivo null!");
            return 1;
        }
    }
    
    public void processVideo()
    {
        do
        {
            Mat tmp = new Mat();
            video.read(tmp);
            if (!tmp.empty())
            {
                frame = tmp.clone();
                tmp.release();
                if (frameCounter < (getFrameCount()/2) -1 )
                {
                    frameCounter++;
                    if (getMinutes() > 0)
                    {
                        carsPerMinute = getDetectedCarsCount() / getMinutes();
                    }

                    processFrame(getFrame());
                }
                else
                {
                    frameCounter = 0;
                    finished = true;

                    System.out.println("Reiniciando..");
                    setFramePos(1);
                }
            }
            else
            {
                System.out.println("Imagen Vacía");
                frameCounter = 0;
                finished = true;

                System.out.println("Reiniciando..");
                setFramePos(1);
            }
        } while (frameCounter > (getFrameCount()/2) - 2);
    }
    
    /**
	 * Processes {@code firstFrame} and {@code secondFrame}.
	 * @param firstFrame 	the first frame of a cycle.
	 */
    private void processFrame(Mat firstFrame)
    {
        double contourArea = 0;
        int position = 0;
        try
        {
            /**
             * Redimensiona el el cuadro actual
             *
             */
            Imgproc.resize(firstFrame, firstFrame, frameSize);

            /**
             * Convierte el cuadro por segundo a escala de grises
             */
            Imgproc.cvtColor(firstFrame, firstGrayImage, Imgproc.COLOR_BGR2GRAY);

            /**
             * Lee el siguiente cuadro, lo redimensiona y convierte a escala de grises
             */
            video.read(secondFrame);

            Imgproc.resize(secondFrame, secondFrame, frameSize);

            Imgproc.cvtColor(secondFrame, secondGrayImage, Imgproc.COLOR_BGR2GRAY);

            /**
             * Obtiene la diferencia absoluta por pixel de los cuadros anteriores.
             */
            Core.absdiff(firstGrayImage, secondGrayImage, differenceOfImages);
            Imgproc.threshold(differenceOfImages, thresholdImage, 25, 255, Imgproc.THRESH_BINARY);
            Imgproc.blur(thresholdImage, thresholdImage, new Size(12, 12));
            Imgproc.threshold(thresholdImage, thresholdImage, 20, 255, Imgproc.THRESH_BINARY);
            /////
            for(int i = 0; i < contours.size(); ++i) {
                contours.get(i).release();
            }
            contours.clear();

            /**
             * La linea Horizontal
             */
            Imgproc.line(firstFrame, controlPoints.get(6), controlPoints.get(7), new Scalar(255,0,0), Imgproc.LINE_4);
            Imgproc.findContours(thresholdImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            for(int i = 0; i < hullPoints.size(); ++i) {
                hullPoints.get(i).release();
            }
            hullPoints.clear();

            for (int i = 0; i < contours.size(); i++)
            {
                MatOfInt tmp = new MatOfInt();
                Imgproc.convexHull(contours.get(i), tmp, false);
                hullPoints.add(tmp);
            }

            /**
             * Busca el contorno con el área más grande
             */
            if (contours.size() > 0)
            {
                for ( int i = 0; i < contours.size(); i++)
                {
                    if (Imgproc.contourArea(contours.get(i)) > contourArea)
                    {
                            contourArea = Imgproc.contourArea(contours.get(i));
                            position = i;
                            boundingRectangle = Imgproc.boundingRect(contours.get(i));
                    }

                }
            }
            secondFrame.release();
            hierarchy.release();
            secondGrayImage.release();
            firstGrayImage.release();
            thresholdImage.release();
            differenceOfImages.release();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        if (controlPoints.get(6).inside(boundingRectangle))
        {
            Imgproc.line(frame, controlPoints.get(0), controlPoints.get(1), new Scalar(0, 0, 255), 2);
            wasAtLeftPoint = true;
        }
        else if (!controlPoints.get(6).inside(boundingRectangle))
        {
            Imgproc.line(frame, controlPoints.get(0), controlPoints.get(1), new Scalar(0, 255, 0), 2);
        }

        if (controlPoints.get(8).inside(boundingRectangle))
        {
            Imgproc.line(frame, controlPoints.get(2), controlPoints.get(3), new Scalar(0, 0, 255), 2);
            wasAtCenterPoint = true;
        }
        else if (!controlPoints.get(8).inside(boundingRectangle))
        {
            Imgproc.line(frame, controlPoints.get(2), controlPoints.get(3), new Scalar(0, 255, 0), 2);
        }

        if (controlPoints.get(7).inside(boundingRectangle))
        {
            Imgproc.line(frame, controlPoints.get(4), controlPoints.get(5), new Scalar(0, 0, 255), 2);
            wasAtRightPoint = true;
        }
        else if (!controlPoints.get(7).inside(boundingRectangle))
        {
            Imgproc.line(frame, controlPoints.get(4), controlPoints.get(5), new Scalar(0, 255, 0), 2);
        }

        if (wasAtCenterPoint && wasAtLeftPoint && wasAtRightPoint)
        {
            detectedCarsCount++;
            wasDetected = true;
            wasAtCenterPoint = false;
            wasAtLeftPoint = false;
            wasAtRightPoint = false;
        }

        if (contourArea > 3000)
        {
            Imgproc.drawContours(frame, contours, position, new Scalar(255,255,255));
        }
    }
    
    
}
