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
public class CharacterSystem extends JLabel implements MouseListener{

    private String path;
    private int nbMode, currentMode;
    
    public CharacterSystem(String path, int system, int nbMode) throws IOException{
        
        this.path = path + "/system/" + system; // "games/SFV/system/0/0.png
        this.nbMode = nbMode;
        this.currentMode = 0;
        //System.out.println(this.path);
        Image img = ImageIO.read(new File(this.path + "/" + this.currentMode + ".png"));
        this.setIcon(new ImageIcon(img));
        this.addMouseListener(this);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(currentMode == nbMode-1){
            currentMode = 0;
        }else{
            currentMode++;
        }
        Image img = null;
        try {
            img = ImageIO.read(new File(path + "/" + this.currentMode + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(CharacterSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIcon(new ImageIcon(img));
            
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
