/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Polack
 */
public class Fighter {
    
    protected int fighter = -1;
    protected int[] system = null;
    
//    private BufferedImage fighterImg;
//    private BufferedImage[] systemsImg = null;
    
    public Fighter(int nbSystem){
        
//        fighterImg = ImageIO.read(new File(path + "/characters/Random/" + player + ".png"));
        
        if(nbSystem > 0){
            system = new int[nbSystem];
//            systemsImg = new BufferedImage[nbSystem];
            for(int i = 0; i < nbSystem; i++){
                system[i] = 0;
//                systemsImg[i] = ImageIO.read(new File(path + "/system/" + i + "/0.png"));
            }
        }
    }
    
    public Fighter(int[] systems, int fighter){
        this.fighter = fighter;
        this.system = systems;
    }

    public int getFighter() {
        return fighter;
    }
    
    public int[] getSystem() {
        return system;
    }
    
    public void setFighter(int fighter) throws IOException {
        this.fighter = fighter;
    }

    public void setSystem(int slot, Boolean isReset){
        
        if(isReset){
            system[slot] = 0;
        }   
        else{
            system[slot]++;
        } 
    }
    
    public void setSystem(int slot, int value){
        
       system[slot] = value;
       
    }
    
    
    
}
