import src.*;
import java.awt.Color;

import src.Image;
import src.Point;


public class Main {

    public static void main(String[] args){
        Point[] bordImage = {new Point(0,0), new Point(0,100), new Point(100,100), new Point(100,0)};
        Centre centre = new Centre(50, 50, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        Plan surface = new Plan(bordImage[0],bordImage[1],bordImage[2],bordImage[3],Color.WHITE);
        Qtree painting = new Qtree(centre, surface);
        Image draw = new Image (1000, 1000);
        draw.setRectangle(painting.getPlan().getDownRight().getX(), painting.getPlan().getDownLeft().getX(), painting.getPlan().getDownRight().getY(), painting.getPlan().getUpright().getY(), Color.YELLOW);
        try{
            draw.save("final_draw");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
