package entidades;

import java.awt.Rectangle;

public abstract class Entidad {
    private float x, y; // Float es preciso para especificar velocidad de movimiento
    private Rectangle colision;
    
    public Entidad(float x, float y, int tamanioColision) {
        this.x = x;
        this.y = y;
        colision = new Rectangle((int) x, (int) y, tamanioColision, tamanioColision);
    }

    public void mover(float x, float y){
        this.x += x;
        this.y += y;
        colision.x = (int) this.x;
        colision.y = (int) this.y;
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getColision() {
        return colision;
    }

    public void setColision(Rectangle colision) {
        this.colision = colision;
    }
    
    
}
