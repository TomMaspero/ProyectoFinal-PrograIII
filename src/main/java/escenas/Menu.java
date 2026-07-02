/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

import IU.MyButton;
import helpers.CargaGuarda;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static main.EstadoJuego.*;
import main.Juego;
import managers.MusicManager;

/**
 *
 * @author lucio
 */
public class Menu extends EscenaJuego implements MetodosEscena{
    private MyButton bJugar,bAjustes,bSalir;
    private final BufferedImage menuBg;

    private static final int W = 640;
    private static final int H = 460;

    public Menu(Juego juego) {
        super(juego);
        menuBg = CargaGuarda.getSpriteAtlas("menu.png");
        initButtons();
        MusicManager.playMenuTheme();
    }

    private void initButtons() {
        bJugar = new MyButton("Jugar",100,100,100,30);
    }

    @Override
    public void render(Graphics g) {
        // dibuja el fondo
        g.drawImage(menuBg, 0, 0, W, H, null);

        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bJugar.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) { }

    @Override
    public void mouseMoved(int x, int y) {
         bJugar.setMouseOver(false);
        if(bJugar.getBounds().contains(x,y)){
            bJugar.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bJugar.getBounds().contains(x,y)){
            bJugar.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (bJugar.isMousePressed() && bJugar.getBounds().contains(x, y)) {
            MusicManager.play("music/day_theme.mp3");
            SetEstadoJuego(JUGANDO);
        }
        bJugar.resetBooleans();
    }
    
}
