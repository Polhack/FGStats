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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import model.Model;

/**
 *
 * @author Polack
 */
public class CharacterPanel extends JPanel implements MouseListener{
    
    private Model m;
    private Controler c;
    int height = 0;
    private Logo randomLogo, multiRandLogo;
    
    public CharacterPanel(Controler c, Model m) throws IOException{
        
        this.m = m;
        this.c = c;
        
        int y = (this.m.getCharacterList().size()+1)/10;
        //System.out.println("Size: " + this.m.getCharacterList().size());
        
        if(this.m.getCharacterList().size()%10 != 0)
            y++;
        
        height = y*90;
        this.setLayout(new GridLayout(y,10));
        
        String path = this.m.getPath();
        
        System.out.println(path);
        
        int nb = 0;
        for(String name : this.m.getCharacterList()){
            Logo l = new Logo(path +"/characters/" + name, nb, 80, 80);
            l.addMouseListener(this);
            //charactersList.add(l);
            this.add(l);
            
            nb++;
            
        }
        
        randomLogo = new Logo(path +"/characters/Random", 80, 80);
        randomLogo.addMouseListener(this);
        //randomLogo.setLocation(5, 5);
        this.add(randomLogo);
        
//        multiRandLogo = new Logo(path +"/characters/MultiRand", 80, 80);
//        multiRandLogo.addMouseListener(this);
//        //randomLogo.setLocation(5, 5);
//        this.add(multiRandLogo);
        
        //int height = ((nameList.size()+1)/11)*120;
        this.setPreferredSize(new Dimension (974,y*80));
        
        this.setBackground(Color.BLACK);
        
        this.setVisible(true);
        
        
        
    }
    
    /////////////
    // Getters //
    /////////////

    public int getHeight() {
        return height;
    }

    public Logo getRandomLogo() {
        return randomLogo;
    }

    ////////////////////////
    // Mouse Interactions //
    ////////////////////////
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println(((Logo)e.getSource()).getNb());
        
        int fighter = ((Logo)e.getSource()).getNb();
        
        if(e.getSource() == randomLogo){
            if(e.getClickCount() == 2){
//                try {
//                    c.controlRandom();
//                } catch (IOException ex) {
//                    Logger.getLogger(CharacterPanel.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {
                    c.controlMultiRandom();
                } catch (IOException ex) {
                    Logger.getLogger(CharacterPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else 
//            if(e.getSource() == multiRandLogo){
//            if(e.getClickCount() == 2){
//                try {
//                    c.controlMultiRandom();
//                } catch (IOException ex) {
//                    Logger.getLogger(CharacterPanel.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }else 
        {
            if(SwingUtilities.isRightMouseButton(e)){
                try {
                    c.controlFighter(fighter, 1);
                } catch (IOException ex) {
                    Logger.getLogger(CharacterPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    c.controlFighter(fighter, 0);
                } catch (IOException ex) {
                    Logger.getLogger(CharacterPanel.class.getName()).log(Level.SEVERE, null, ex);
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

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        setImage((Logo)e.getSource(), 
                this.m.getGameList().get(m.getCurrentGame()).getName(), 
                getCharacter(e.getSource()),
                "mouse_on.png");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setImage((Logo)e.getSource(), 
                this.m.getGameList().get(m.getCurrentGame()).getName(), 
                getCharacter(e.getSource()),
                "mouse_off.png");
    }
    
    private boolean isRandomButton(Object o){
        
        if(o == this.randomLogo)
            return true;
        
        return false;
        
    }
    
    private String getCharacter(Object o){
        
        if(o != this.randomLogo)
            return this.m.getCharacterList().get(((Logo)o).getNb());
                    
        return "Random";      
        
    }
    
    private void setImage(JLabel l, String game, String character, String image){
        Image img = null;
        try {
            img = ImageIO.read(new File("games/" + game + "/characters/" + character + "/" + image));
        } catch (IOException ex) {
            Logger.getLogger(Logo.class.getName()).log(Level.SEVERE, null, ex);
        }
        l.setIcon(new ImageIcon(img.getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
    }
    
}
