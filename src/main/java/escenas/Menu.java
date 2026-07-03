/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

import IU.MyButton;
import helpers.CargaGuarda;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static main.EstadoJuego.*;
import main.Juego;
import managers.MusicManager;

/**
 *
 * @author lucio
 */
public class Menu extends EscenaJuego implements MetodosEscena{
    private MyButton bJugar;
    private MyButton bHighscores;
    private final BufferedImage menuBg;

    private JTable tablaHighscores;
    private JScrollPane scrollHighscores;
    private boolean mostrandoHighscores = false;

    private static final int W = 640;
    private static final int H = 460;

    public Menu(Juego juego) {
        super(juego);
        menuBg = CargaGuarda.getSpriteAtlas("menu.png");
        initButtons();
        initHighscoresTable();
        MusicManager.playMenuTheme();
    }

    private void initButtons() {
        bJugar = new MyButton("Jugar",280,200,100,30);
        bHighscores = new MyButton("Highscores",280,240,100,30);
    }

    private void initHighscoresTable() {
        tablaHighscores = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollHighscores = new JScrollPane(tablaHighscores);
        // Coordenadas reales de pantalla (1280x920) — a la derecha del boton Highscores
        scrollHighscores.setBounds(780, 440, 460, 300);
    }

    @Override
    public void render(Graphics g) {
        // dibuja el fondo
        g.drawImage(menuBg, 0, 0, W, H, null);

        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bJugar.draw(g);
        bHighscores.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) { }

    @Override
    public void mouseMoved(int x, int y) {
        bJugar.setMouseOver(false);
        if(bJugar.getBounds().contains(x,y)){
            bJugar.setMouseOver(true);
        }

        bHighscores.setMouseOver(false);
        if(bHighscores.getBounds().contains(x,y)){
            bHighscores.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bJugar.getBounds().contains(x,y)){
            bJugar.setMousePressed(true);
        }
        if(bHighscores.getBounds().contains(x,y)){
            bHighscores.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (bJugar.isMousePressed() && bJugar.getBounds().contains(x, y)) {
            ocultarHighscores();
            MusicManager.play("music/day_theme.mp3");
            getJuego().resetJugando();
            SetEstadoJuego(JUGANDO);
        }

        if (bHighscores.isMousePressed() && bHighscores.getBounds().contains(x, y)) {
            if (mostrandoHighscores) {
                ocultarHighscores();
            } else {
                mostrarHighscores();
            }
        }

        bJugar.resetBooleans();
        bHighscores.resetBooleans();
    }

    private void mostrarHighscores() {
        List<Map<String, Object>> filas = getJuego().getPartidaDAO().findHighscores();

        String[] columnas = {"Jugador", "Zombies eliminados", "Puntaje"};
        Object[][] datos = new Object[filas.size()][3];
        for (int i = 0; i < filas.size(); i++) {
            Map<String, Object> fila = filas.get(i);
            datos[i][0] = fila.get("nombre");
            datos[i][1] = fila.get("zombies_eliminados");
            datos[i][2] = fila.get("puntuacion");
        }

        tablaHighscores.setModel(new DefaultTableModel(datos, columnas));

        getJuego().getPantalla().add(scrollHighscores);
        getJuego().getPantalla().revalidate();
        getJuego().getPantalla().repaint();
        mostrandoHighscores = true;
    }

    private void ocultarHighscores() {
        if (!mostrandoHighscores) return;
        getJuego().getPantalla().remove(scrollHighscores);
        getJuego().getPantalla().revalidate();
        getJuego().getPantalla().repaint();
        mostrandoHighscores = false;
    }

}
