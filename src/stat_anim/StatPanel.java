/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import stat_anim.AnimThread;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Polack
 */
public class StatPanel extends JPanel{
 
        private AnimThread t;
        private Image img[] = new Image[4];
        private int nb = 0; 
        private int v = 36;
        private boolean isCurrent = false, isTotal = false, isModified = false;
        
        public StatPanel() throws IOException{
            
            refresh();
            
            this.setBackground(Color.BLUE);
            
            
            t = new AnimThread(this);
            t.start();
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(img[nb], 0, v, this);
            Toolkit.getDefaultToolkit().sync();
        }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public boolean isTotal() {
        return isTotal;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }
    
    public void setIsTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
    
    public void setV(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }
    
    public void refresh() throws IOException{
        img[0] = ImageIO.read(new File("current.png"));
        img[1] = ImageIO.read(new File("last.png"));
        img[2] = ImageIO.read(new File("best.png"));
        img[3] = ImageIO.read(new File("total.png"));
    }

    
    
        
}
