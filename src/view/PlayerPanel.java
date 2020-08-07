/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.CharacterSystem;
import classes.Logo;
import controler.Controler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import model.Model;

/**
 *
 * @author Polack
 */
public class PlayerPanel extends JPanel implements MouseListener{
    
    private Controler c;
    private Model m;
    
    //private JLabel lbl;
    private BufferedImage bgimg, saveImg;
    //private BufferedImage[][] FighterImg, systemImg;
    private BufferedImage[][] roundImg;
    
    public PlayerPanel(Model m, Controler c, int nbFighter, int nbRound, String path, int nbSystem, int nbMode, ArrayList<String> nameList) throws IOException{
        
        this.m = m;
        this.c = c;
        
        this.bgimg = ImageIO.read(new File(m.getPath() + "/fond.png"));
        this.saveImg = ImageIO.read(new File(m.getPath() + "/save.png"));
        
        if(nbRound == 1){
            this.roundImg = null;
        }else{
            this.roundImg = new BufferedImage[nbRound][2];
            for(int i = 0; i < nbRound; i++){
                this.roundImg[i][0] = ImageIO.read(new File(m.getPath() + "/" + false + ".png"));
                this.roundImg[i][1] = ImageIO.read(new File(m.getPath() + "/" + false + ".png"));
            }
        }
        
//        this.FighterImg = new BufferedImage[nbFighter][2];
//        for(int i = 0; i < nbFighter; i++){
//            System.out.println(path + "/characters/Random/" + i + "/1.png");
//            this.FighterImg[i][0] = ImageIO.read(new File(path + "/characters/Random/" + i + "/0.png"));
//            this.FighterImg[i][1] = ImageIO.read(new File(path + "/characters/Random/" + i + "/1.png"));
//        }
//        
//        
//        if(nbSystem == 0){
//            this.systemImg = null;
//        }else{
//            this.systemImg = new BufferedImage[nbSystem][2];
//            for(int i = 0; i < nbSystem; i++){
//                System.out.println(path + "/system/" + i + "/0.png");
//                this.systemImg[i][0] = ImageIO.read(new File(path + "/system/" + i + "/0.png"));
//                this.systemImg[i][1] = ImageIO.read(new File(path + "/system/" + i + "/0.png"));
//            }
//        }
        this.addMouseListener(this);
        
        this.setBackground(Color.BLACK);
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bgimg, 0, 0, null);
        
        //setFighterPanel(g);
        
        if(m.getGameList().get(m.getCurrentGame()).getNbCharacters() == 1)
            setFighterPanel(g);
        else
            setTeamPanel(g);
        
//        g.drawImage(FighterImg[0][0], 0, 0, null);
//        g.drawImage(systemImg[0][0], 0, FighterImg[0][0].getHeight() - systemImg[0][0].getHeight(), null);
//        g.drawImage(systemImg[1][0], FighterImg[0][0].getWidth() - systemImg[1][0].getWidth(), FighterImg[0][0].getHeight() - systemImg[1][0].getHeight(), null);
//        
//        g.drawImage(FighterImg[0][1], 974 - FighterImg[0][1].getWidth() -16, 0, null);
//        g.drawImage(systemImg[0][1]/*.getScaledInstance(70, 70, Image.SCALE_DEFAULT)*/, 974 - FighterImg[0][1].getWidth() -16, FighterImg[0][1].getHeight() - systemImg[0][1].getHeight(), null);
//        g.drawImage(systemImg[1][1], 974 - systemImg[1][1].getWidth() -16, FighterImg[0][1].getHeight() - systemImg[1][1].getHeight(), null);
//        
//        g.drawImage(roundImg[0][0], 352, 95, null);
//        g.drawImage(roundImg[1][0], 352, 195, null);
//        g.drawImage(roundImg[2][0], 352, 295, null);
//        
//        g.drawImage(roundImg[0][1], 582, 95, null);
//        g.drawImage(roundImg[1][1], 582, 195, null);
//        g.drawImage(roundImg[2][1], 582, 295, null);
        
