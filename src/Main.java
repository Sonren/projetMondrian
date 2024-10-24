package src;
import java.awt.Color;
import java.io.File;


public class Main {

    public static void main(String[] args){
        Point[] bordImage = {new Point(0,0), new Point(0,100), new Point(100,100), new Point(100,0)};
        //Centre centre = new Centre(50, 50, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        //Plan surface = new Plan(bordImage[0],bordImage[1],bordImage[2],bordImage[3],Color.WHITE);
        Image draw = new Image (1000, 2000);
        draw.setRectangle(0, 100, 0, 100, Color.black);
        try{
            draw.save("final_draw");
           /*  if (f.createNewFile()){
                System.out.println("Fichier créer");
            }else{
                System.out.println("Le fichier existe déja");
            }*/
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
