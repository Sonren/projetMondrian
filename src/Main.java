package src;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;




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
    static Point[] bordImage = {new Point(0,0), new Point(0,1000), new Point(1000,1000), new Point(1000,0)};
    static Plan surface = new Plan(bordImage[1],bordImage[2],bordImage[3],bordImage[0],Color.BLUE); //taille de l'image 
    

    public static void lectureFichier(){
        //lecture du fichier d'entrée pour pouvoir collecter les données utiles a la construction de la toile
        try(Scanner scanner = new Scanner(new File("Fichier_Entree.txt"))){
            taille = Integer.parseInt(scanner.nextLine().trim());
            nbCentre = Integer.parseInt(scanner.nextLine().trim());

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

    //probleme je pense ne passe dans les feuille que lorsque chaque fils est fils (voir feuille brouillon)
    public static void toImage(Qtree qfinal, Image draw){
        if(qfinal.getNE().isEmpty()){
            draw.setRectangle(qfinal.getNE().getPlan().getDownLeft().getX(), 
                              qfinal.getNE().getPlan().getDownRight().getX(), 
                              qfinal.getNE().getPlan().getDownLeft().getY(), 
                              qfinal.getNE().getPlan().getUpLeft().getY(), 
                              qfinal.getNE().getPlan().getColor());    
        }else{
            toImage(qfinal.getNE(), draw);
        }

        if(qfinal.getNO().isEmpty()){
            draw.setRectangle(qfinal.getNO().getPlan().getDownLeft().getX(), 
                              qfinal.getNO().getPlan().getDownRight().getX(), 
                              qfinal.getNO().getPlan().getDownLeft().getY(), 
                              qfinal.getNO().getPlan().getUpLeft().getY(),                              
                              qfinal.getNO().getPlan().getColor());

        }else{
            toImage(qfinal.getNO(), draw);
        }

        if(qfinal.getSO().isEmpty()){
            draw.setRectangle(qfinal.getSO().getPlan().getDownLeft().getX(), 
                              qfinal.getSO().getPlan().getDownRight().getX(), 
                              qfinal.getSO().getPlan().getDownLeft().getY(), 
                              qfinal.getSO().getPlan().getUpLeft().getY(),                               
                              qfinal.getSO().getPlan().getColor());
        }else{
            toImage(qfinal.getSO(), draw);
        }

        if(qfinal.getSE().isEmpty()){
            draw.setRectangle(qfinal.getSE().getPlan().getDownLeft().getX(), 
                              qfinal.getSE().getPlan().getDownRight().getX(), 
                              qfinal.getSE().getPlan().getDownLeft().getY(), 
                              qfinal.getSE().getPlan().getUpLeft().getY(),                              
                              qfinal.getSE().getPlan().getColor());
        }else{
            toImage(qfinal.getSE(), draw);
        }
        try{
            draw.save("../final_draw");
        }
        catch (Exception e){
            System.out.println(e);
        }
        
    }

    public static void drawOutline(Qtree qfinal, Image draw){
        if(qfinal.getNE().noSon() && qfinal.getNO().noSon() && qfinal.getSE().noSon() && qfinal.getSO().noSon()){
            draw.setRectangle(qfinal.getSE().getPlan().getDownRight().getX()-epaisseur/2, qfinal.getSO().getPlan().getDownLeft().getX()+epaisseur/2, qfinal.getSE().getPlan().getDownRight().getY(), qfinal.getNO().getPlan().getUpLeft().getY(),  Color.BLACK);
        }
        if(!qfinal.getNE().noSon()){
            drawOutline(qfinal.getNE(), draw);
        }
        if(!qfinal.getNO().noSon()){
            drawOutline(qfinal.getNO(), draw);
        }
        if(!qfinal.getSE().noSon()){
            drawOutline(qfinal.getSE(), draw);
        }
        if(!qfinal.getSO().noSon()){
            drawOutline(qfinal.getSO(), draw);
        }
    }


    public static void recolor(List<Centre> listPairR, Qtree root){
        for (int i = 0; i < listPairR.size(); i++){
            Qtree temp = new Qtree(null, null); 
            temp = root.searchQtree(listPairR.get(i));  //a voir car je ne sais pas si temp est une copie ou un pointeur vers root(...) 
            temp.getPlan().setColor(listPairR.get(i).getC1()); //solution remplacer temp par root.searchQtree(listPairR.get(i))  mais complexite nul
            compressQTree(temp); //si c'est une copie cela ne va pas changer la valeur dans compressQtree 
        }
    }

    //a voir si l'on a bien le pere et non une feuille ce qui voudrait dire que ses fils sont nul
    public static void compressQTree(Qtree qpere){
        if (qpere.getNE().getPlan().getColor() == qpere.getNO().getPlan().getColor() && qpere.getSE().getPlan().getColor() == qpere.getSO().getPlan().getColor() && qpere.getNE().getPlan().getColor() == qpere.getSE().getPlan().getColor()){
            qpere.getPlan().setColor(qpere.getNE().getPlan().getColor());
            qpere.setNullSon();
            System.out.println("l'arbre a bien été compréssé");
        }
        System.out.println("il n'y a pas besoin de compresser l'arbre");
    }
    
    public static void toText(Qtree textQtree){

    }
    

    public static void main(String[] args){

        //Définition d'une HasMap pour pouvoir faire le toText
        HashMap<Color, String> colorNames = new HashMap<Color, String>();
        colorNames.put(Color.RED, "R");
        colorNames.put(Color.GREEN, "V");
        colorNames.put(Color.BLUE, "Be");   
        colorNames.put(Color.BLACK, "N");
        colorNames.put(Color.WHITE, "Ba");
        colorNames.put(Color.YELLOW, "J");
        colorNames.put(Color.GRAY, "G");
        colorNames.put(Color.DARK_GRAY, "DG");
        colorNames.put(Color.ORANGE, "O");
        colorNames.put(Color.LIGHT_GRAY, "LG");
        colorNames.put(Color.PINK, "Ro");
        colorNames.put(Color.CYAN, "C");
        colorNames.put(Color.MAGENTA, "M");


        lectureFichier();
        Qtree painting = new Qtree(centers.getFirst(), surface);
        painting.addQtree();
        painting.buildQtree(centers);
        painting.printTree(10);
        toText(painting);
        Image masterpiece = new Image (taille, taille);
        toImage(painting, masterpiece);
        painting.toText(colorNames);
        //recolor(centers, painting); a debugger 
        drawOutline(painting, masterpiece);
        System.out.println("hello world !");
    }
}

