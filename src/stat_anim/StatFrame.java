/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Polack
 */
public class StatFrame extends JFrame implements KeyListener{
    
    private boolean isActive = true;
    private JLabel statLabel = new JLabel();
    private StatPanel p;
    
    public StatFrame(int x, int y) throws IOException{
        
        
        
        //statLabel.setIcon(new ImageIcon(current));
        
        p = new StatPanel();
        
        //p.add(statLabel);
        
        this.getContentPane().add(p);
        this.setLocation(x, y);
        this.setSize(513,64);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Characters Stats");
        this.setBackground(Color.BLUE);
        this.setResizable(false);
        
        this.setVisible(true);
        //this.addKeyListener(this);
        
        RepaintThread t = new RepaintThread(this);
        t.start();
        
    }

    public StatPanel getP() {
        return p;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        
//        switch(e.getKeyCode()){
//            case KeyEvent.VK_T:
//                p.setIsModified(true) ;
//                break;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class RepaintThread extends Thread{
        
        private StatFrame f;
        
        public RepaintThread(StatFrame f){
            
            this.f = f;
            
        }
        
        public void run(){
            
            while(isActive){
                
                try {
                    sleep(16);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StatFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                f.repaint();
                f.revalidate();
                     
            }
            
        }
        
    }
    
    
    
}
