/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Polack
 */
public class FighterImg extends Fighter{
    
    private BufferedImage fighterImg;
    private BufferedImage[] systemsImg = null;
    
    public FighterImg(int nbSystem, String path, int player) throws IOException{
        
        super(nbSystem);
        
        fighterImg = ImageIO.read(new File(path + "/characters/Random/" + player + ".png"));
        if(nbSystem > 0){
            systemsImg = new BufferedImage[nbSystem];
            for(int i = 0; i < nbSystem; i++){
                systemsImg[i] = ImageIO.read(new File(path + "/system/" + i + "/0.png"));
            }
        }
    }
    
    public BufferedImage getFighterImg() {
        return fighterImg;
    }

    public BufferedImage[] getSystemsImg() {
        return systemsImg;
    }
    
    public void setFighter(int fighter, String path) throws IOException {
        this.fighter = fighter;
        fighterImg = ImageIO.read(new File(path));
    }
    
    public void setSystem(int slot, int nbMode, String path) throws IOException {
        
        if(system[slot] == nbMode-1){
            system[slot] = 0;
        }   
        else{
            system[slot]++;
        }
        systemsImg[slot] = ImageIO.read(new File(path + "/system/" + slot + "/" + system[slot] + ".png"));    
    }
    
    public Fighter getFighterData(){
        
        return new Fighter(getSystem(), getFighter());
    }
    
}
