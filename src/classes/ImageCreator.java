/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Polack
 */
public class ImageCreator {
    
    private int nbWinLast, nbFightLast, nbWinBest, nbFightBest, nbWinTotal, nbFightTotal;
    
    public ImageCreator(){
        
        resetImageCreator();
        
    }
    
    public void resetImageCreator(){
        
        this.nbFightBest = -1;
        this.nbFightLast = -1;
        this.nbFightTotal = 0;
        this.nbWinBest = -1;
        this.nbWinLast = -1;
        this.nbWinTotal = 0;
        
    }
    
    public void setImageCreator(int[] nb){
        
        this.nbWinLast = nb[0];
        this.nbFightLast = nb[1];
        this.nbWinBest = nb[2];
        this.nbFightBest = nb[3];
        this.nbWinTotal = nb[4];
        this.nbFightTotal = nb[5];
        
    }
    
    public void createStatImage(){
        
        
        
    }
    
    public void createFightersImage(ArrayList<String> pathList) throws IOException{
        
        // Path example: games/"Game_Name"/characters/"Character_Name/"
        
        if(pathList.size() == 1){
            CopyFile.copyFileUsingStream(new File(pathList.get(0) + "cam.png"), new File("cam_obs.png"));
            CopyFile.copyFileUsingStream(new File(pathList.get(0) + "game.png"), new File("game_obs.png"));
        }
        
    }
    
    public void getGif(int win, int fight) throws IOException{
        
        BufferedImage last, best, total;
        //File output = new File("current.png");
        //System.out.println("Prout");
        //AnimatedGifEncoder e = new AnimatedGifEncoder();
        
        BufferedImage bg = ImageIO.read(new File("stat.png"));
        BufferedImage current = getFullTextImage("Current Session: ", getWinPercent(win, fight), win, fight, 25, 23);
        
        BufferedImage current_final = setImageIn(current, ImageIO.read(new File("stat.png")), bg.getWidth()/2 - current.getWidth()/2, 10);
//        ImageIO.write(current_final, "PNG", new File("current.png"));
        ImageIO.write(current_final, "PNG", new File("current.png"));
//        e.start("stat_obs.gif");
//        e.setDelay(5000);
//        e.setRepeat(0);
//        e.setTransparent(Color.BLUE);
//        
//        e.addFrame(current_final);
        
        if(nbFightTotal != 0){
            
            last = getFullTextImage("Last Session: ", getWinPercent(nbWinLast, nbFightLast), nbWinLast, nbFightLast, 25, 23);
//            System.out.println("current win: " + win);
//            System.out.println("current fight: " + fight);
//            
//            System.out.println(nbWinBest);
//            System.out.println(nbFightBest);
            if(win > nbWinBest && fight >= nbFightBest && getWinPercent(win, fight) > getWinPercent(nbWinBest, nbFightBest))
                best = getFullTextImage("Best Session: ", getWinPercent(win, fight), win, fight, 25, 23);
            else
                best = getFullTextImage("Best Session: ", getWinPercent(nbWinBest, nbFightBest), nbWinBest, nbFightBest, 25, 23);
            
            total = getFullTextImage("Total: ", getWinPercent(nbWinTotal+win, nbFightTotal+fight), nbWinTotal+win, nbFightTotal+fight, 25, 23);
                    
            BufferedImage last_final = setImageIn(last, ImageIO.read(new File("stat.png")), bg.getWidth()/2 - last.getWidth()/2, 10);
            BufferedImage best_final = setImageIn(best, ImageIO.read(new File("stat.png")), bg.getWidth()/2 - best.getWidth()/2, 10);
            BufferedImage total_final = setImageIn(total, ImageIO.read(new File("stat.png")), bg.getWidth()/2 - total.getWidth()/2, 10);
            
            ImageIO.write(last_final, "PNG", new File("last.png"));
            ImageIO.write(best_final, "PNG", new File("best.png"));
            ImageIO.write(total_final, "PNG", new File("total.png"));
            
//            e.addFrame(last_final);
//            e.addFrame(best_final);
//            e.addFrame(total_final);
            
        }
        else{
            last = ImageIO.read(new File("stat.png"));
            best = ImageIO.read(new File("stat.png"));
            total = ImageIO.read(new File("stat.png"));
            
            ImageIO.write(last, "PNG", new File("last.png"));
            ImageIO.write(best, "PNG", new File("best.png"));
            ImageIO.write(total, "PNG", new File("total.png"));
        }
        
//        e.finish();
        
        //System.out.println("2 Prout");
        //BufferedImage fusion = setImageIn(current, ImageIO.read(new File("stat.png")), bg.getWidth()/2 - current.getWidth()/2, 10);
        //ImageIO.write(fusion, "PNG", output);
        //ImageIO.write(createTextImage("Current Session: 100% Win (1000/1000)", 25, 24, Color.WHITE), "PNG", output); 
        
        
    }
    
//    public void remakeGIF() throws IOException{
//        
//        CopyFile.copyFileUsingStream(new File("stat_obs.gif"), new File("stat_temp.gif"));
//        CopyFile.copyFileUsingStream(new File("stat_temp.gif"), new File("stat_obs.gif"));
//    }
    
