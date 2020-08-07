/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controler.Controler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.Model;

/**
 *
 * @author Polack
 */
public class ChooseRandPanel extends JPanel implements MouseListener{
    
    private Controler c;
    private Model m;
    private Image[] randImg;
    private BufferedImage bgimg, frameImg, cancelImg;
    
    public ChooseRandPanel(Model m, Controler c, ArrayList<Integer> rands) throws IOException{
        
        this.m = m;
        this.c = c;
        
        this.bgimg = ImageIO.read(new File(m.getPath() + "/fond.png"));
        this.frameImg = ImageIO.read(new File("fond_rand.png"));
        this.cancelImg = ImageIO.read(new File(m.getPath() + "/save.png"));
        
        randImg = new Image[5];
        
        for(int i = 0; i < 5; i++){
            System.out.println(m.getPath() +"/characters/" + m.getCharacterList().get(rands.get(i)) + "/0.png");
            Image img = ImageIO.read(new File(m.getPath() +"/characters/" + m.getCharacterList().get(rands.get(i)) + "/0.png"));
            this.randImg[i] = img.getScaledInstance(190, 293, Image.SCALE_DEFAULT);
        }
        
        this.addMouseListener(this);
        this.setBackground(Color.BLACK);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        //g.drawImage(bgimg, 0, 0, null);
        g.drawImage(frameImg, 0, 0, null);
        
        for(int i = 0; i < 5; i++){
            g.drawImage(randImg[i], 4+i*194, 121, null);
        }
        g.drawImage(cancelImg, (bgimg.getWidth()/2) - (cancelImg.getWidth()/2), bgimg.getHeight() - cancelImg.getHeight() -20, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
            boolean found = false;
            int i = 0;
            while(!found){
                if(new Rectangle(4+i*194, 121, 190, 293).contains(e.getPoint())){
                    try {
                        c.controlRand(i);
                    } catch (IOException ex) {
                        Logger.getLogger(ChooseRandPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    found = true;
                }else{
                    i++;
                }
            }
            
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
