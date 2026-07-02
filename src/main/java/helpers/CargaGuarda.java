/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
    
    public static Image getIcon() {
        ImageIcon icon = new ImageIcon("./src/main/resources/icon.png");
        return icon.getImage();
    }
    
    public static void CreateFile(){
        File txtFile = new File("src/main/resources/testTextfile.txt");
        
        try {
            txtFile.createNewFile();
        } catch (IOException ex) {
            System.getLogger(CargaGuarda.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public static void CreateLevel(String nombre, int[] idArray){
        File newLevel = new File("src/main/resources/" + nombre + ".txt");

        if (newLevel.exists()) {
            System.out.println("Archivo: " + nombre + " ya existe.");
        } else {
            try {
                newLevel.createNewFile();
            }catch (IOException ex) {
            System.getLogger(CargaGuarda.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            WriteToFile(newLevel,idArray);
        }

        
    }
    
    public static int[][] GetLevelData(String nombre){
        File lvlFile = new File("src/main/resources/" + nombre + ".txt");
        
        if(lvlFile.exists()){
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilidades.ArrayListAInt2D(list, 5, 9);
        }else{
            System.out.println("Archivo: " + nombre + " no existe.");
            return null;
        }
        
        
    }
    
    private static void WriteToFile(File f, int[] idArray){
        try {
            PrintWriter pw = new PrintWriter(f);
            
            for(Integer i : idArray)
                pw.println(i);
            
            pw.close();
        } catch (FileNotFoundException ex) {
            System.getLogger(CargaGuarda.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
    }
    
    private static ArrayList<Integer> ReadFromFile(File lvlFile){
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(lvlFile);
            
            while(sc.hasNextLine()){
                list.add(Integer.parseInt(sc.nextLine()));
            }
            
            sc.close();
        } catch (FileNotFoundException ex) {
            System.getLogger(CargaGuarda.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return list;
    }
}