        g.drawImage(saveImg, (bgimg.getWidth()/2) - (saveImg.getWidth()/2), bgimg.getHeight() - saveImg.getHeight() -20, null);
        
    }
    
    private void setTeamPanel(Graphics g){
        
        int fighterWidth = m.getChosenFighters()[0][0].getFighterImg().getWidth(), systemWidth = 0, systemHeight = 0;
        int fighterHeight = m.getChosenFighters()[0][0].getFighterImg().getHeight();
        
        if(m.getChosenFighters()[0][0].getSystem() != null){
            systemWidth = m.getChosenFighters()[0][0].getSystemsImg()[0].getWidth();
            systemHeight = m.getChosenFighters()[0][0].getSystemsImg()[0].getHeight();
        }
        
        for(int fighter = 0; fighter < 3; fighter++){
            g.drawImage(m.getChosenFighters()[fighter][0].getFighterImg(), 0, 0+fighterHeight*fighter, null);
            g.drawImage(m.getChosenFighters()[fighter][1].getFighterImg(), bgimg.getWidth() - fighterWidth, 0+fighterHeight*fighter, null);
            if(m.getChosenFighters()[0][0].getSystem() != null){
                for(int system = 0; system < m.getChosenFighters()[0][0].getSystem().length; system++){
                    g.drawImage(m.getChosenFighters()[0][0].getSystemsImg()[system], 0, 0 + 70*system + fighterHeight*fighter/*+181*fighter*/, null);
                    g.drawImage(m.getChosenFighters()[0][1].getSystemsImg()[system], bgimg.getWidth() - fighterWidth, 0 + 70*system + fighterHeight*fighter/*+181*fighter*/, null);
                }
            }
        }
        
    }
    
    private void setFighterPanel(Graphics g){
        
        int fighterWidth = 324/*m.getChosenFighters()[0][0].getFighterImg().getWidth()*/, systemWidth = 0, systemHeight = 0;
        
        if(m.getChosenFighters()[0][0].getSystem() != null){
            systemWidth = m.getChosenFighters()[0][0].getSystemsImg()[0].getWidth();
            systemHeight = m.getChosenFighters()[0][0].getSystemsImg()[0].getHeight();
        }
        
        g.drawImage(m.getChosenFighters()[0][0].getFighterImg(), 0, 0/*+181*fighter*/, null);
        g.drawImage(m.getChosenFighters()[0][1].getFighterImg(), bgimg.getWidth() - fighterWidth, 0, null);
        
        if(m.getChosenFighters()[0][0].getSystem() != null){
            for(int i = 0; i < m.getChosenFighters()[0][0].getSystem().length; i++){
                g.drawImage(m.getChosenFighters()[0][0].getSystemsImg()[i].getScaledInstance(70, 70, Image.SCALE_DEFAULT), 0, 0 + 70*i/*+181*fighter*/, null);
                g.drawImage(m.getChosenFighters()[0][1].getSystemsImg()[i].getScaledInstance(70, 70, Image.SCALE_DEFAULT), bgimg.getWidth() - 70, 0 + 70*i/*+181*fighter*/, null);
            }
        }
        
        for(int i = 0; i < roundImg.length; i++){
            g.drawImage(roundImg[i][0], 352, 95 + 100*i/*+181*fighter*/, null);
            g.drawImage(roundImg[i][1], 582, 95 + 100*i/*+181*fighter*/, null);
        }
            
        
    }
    
    /////////////
    // Getters //
    /////////////

    public BufferedImage getBgimg() {
        return bgimg;
    }

//    public BufferedImage[][] getFighterImg() {
//        return FighterImg;
//    }
//
//    public BufferedImage[][] getSystemImg() {
//        return systemImg;
//    }

    public BufferedImage[][] getRoundImg() {
        return roundImg;
    }

    
    /////////////
    // Setters //
    /////////////

    public void setBgimg(BufferedImage bgimg) {
        this.bgimg = bgimg;
    }

//    public void setFighterImg(int fighter, int player, String path){
//        try {
//            this.FighterImg[fighter][player] = ImageIO.read(new File(path));
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //this.repaint();
//    }
//
//    public void setSystemImg(int system, int player, String path) {
//        try {
//            this.systemImg[system][player] = ImageIO.read(new File(path));
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //this.repaint();
//    }

    public void setRoundImg(int round, int player, String path) {
        try {
            this.roundImg[round][player] = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.repaint();
    }
    
    ////////////////////////
    // Mouse Interactions //
    ////////////////////////
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(SwingUtilities.isRightMouseButton(e)){
            
            int fighter = 0, player = 0;
            if(new Rectangle(974/2, 0, 974/2, 543).contains(e.getPoint())){
                player = 1;
            }
            
            if(m.getChosenFighters().length == 3){
                if(new Rectangle(181, 0, 974, 181).contains(e.getPoint())){
                    fighter = 1;
                }else if(new Rectangle(362, 0, 974, 181).contains(e.getPoint())){
                    fighter = 2;
                }
            }
            PopUpMenu popup = new PopUpMenu(player, fighter);
            popup.show(e.getComponent(), e.getX(), e.getY());
        }else{
            try {
                c.controlMatch(e.getPoint(), e.getClickCount());
            } catch (IOException ex) {
                Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private class PopUpMenu extends JPopupMenu{
        
        private JMenuItem resetMI, titleMI, removeMI, rankedMI;
        private String str[] = {"Casual (Click for ranked)", "Ranked (Click for casual)"};
        //int player, fighter;
        
        public PopUpMenu(int player, int fighter){
            
//            this.player = player;
//            this.fighter = fighter;
            resetMI = new JMenuItem("Reset Session");
            //recreateMI = new JMenuItem("Recreate GIF");
            titleMI = new JMenuItem("PLAYER " + (player+1) + " FIGHTER " + (fighter+1));
            removeMI = new JMenuItem("Remove fighter");
            if(m.isRanked())
                rankedMI = new JMenuItem(str[1]);
            else
                rankedMI = new JMenuItem(str[0]);
            
            add(titleMI);
            //add(recreateMI);
            add(resetMI);
            //add(blankMI);
            add(removeMI);
            add(rankedMI);
            
            resetMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        c.controlReset(player, fighter);
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            });
            
            removeMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                   c.controlRemove(player, fighter);
                }
                
            });
            
            rankedMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                   //c.controlRemove(player, fighter);
                   if(m.isRanked()){
                       try {
                           c.controlRanked(false);
                       } catch (IOException ex) {
                           Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       rankedMI.setText(str[0]);
                   }else{
                       try {
                           c.controlRanked(true);
                       } catch (IOException ex) {
                           Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       rankedMI.setText(str[1]);
                   }
                   
                   
                   
                }
                
                
            });
            
//            recreateMI.addActionListener(new ActionListener(){
//                
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    try {
//                        c.controlRecreate();
//                    } catch (IOException ex) {
//                        Logger.getLogger(PlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//            });
            
        }
        
    }
    
}
