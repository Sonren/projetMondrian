package src;

public class Point {
    private int x; //Abscisse
    private int y; //Ordonnée

    //Constructeur
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    } 
    
    //Getter
    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    //Setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
