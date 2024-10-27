import src.*;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {

    //Enumeration pour pouvoir associer une chaine de caractère a une couleurs précise ici J = jaune par exemple 
    public enum Couleurs {
        J(Color.YELLOW),
        R(Color.RED),
        B(Color.BLUE),
        G(Color.GRAY),
        N(Color.BLACK);

        private Color couleurs;

        Couleurs(Color c){
            this.couleurs = c;
        }

        public Color getCouleurs(){
            return this.couleurs;
        }

        public static Color whatColor(String c){  //A voir pour rajouter une erreur si jamais aucune couleur ne correspond
            Color color = null;
            
            switch (c) {
                case "J":
                    color = J.getCouleurs();
                    break;
                case "R":
                    color = R.getCouleurs();
                    break;
                case "B":
                    color = B.getCouleurs();
                    break;
                case "G":
                    color = G.getCouleurs();
                    break;
                case "N":
                    color = N.getCouleurs();
                    break;
            }
            return color;
        }

    }

    public static void main(String[] args){

        
       

        //Initialisation des variables utiles pour tester le programme (TODO les mettres dans un fichier)
        int n = 0; //taille du carré pour notre image en sachant qu'on considère que la taille de la fenetre = taille du rectangle
        int m = 0; //nombre de centre fourni pour pouvoir construire la toile
        Point[] bordImage = {new Point(0,0), new Point(0,100), new Point(100,100), new Point(100,0)};
        Centre centre = new Centre(50, 50, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        Plan surface = new Plan(bordImage[0],bordImage[1],bordImage[2],bordImage[3],Color.WHITE);
        Qtree painting = new Qtree(centre, surface);
        
        //lecture du fichier d'entrée pour pouvoir collecter les données utiles a la construction de la toile
        try(Scanner scanner = new Scanner(new File("Fichier_Entree.txt"))){
            n = Integer.parseInt(scanner.nextLine().trim());
            m = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("n = " + n + " m = " + m);

            //On range tous les centres dans une liste (centers) ces centres sont lus dans le fichier d'entrée 
            //a voir pour rajouter un test pour voir si le nombre de centres m = a la taille de la liste 
            List<Centre> centers = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                String[] listeData = scanner.nextLine().trim().split(", ");
                int x = Integer.parseInt(listeData[0].trim());
                int y = Integer.parseInt(listeData[1].trim());
                Color c1 = Couleurs.whatColor(listeData[2].trim()); 
                Color c2 = Couleurs.whatColor(listeData[3].trim());
                Color c3 = Couleurs.whatColor(listeData[4].trim()); 
                Color c4 = Couleurs.whatColor(listeData[5].trim()); 
                centers.add(new Centre(x, y, c1, c2, c3, c4));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        Image draw = new Image (n, n);
        draw.setRectangle(painting.getPlan().getDownRight().getX(), painting.getPlan().getDownLeft().getX(), painting.getPlan().getDownRight().getY(), painting.getPlan().getUpright().getY(), Color.YELLOW);
        try{
            draw.save("final_draw");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
