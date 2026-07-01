/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

/**
 * Constructor básico del nivel mediante un array estático.
 * @author lucio
 */
public class EditorNivel {
    public static int[][] getLevelData(){
        /**
         * Crea un array 2D en donde cada cuadrado es un tile del nivel
         */
        int[][] lvl = {
            {1,0,0,1,0,0,1,1},
            {1,0,0,1,0,1,0,0},
            {1,0,0,1,0,1,0,0},
            {1,0,0,1,0,0,1,1},
            {0,0,0,0,0,0,0,0}
        };
        return lvl;
    }
}
