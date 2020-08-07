/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Polack
 */
public class Logo extends JLabel{
    
    private String path;
    private int nb = -1;
    
    public Logo(String path, int width, int height) throws IOException{
        
        //super();
        this.path = path;
        System.out.println(path + "/mouse_off.png");
        Image img = ImageIO.read(new File(path + "/mouse_off.png"));
        this.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        //this.addMouseListener(this);
    }
    
    public Logo(String path, int nb, int width, int height) throws IOException{
        
        //super();
        this.path = path;
        this.nb = nb;
        System.out.println(path + "/mouse_off.png");
        Image img = ImageIO.read(new File(path + "/mouse_off.png"));
        this.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        //this.addMouseListener(this);
    }

    public String getPath() {
        return path;
    }
    
    public int getNb() {
        return nb;
    }
    
    public void setImage(String image){
        Image img = null;
        try {
            img = ImageIO.read(new File(path + image));
        } catch (IOException ex) {
            Logger.getLogger(Logo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIcon(new ImageIcon(img.getScaledInstance(120, 72, Image.SCALE_DEFAULT)));
    }
    
}
