/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author lucio
 */
public class CargaGuarda {
    
    public static BufferedImage getPeaSpriteAtlas(){
    BufferedImage img = null;
    InputStream is = CargaGuarda.class.getClassLoader().getResourceAsStream("pea.png");
        
        try{
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
        return img;
    }
    
    public static BufferedImage getSunSpriteAtlas(){
    BufferedImage img = null;
    InputStream is = CargaGuarda.class.getClassLoader().getResourceAsStream("sunflower.png");
        
        try{
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
        return img;
    }

    public static BufferedImage getGardenSpriteAtlas() {
         BufferedImage img = null;
    InputStream is = CargaGuarda.class.getClassLoader().getResourceAsStream("garden.png");
        
        try{
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
        return img;
    }
    
    public static BufferedImage getSpriteAtlas(String atlasName){
    BufferedImage img = null;
    InputStream is = CargaGuarda.class.getClassLoader().getResourceAsStream(atlasName);
        
        try{
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
        return img;
    }
}
