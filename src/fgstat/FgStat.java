/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fgstat;

import classes.Session;
import controler.Controler;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.Model;
import org.xml.sax.SAXException;
import stat_anim.StatFrame;
import view.ViewFrame;

/**
 *
 * @author Polack
 */
public class FgStat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        // TODO code application logic here
        Model m = new Model();
        Controler c = new Controler(m);
        ViewFrame f = new ViewFrame(m, c);
        StatFrame sv = new StatFrame((int)f.getLocation().getX(), (int)f.getLocation().getY());
        m.setV(f, sv);
        
//        int test[] = null;
//        
//        int test2[] = test.clone();
//        
//        if(test == null)
//            System.out.println("C'est null");
        
//        Session s = new Session("games/test/Session_0.xml");
        //s.writeSessionFile();
        //s.openSessionFile(new File("games/test/Session_" + (s.getNbFiles()-1) + ".xml"));
        
        
    }
    
}
