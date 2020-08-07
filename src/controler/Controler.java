/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import model.Model;
import org.xml.sax.SAXException;
import view.ViewFrame;

/**
 *
 * @author Polack
 */
public class Controler {
    
    Model m;
    //ViewFrame v;
    
    public Controler(Model m){
        
        this.m = m;
        //this.v = v;
        
    }
    
    //////////////////////
    //                  //
    //  Game Controler  //
    //                  //
    //////////////////////
    
    public void controlGame(int game) throws ParserConfigurationException, IOException, SAXException{
        
        if(game > -1){
            
            m.setCurrentGame(game);
            m.getV().setFrame(false);
            
        }
        
    }
    
    
    ///////////////////////
    //                   //
    //  Match Controler  //
    //                   //
    ///////////////////////
    
    public void controlMatch(Point p, int count) throws IOException{
        
        if(new Rectangle((974/2)-(80/2),543-100,80,80).contains(p)){
            if(count == 2){// Save button
                System.out.println("Bouton Save");
                controlMatchResult();
            }
                
        }else{
            controlFighter(p, count, m.getGameList().get(m.getCurrentGame()).getNbCharacters());
        } 
        
//        if(count == 2){
//            if(new Rectangle(0,0,352,543-140).contains(p)) //Player 1
//                m.setFighter(0, 0, -1); //System.out.println("p1");
//            if(new Rectangle(974-352,0,352,543-140).contains(p)) //Player 2
//                m.setFighter(0, 1, -1); //System.out.println("p2");
//            if(new Rectangle((974/2)-(80/2),543-100,80,80).contains(p))
//                controlMatchResult();
//            
//        }else{
//            if(new Rectangle(0,543-140,140,140).contains(p)) //vsp1
//                m.setSystem(0, 0); //System.out.println("p1 VS");
//            if(new Rectangle(352-140,543-140,140,140).contains(p)) //vtp1
//                m.setSystem(1, 0); //System.out.println("p1 VT");
//            if(new Rectangle(974-352,543-140,140,140).contains(p)) //vsp2
//                m.setSystem(0, 1); //System.out.println("p2 VS");
//            if(new Rectangle(974-140,543-140,140,140).contains(p)) //vtp2
//                m.setSystem(1, 1); //System.out.println("p2 VT");
//            if(new Rectangle(352,95,40,40).contains(p)) //p1 R1
//                m.setRound(0, 0); //System.out.println("p1 R1");
//            if(new Rectangle(352,195,40,40).contains(p)) //p1 R2
//                m.setRound(1, 0); //System.out.println("p1 R2");
//            if(new Rectangle(352,295,40,40).contains(p)) //p1 R3
//                m.setRound(2, 0); //System.out.println("p1 R3");
//            if(new Rectangle(582,95,40,40).contains(p)) //p2 R1
//                m.setRound(0, 1); //System.out.println("p2 R1");
//            if(new Rectangle(582,195,40,40).contains(p)) //p2 R2
//                m.setRound(1, 1); //System.out.println("p2 R2");
//            if(new Rectangle(582,295,40,40).contains(p)) //p2 R3
//                m.setRound(2, 1); //System.out.println("p2 R3");
//        }
        
    }
    
