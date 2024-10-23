package src;

//Un plan représente un rectangle ( en 2 dimension donc)
//qui a 4 points (upLeft, upRight, downLeft, downRight)
//chaque point représente un coin de notre plan
public class Plan {

    //Attributs
    private Point upLeft, upRight, downRight, downLeft;
    private Color col;

    //Constructeur
    public Plan(Point upLeft, Point upRight, Point downRight, Point downLeft, Color c){
        this.upLeft = upLeft;
        this.upRight = upRight;
        this.downRight = downRight;
        this.downLeft = downLeft;
        this.col = c;
    }

    //Méthodes : 
    //Getter
    public Point getUpLeft() {
        return this.upLeft;
    }

    public Point getUpright() {
        return this.upRight;
    }

    public Point getDownRight() {
        return this.downRight;
    }

    public Point getDownLeft() {
        return this.downLeft;
    }

    public Color getColor() {
        return this.col;
    }

    //Setter
    public void setUpLeft(Point upLeft) {
        this.upLeft = upLeft;
    }

    public void setUpLeft(int x, int y) {
        this.upLeft.setX(x);
        this.upLeft.setY(y);
    }

    public void setUpright(Point upRight) {
        this.upRight = upRight;
    }

    public void setUpright(int x, int y) {
        this.upRight.setX(x);
        this.upRight.setY(y);
    }

    public void setDownRight(Point downRight) {
        this.downRight = downRight;
    }

    public void setDownRight(int x, int y) {
        this.downRight.setX(x);
        this.downRight.setY(y);
    }


    public void setDownLeft(Point downLeft) {
        this.downLeft = downLeft;
    }

    public void setDownLeft(int x, int y) {
        this.downLeft.setX(x);
        this.downLeft.setY(y);
    }

    public void setColor(Color co){
        this.col = co;
    }
    
}
