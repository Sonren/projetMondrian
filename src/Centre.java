package src;
import java.awt.*;

public class Centre {

    //Attributs
    private Point coordPoint; //Cordonnées du centre

    //Couleurs des carrés autour du centre
    private Color c1;
    private Color c2;
    private Color c3;
    private Color c4;


    //Constructeur
    public Centre(int x, int y, Color c1, Color c2, Color c3, Color c4) {
        coordPoint.setX(x);
        coordPoint.setY(y);
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c3 = c3;
        this.c4 = c4;
    }

    //Méthodes : 
    //Getter

    public Point getCoordPoint(){
        return this.coordPoint;
    }
    
    public Color getC1(){
        return this.c1;
    }

    public Color getC2(){
        return this.c2;
    }

    public Color getC3(){
        return this.c3;
    }
    
    public Color getC4(){
        return this.c4;
    }

   
}


