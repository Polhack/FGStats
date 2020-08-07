/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class AnimThread extends Thread{
    
    private StatPanel p;
    private int wait = 100;
    private int nb = -1;
    private boolean isUp = false;
    
    public AnimThread(StatPanel p){
        
        this.p = p;
        
    }
    
    public void run(){
        
        while(true){
            
            try {
                sleep(wait);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            if(p.isTotal() && p.isCurrent()){
                
                if(p.isModified() && nb != 0 && !isUp){
                    nb = -1;
                    p.setIsModified(false);
                }
                if(nb == -1)
                    setNb();
                if(isUp){
                    scrollDown();
                    wait = 1000;
                    isUp = false;
                    setNb();
                }else{
                    
                    scrollUp();
                    wait = 5000;
                    isUp = true;
                }
                
            }else if(p.isCurrent()){
            
                if(nb == -1){
                    
                    // Change to current
                    nb = 0;
                    p.setNb(0);
                    
                    // Scroll Up
                    scrollUp();
//                    try {
//                        sleep(5000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    scrollDown();
                    wait = 5000;
                    isUp = true;
                }
                
            }else{
                
                if(nb > -1){
                    scrollDown();
                }
                resetNb();
                wait = 100;
            }
        }
        
        
    }
    
    private void scrollUp(){
        
        int y = p.getV();
        
        while(y > 0){
            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            p.setV(y);
            
            y--;
            
        }
        
    }
    
    private void scrollDown(){
        
        int y = p.getV();
        
        while(y <= 35){
            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            p.setV(y);
            
            y++;
        }
    }
    
    private void setNb(){
        
        if(nb == 3){
            nb = 0;
        }else{
            nb++;
        }
        p.setNb(nb);
    }
    
    private void resetNb(){
        nb = -1;
    }
    
}
