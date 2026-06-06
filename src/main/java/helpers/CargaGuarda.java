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
 * Maneja carga y guardado de recursos del juego.
 * @author lucio
 */
public class CargaGuarda {
    /**
     * Carga un atlas de sprites desde una imagen.
     * @param atlasName Nombre de la imagen
     * @return Imagen leida
     */
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
