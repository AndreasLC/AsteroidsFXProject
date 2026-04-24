package dk.sdu.cbse.common.data;

import java.util.UUID;

public class Entity {
    private final UUID ID = UUID.randomUUID();

    private String type;

    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;

    private String color = "WHITE"; // Standard color of Entities and bullets.
    private String fillColor = "BLACK"; // Standard fill color.

    public String getID()
    {
        return ID.toString();
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    private int lives;
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    private int health;
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    private long invisibleUntil = 0;
    public boolean isInvisible() { return System.nanoTime() < invisibleUntil; }
    public void setInvisibleUntil(long durationNano) { invisibleUntil = System.nanoTime() + durationNano; }

    private long blinkRedUntil = 0;
    public boolean isBlinkingRed() { return System.nanoTime() < blinkRedUntil; }
    public void setBlinkRedUntil(long durationNano) { blinkRedUntil = System.nanoTime() + durationNano; }

    public void setPolygonCoordinates (double... coordinates) // Sets the coordinates of the polygon. (varargs ...)
    {
        this.polygonCoordinates=coordinates;
    }

    public double[] getPolygonCoordinates() // getter for polygon coordinates
    {
        return polygonCoordinates;
    }

    public String getColor() // getter for color
    {
        return color;
    }
    public void setColor(String color) // Setter for color
    {
        this.color = color;
    }
    public String getFillColor() // getter for fill color
    {
        return fillColor;
    }
    public void setFillColor(String fillColor) // setter for fill color
    {
        this.fillColor = fillColor;
    }

    // Getter and setter for coordinates,radius and rotation.
    public void setX(double x)
    {
        this.x = x;
    }
    public double getX()
    {
       return x;
    }
    public void setY(double y)
    {
        this.y=y ;
    }
    public double getY()
    {
        return y;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
    public double getRotation()
    {
        return rotation;
    }
    public void setRadius(Float radius)
    {
        this.radius = radius;
    }
    public float getRadius()
    {
        return radius;
    }


}
