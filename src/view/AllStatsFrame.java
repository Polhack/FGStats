/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Polack
 */
public class AllStatsFrame extends JFrame{
    
    private JLabel current = new JLabel();
    private JLabel last = new JLabel();
    private JLabel best = new JLabel();
    private JLabel total = new JLabel();
    
    public AllStatsFrame(){
        
        try {
            current.setIcon(new ImageIcon(ImageIO.read(new File("current.png"))));
            last.setIcon(new ImageIcon(ImageIO.read(new File("last.png"))));
            best.setIcon(new ImageIcon(ImageIO.read(new File("best.png"))));
            total.setIcon(new ImageIcon(ImageIO.read(new File("total.png"))));
        } catch (IOException ex) {
            Logger.getLogger(AllStatsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setLayout(new GridLayout(4,1));
        
        this.add(current);
        this.add(last);
        this.add(best);
        this.add(total);
        
        this.setSize(507, 35*4);
        
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        
    }
    
    public void setVisible(int x, int y){
        this.setLocation(x, y);
        this.setVisible(true);
    }
    
    public void refresh(){
        
        try {
            current.setIcon(new ImageIcon(ImageIO.read(new File("current.png"))));
            last.setIcon(new ImageIcon(ImageIO.read(new File("last.png"))));
            best.setIcon(new ImageIcon(ImageIO.read(new File("best.png"))));
            total.setIcon(new ImageIcon(ImageIO.read(new File("total.png"))));
        } catch (IOException ex) {
            Logger.getLogger(AllStatsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.repaint();
        this.revalidate();
        
    }
    
}