    private BufferedImage getFullTextImage(String text, int percent, int nbWin, int nbFight, int height, int size) throws IOException{
        
        ArrayList<BufferedImage> imgList = new ArrayList<BufferedImage>();
        
        imgList.add(createTextImage(text, 
                                height, 
                                size,
                                Color.WHITE));

        imgList.add(createTextImage(" " + percent + "% Win", 
                                height, 
                                size,
                                getColor(nbFight, percent)));

        imgList.add(createTextImage(" (" + nbWin + "/" + nbFight + ")", 
                                height, 
                                size,
                                Color.WHITE));
        
        return createImage(imgList);
        
    }
    
    private BufferedImage createTextImage(String text, int height, int textSize, Color color) throws IOException{

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Terminal", Font.PLAIN, textSize);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int h = fm.getHeight();
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2d.setColor(color);
        int offset = (height - h) / 2;
        g2d.drawString(text, 0, fm.getAscent() + offset);
        g2d.dispose();
        
        return img;
//        File output = new File("test.png");
//        
//        ImageIO.write(img, "png", output);
    }
    
    private BufferedImage createImage(ArrayList<BufferedImage> imgList){
        
        System.out.println("size: " + imgList.size());
        int width = 0;
        int height = imgList.get(0).getHeight();
        for(int i = 0; i < imgList.size(); i++){
            width += imgList.get(i).getWidth();
        }
        int offset = 0;
        
        BufferedImage out = new BufferedImage(width, height, imgList.get(0).getType());
        
        for(int nb = 0; nb < imgList.size(); nb++){
            
            setImageIn(imgList.get(nb), out, offset, 0);
            
            offset += imgList.get(nb).getWidth();
        }
        
        return out;
        
    }
    
    private BufferedImage setImageIn(BufferedImage imgIn, BufferedImage img, int xOffset, int yOffset){
        
        BufferedImage out = img;
        Raster tramePixel = imgIn.getRaster();
        ColorModel modeleCouleur = imgIn.getColorModel();

        for(int w = 0; w < imgIn.getWidth(); w++){
            for (int h = 0; h < imgIn.getHeight(); h++){

                Object objCouleur = tramePixel.getDataElements(w, h, null);
                out.setRGB(w + xOffset, h + yOffset, modeleCouleur.getRGB(objCouleur));
            }
        }
        return out;
        
    }
    
    private Color getColor(int nbFight, int percent){
        
        if(nbFight > 0){
            if(percent > 50){
                return Color.GREEN;
            }
            if(percent < 50){
                return Color.RED;
            }
        }
        
        return Color.YELLOW;
        
    }
    
    private int getWinPercent(int win, int total){
        
        int nb = 0;
        if(total != 0){
            nb = (win*100)/total;

            if((win*100)%total != 0){
                nb++;
            }
        }
        
        return nb;
        
    }
    
}
