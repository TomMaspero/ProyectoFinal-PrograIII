/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package escenas;

import java.awt.Graphics;

/**
 *
 * @author lucio
 */
public interface MetodosEscena {
    public void render(Graphics g);
    public void mouseClicked(int x, int y);
    public void mouseMoved(int x, int y);
    public void mousePressed(int x, int y);
    public void mouseReleased(int x, int y);
}
