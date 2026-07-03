package IU;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.function.Consumer;

/**
 * Pantalla de fin de partida: overlay + entrada de 3 iniciales + boton de volver al menu.
 * No conoce a Jugando, Juego, DAOs, ni WaveManager — se comunica solo mediante los
 * callbacks que recibe en el constructor.
 */
public class PantallaDerrota {

    private static final int NOMBRE_MAX_LEN = 3;
    private static final Rectangle MENU_BTN = new Rectangle(245, 210, 150, 35);

    private static final Font FONT_DERROTA  = new Font("Arial", Font.BOLD, 40);
    private static final Font FONT_MENU_BTN = new Font("Arial", Font.BOLD, 14);
    private static final Font FONT_NOMBRE   = new Font("Consolas", Font.BOLD, 28);

    private static final AlphaComposite ALPHA_OVERLAY = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
    private static final AlphaComposite ALPHA_FULL     = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

    private final Consumer<String> onNombreConfirmado;
    private final Runnable onVolverAlMenu;

    private boolean pidiendoNombre = false;
    private StringBuilder nombreIngresado = new StringBuilder();

    public PantallaDerrota(Consumer<String> onNombreConfirmado, Runnable onVolverAlMenu) {
        this.onNombreConfirmado = onNombreConfirmado;
        this.onVolverAlMenu = onVolverAlMenu;
    }

    /** Llamar cuando el jugador pierde, para empezar a pedir el nombre. */
    public void activar() {
        pidiendoNombre = true;
        nombreIngresado.setLength(0);
    }

    public boolean isPidiendoNombre() {
        return pidiendoNombre;
    }

    public void keyTyped(char c) {
        if (!pidiendoNombre) return;

        if (Character.isLetterOrDigit(c) && nombreIngresado.length() < NOMBRE_MAX_LEN) {
            nombreIngresado.append(Character.toUpperCase(c));
        }

        if (nombreIngresado.length() == NOMBRE_MAX_LEN) {
            pidiendoNombre = false;
            onNombreConfirmado.accept(nombreIngresado.toString());
        }
    }

    public void mouseReleased(int x, int y) {
        if (!pidiendoNombre && MENU_BTN.contains(x, y)) {
            onVolverAlMenu.run();
        }
    }

    public void render(Graphics g, int puntos) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(ALPHA_OVERLAY);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 640, 460);
        g2d.setComposite(ALPHA_FULL);

        g2d.setFont(FONT_DERROTA);
        String msg = "Derrota";
        int msgW = g2d.getFontMetrics().stringWidth(msg);
        g2d.setColor(Color.RED);
        g2d.drawString(msg, 320 - msgW / 2, 150);

        g2d.setFont(FONT_MENU_BTN);
        g2d.setColor(Color.WHITE);
        String puntosMsg = "Puntos: " + puntos;
        int pmW = g2d.getFontMetrics().stringWidth(puntosMsg);
        g2d.drawString(puntosMsg, 320 - pmW / 2, 185);

        if (pidiendoNombre) {
            String prompt = "Ingresa tus iniciales:";
            int prW = g2d.getFontMetrics().stringWidth(prompt);
            g2d.drawString(prompt, 320 - prW / 2, 215);

            StringBuilder display = new StringBuilder(nombreIngresado);
            while (display.length() < NOMBRE_MAX_LEN) display.append('_');
            g2d.setFont(FONT_NOMBRE);
            g2d.setColor(Color.YELLOW);
            String nombreStr = display.toString();
            int nW = g2d.getFontMetrics().stringWidth(nombreStr);
            g2d.drawString(nombreStr, 320 - nW / 2, 260);
        } else {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(MENU_BTN.x, MENU_BTN.y, MENU_BTN.width, MENU_BTN.height);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(MENU_BTN.x, MENU_BTN.y, MENU_BTN.width, MENU_BTN.height);
            String btnText = "Volver al Menu";
            int btnW = g2d.getFontMetrics().stringWidth(btnText);
            g2d.drawString(btnText, MENU_BTN.x + (MENU_BTN.width - btnW) / 2, MENU_BTN.y + 23);
        }
    }
}
