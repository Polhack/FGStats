/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Logo;
import controler.Controler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import model.Model;
import org.xml.sax.SAXException;

/**
 *
 * @author Polack
 */
public class GamePanel extends JPanel implements MouseListener{
    
    private Model m;
    private Controler c;
    
    //ArrayList<String> nameList;
    
    
    public GamePanel(Controler c, Model m, int div, int currentGame){
        
        this.m = m;
        this.c = c;
        System.out.println(this.m.getGameList().size());
        for(int i = 0;
                i < this.m.getGameList().size();
                i++){
            
            Logo l = null;
            try {
                l = new Logo("games/" + m.getGameList().get(i).getName() + "/logo", i, 600/div, 360/div);
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            l.addMouseListener(this);
            
            if(i != currentGame)
                this.add(l);
            
        }
        
        this.setPreferredSize(new Dimension (974,80));
        this.setBackground(Color.BLACK);
        
        this.setVisible(true);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println(((Logo)e.getSource()).getNb());
        try {
            c.controlGame(((Logo)e.getSource()).getNb());
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
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
        ((Logo)e.getSource()).setImage("/mouse_on.png");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((Logo)e.getSource()).setImage("/mouse_off.png");
    }
    
}
