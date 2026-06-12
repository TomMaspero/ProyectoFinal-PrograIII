/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.util.ArrayList;

/**
 *
 * @author lucio
 */
public class Utilidades {
    /**
     * Toma un ArrayList y lo convierte a un array int de 2 dimensiones (x, y)
     * @param list ArrayList cargado
     * @param row  Filas del nivel
     * @param col  Columnas del nivel
     * @return      Array 2D resultante
     */
    public static int[][] ArrayListAInt2D(ArrayList<Integer> list, int row, int col){
        int[][] newArr = new int[row][col];
        
        for(int j = 0; j < newArr.length;j++){
            for(int i = 0; i < newArr[j].length; i++){
                int index = j * row + i;
                newArr[j][i] = list.get(index);
            }
        }
        return newArr;
    }
}
