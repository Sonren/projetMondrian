package src;

import java.awt.Color;
import java.io.File;
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

    //Initialisation des variables utiles pour tester le programme 
    static int taille = 0; //taille du carré pour notre image en sachant qu'on considère que la taille de la fenetre = taille du rectangle
    static int nbCentre = 0; //nombre de centre fourni pour pouvoir construire la toile
    public static int epaisseur = 0; //épaisseur des trait qui sépare chaque régions (doit être impair)
    static int nbpairRecolor = 0; //nombre de paire(point, couleur) de recoloriage pour pour recolorer la zone dans laquelle se situe le point
    static List<Centre> centers = new ArrayList<>(); // Liste de tous les centres qui vont etre fournir dans le fichier en entrée
    static List<Centre> lpairRecolor = new ArrayList<>(); // liste de toutes les PairRecolor 
    static Point[] bordImage = {new Point(0,0), new Point(0,100), new Point(100,100), new Point(100,0)};
    static Plan surface = new Plan(bordImage[0],bordImage[1],bordImage[2],bordImage[3],Color.WHITE);
    

    public static void lectureFichier(){
        //lecture du fichier d'entrée pour pouvoir collecter les données utiles a la construction de la toile
        try(Scanner scanner = new Scanner(new File("Fichier_Entree.txt"))){
            taille = Integer.parseInt(scanner.nextLine().trim());
            nbCentre = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("n = " + taille + " m = " + nbCentre);

            //On range tous les centres dans une liste (centers) ces centres sont lus dans le fichier d'entrée 
            //a voir pour rajouter un test pour voir si le nombre de centres m = a la taille de la liste 
            for (int i = 0; i < nbCentre; i++) {
                String[] listeData = scanner.nextLine().trim().split(", ");
                int x = Integer.parseInt(listeData[0].trim());
                int y = Integer.parseInt(listeData[1].trim());
                Color c1 = Couleurs.whatColor(listeData[2].trim()); 
                Color c2 = Couleurs.whatColor(listeData[3].trim());
                Color c3 = Couleurs.whatColor(listeData[4].trim()); 
                Color c4 = Couleurs.whatColor(listeData[5].trim()); 
                centers.add(new Centre(x, y, c1, c2, c3, c4));
            }
            epaisseur = Integer.parseInt(scanner.nextLine().trim());
            nbpairRecolor = Integer.parseInt(scanner.nextLine().trim());

            //La liste de toutes les PairRecolor extraite du fichier en entrée
            for (int j = 0; j < nbpairRecolor; j++){
                String[] listdataPair = scanner.nextLine().trim().split(", ");
                int z = Integer.parseInt(listdataPair[0].trim());
                int w = Integer.parseInt(listdataPair[1].trim());
                Color tempcolor = Couleurs.whatColor(listdataPair[2].trim());
                lpairRecolor.add(new Centre(z, w, tempcolor));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void toImage(Qtree qfinal){
        Image draw = new Image (taille, taille);
        draw.setRectangle(0,taille,0,taille, Couleurs.B.getCouleurs());
        draw.setRectangle(qfinal.getPlan().getDownLeft().getX(), qfinal.getPlan().getDownRight().getX(), qfinal.getPlan().getDownLeft().getY(), qfinal.getPlan().getUpLeft().getY(), Couleurs.B.getCouleurs());
        //draw.setRectangle(qfinal.getNO().getPlan().getDownLeft().getX(), qfinal.getNO().getPlan().getDownRight().getX(), qfinal.getNO().getPlan().getDownLeft().getY(), qfinal.getNO().getPlan().getUpLeft().getY(), qfinal.getCentre().getC1());
        try{
            draw.save("final_draw");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void reColor(){
        
    }

    

    public static void main(String[] args){
        lectureFichier();
        Qtree painting = new Qtree(centers.getFirst(), surface);
        toImage(painting);
        System.out.println("hello world !");
    }
}