    private void controlFighter(Point p, int count, int nbFighters){
        int nbSystem = m.getGameList().get(m.getCurrentGame()).getNbSystem();
        if(new Rectangle(0,0,487,543).contains(p)){ // P1
            System.out.println("Player 1");
            if(new Rectangle(0,0,324,543).contains(p)){// Fighters + systems
                System.out.println("Fighters & systems");
                
                int fighter = 0;
                boolean isFound = false;
                
                while(fighter < nbFighters && !isFound){
                    if(new Rectangle(0,0+181*fighter,324,543/nbFighters).contains(p)){ // Fighter x
                        
                        if(nbSystem > 0 && new Rectangle(0,0+181*fighter,70,70*nbSystem).contains(p)){ 
                            controlZone(p, 0, fighter, 0, 0+181*fighter, 70, 70, 70, m.getGameList().get(m.getCurrentGame()).getNbSystem(), true);
                        }else{
                            //System.out.println("Fighter " + fighter);
                            if(count == 2){
                                System.out.println("Fighter " + fighter);
                                try {
                                    m.setFighter(fighter, 0, -1);
                                } catch (IOException ex) {
                                    Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        isFound = true;
                    }
                }
            }else{ // Rounds
                System.out.println("Rounds");
                controlZone(p, 0, 0, 352, 95, 40, 40, 100, m.getGameList().get(m.getCurrentGame()).getNbRound(), false);
            }
        }else{ //P2
            System.out.println("Player 2");
            if(new Rectangle(974-324,0,324,543).contains(p)){// Fighters + systems
                System.out.println("Fighter & systems");
                int fighter = 0;
                boolean isFound = false;
                
                while(fighter < nbFighters && !isFound){
                    if(new Rectangle(974-324,0+181*fighter,324,543/nbFighters).contains(p)){ // Fighter x
                        System.out.println("Le Prout");
                        if(nbSystem > 0 && new Rectangle(974-70,0,70,70*nbSystem).contains(p)){ // System x
                            controlZone(p, 1, fighter, 974-70, 0+181*fighter, 70, 70, 70, m.getGameList().get(m.getCurrentGame()).getNbSystem(), true);
                        }else{
                            if(count == 2){
                                
                                try {
                                    m.setFighter(fighter, 1, -1);
                                } catch (IOException ex) {
                                    Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        isFound = true;
                    }
                }
            }else{ // Rounds
                System.out.println("Rounds");
                controlZone(p, 1, 0, 582, 95, 40, 40, 100, m.getGameList().get(m.getCurrentGame()).getNbRound(), false);
            }
        } 
    }
    
    private void controlTeam(){
        
    }
    
//    private void controlRound(Point p, int player, int x, int y, int width, int height, int offset){
//        
//        int nbRound = m.getRounds().length;
//        int round = 0;
//        boolean isFound = false;
//        
//        while(round < nbRound || !isFound){
//            if(new Rectangle(x,y + offset*round,width,height).contains(p)){
//                m.setRound(round, player);// Fonction
//                isFound = true;
//            }
//            round++;
//        }
//        
//    }
    
    private void controlZone(Point p, int player, int fighter, int x, int y, int width, int height, int offset, int nb, boolean isSystem){
        
        int i = 0;
        boolean isFound = false;
        
        while(i < nb && !isFound){
            if(new Rectangle(x,y + offset*i,width,height).contains(p)){
                if(isSystem){
                    System.out.println("System " + i);
                    m.setSystem(i, player, fighter);
//                    try {
//                        m.getChosenFighters()[fighter][player].setSystem(i, m.getGameList().get(m.getCurrentGame()).getNbMode(), m.getPath());
//                        //this.v.getPlayer1Panel().repaint();
//                    } catch (IOException ex) {
//                        Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }else{
                    System.out.println("Round " + i);
                    m.setRound(i, player);
                }
                isFound = true;
            }
            i++;
        }
    }
    
    
    
    private void controlMatchResult() throws IOException{
        
        int winRoundP1 = 0, winRoundP2 = 0;
        boolean isFirstRoundsOk = true;
        
            for(int i = 0; i < m.getRounds().length; i++){
                if(m.getRounds()[i][0] == true)
                    winRoundP1++;
                
                if(m.getRounds()[i][1] == true)
                    winRoundP2++;
                
                if(m.getRounds()[i][0] == false && m.getRounds()[i][1] == false && i < (m.getRounds().length/2)+1)
                    isFirstRoundsOk = false;
                
                

            }
            
//            System.out.println(winRoundP1);
//                System.out.println(winRoundP2);
//                System.out.println(isFirstRoundsOk);
//                System.out.println();
            
        if(isFirstRoundsOk && ((winRoundP1 == m.getRounds().length/2+1) || (winRoundP2 == m.getRounds().length/2+1)) && m.isPlayerReady(0) && m.isPlayerReady(1)){
            System.out.println("Match is OK");
            m.addMatchToSession();
        }
        else
            System.out.println("Match is not OK");
            
    }
    
    
    /////////////////////////
    //                     //
    //  Fighter Controler  //
    //                     //
    /////////////////////////
    
    public void controlFighter(int fighter, int player) throws IOException{
        
        int slot = getFighterNb(player);
        
        if(slot != -1){
            
            m.setFighter(slot, player, fighter);
            
        }
        
        
    }
    
    private int getFighterNb(int player){
        
        int nb = -1;
        //System.out.println("test: " + (m.getGameList().get(m.getCurrentGame()).getNbCharacters()-1));
        for(int i = m.getGameList().get(m.getCurrentGame()).getNbCharacters()-1; i >= 0; i--){
            System.out.println("test: " + i);
            if (m.getChosenFighters()[i][player].getFighter() == -1) 
                nb = i; 
        }
            
        return nb;
        
    }
    
    //////////////////////
    // Random Controler //
    //////////////////////
    
    public void controlRandom() throws IOException{
        
        int slot = getFighterNb(0);
        if(slot != -1){
            
            m.setRandomFighter(slot);
            
        }
        
    }
    
    private boolean isFighterChosen(int player){
        
        boolean b = false;
        
        for(int i = 0; i < m.getGameList().get(m.getCurrentGame()).getNbCharacters(); i++){
            b = (m.getChosenFighters()[i][player].getFighter() == -1) ? false : true; 
        }
            
        return true;
        
    }
    
    public void controlReset(int player, int fighter) throws IOException{
        if(m.getChosenFighters()[fighter][player].getFighter() > -1){
            m.setSession();
        }
    }
    
    public void controlRemove(int player, int fighter){
        if(m.getChosenFighters()[fighter][player].getFighter() > -1){
            try {
                m.setFighter(fighter, player, -1);
            } catch (IOException ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
//    public void controlRecreate() throws IOException{
//        
//        m.getImgCreator().remakeGIF();
//        
//    }

    public void controlRand(int chosen) throws IOException {
        
        m.updatePickChances(0, m.getRandList().get(chosen));
        m.getV().setFrame(false);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void controlMultiRandom() throws IOException {
        m.prepareMultipleRandomFighters();
        m.getV().setFrame(true);
        
    }

    public void controlRanked(boolean isRanked) throws IOException {
        m.setIsRanked(isRanked);
        if(m.isPlayerReady(0)){
            m.setSession();
        }
    }
    
}
