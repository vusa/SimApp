package org.simapp.floor;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author vusa
 */
public abstract class FloorItem {

    private int x;
    private int y;
    Image sprite;
    String sensorType;


    public FloorItem() {
    }

    public FloorItem(String image, int x, int y) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(image));
        sprite = ii.getImage();
        this.x = x;
        this.y = y;
    }

    public void setImage(String image) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(image));
        sprite = ii.getImage();
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public double getDistanceFrom(FloorItem i) {
        return Math.sqrt(Math.hypot(i.x - this.x, i.y - this.y));
    }
    
    public String getSeonsorType(FloorItem i){
        return i.sensorType;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
}
