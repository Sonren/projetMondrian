package src;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class MonBoTablo {

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

        public static String colorToString(Color col) {
            if (Color.RED.equals(col)) {
                return "R";
            } else if (Color.BLACK.equals(col)) {
                return "N";
            } else if (Color.BLUE.equals(col)) {
                return "B";
            } else if (Color.YELLOW.equals(col)) {
                return "J";
            } else if (Color.GRAY.equals(col)) {
                return "G";
            } else {
                System.out.println("Couleur inconnue !");
                return null; 
            }
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
        try(Scanner scanner = new Scanner(new File("RivardPiard/test/Fichier_Entree.txt"))){
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
    public static void toImage(Qtree qfinal, Image draw, String file){
        if(qfinal.getNE().isEmpty()){
            draw.setRectangle(qfinal.getNE().getPlan().getDownLeft().getX(), 
                              qfinal.getNE().getPlan().getDownRight().getX(), 
                              qfinal.getNE().getPlan().getDownLeft().getY(), 
                              qfinal.getNE().getPlan().getUpLeft().getY(), 
                              qfinal.getNE().getPlan().getColor());    
        }else{
            toImage(qfinal.getNE(), draw, file);
        }

        if(qfinal.getNO().isEmpty()){
            draw.setRectangle(qfinal.getNO().getPlan().getDownLeft().getX(), 
                              qfinal.getNO().getPlan().getDownRight().getX(), 
                              qfinal.getNO().getPlan().getDownLeft().getY(), 
                              qfinal.getNO().getPlan().getUpLeft().getY(),                              
                              qfinal.getNO().getPlan().getColor());

        }else{
            toImage(qfinal.getNO(), draw, file);
        }

        if(qfinal.getSO().isEmpty()){
            draw.setRectangle(qfinal.getSO().getPlan().getDownLeft().getX(), 
                              qfinal.getSO().getPlan().getDownRight().getX(), 
                              qfinal.getSO().getPlan().getDownLeft().getY(), 
                              qfinal.getSO().getPlan().getUpLeft().getY(),                               
                              qfinal.getSO().getPlan().getColor());
        }else{
            toImage(qfinal.getSO(), draw, file);
        }

        if(qfinal.getSE().isEmpty()){
            draw.setRectangle(qfinal.getSE().getPlan().getDownLeft().getX(), 
                              qfinal.getSE().getPlan().getDownRight().getX(), 
                              qfinal.getSE().getPlan().getDownLeft().getY(), 
                              qfinal.getSE().getPlan().getUpLeft().getY(),                              
                              qfinal.getSE().getPlan().getColor());
        }else{
            toImage(qfinal.getSE(), draw, file);
        }
        try{
            draw.save(file);
        }
        catch (Exception e){
            System.out.println(e);
        }
        
    }

    public static void drawOutline(Qtree qfinal, Image draw, String file){
    
        int firstX = qfinal.getCentre().getCoordPoint().getX() - epaisseur/2;
        int lastX = firstX + epaisseur;
        int firstY = qfinal.getPlan().getDownRight().getY();
        int lastY = qfinal.getPlan().getUpright().getY();
        draw.setRectangle(firstX, lastX ,firstY , lastY , Color.BLACK);
        int secondX = qfinal.getPlan().getDownLeft().getX();
        int finishX = qfinal.getPlan().getDownRight().getX();
        int secondY = qfinal.getCentre().getCoordPoint().getY() - epaisseur/2;
        int finishY = secondY + epaisseur;
        draw.setRectangle(secondX, finishX, secondY, finishY, Color.BLACK);
        if(!(qfinal.getNE().getCentre() == null)){
            drawOutline(qfinal.getNE(), draw, file);
        }
        if(!(qfinal.getNO().getCentre() == null)){
            drawOutline(qfinal.getNO(), draw, file);
        }
        if(!(qfinal.getSE().getCentre() == null)){
            drawOutline(qfinal.getSE(), draw, file);
        }
        if(!(qfinal.getSO().getCentre() == null)){
            drawOutline(qfinal.getSO(), draw, file);
        }
        try{
            draw.save(file);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    //fonction qui sert a retouver le parent d'un noeud 
    public static Qtree findParent(Qtree root, Qtree target) {
        if (root == null || root.estFeuille()) {
            return null; // Si c'est une feuille ou un arbre vide, pas de parent
        }
    
        // Vérifie si le nœud courant est le parent du nœud cible
        if (root.getNE() == target || root.getNO() == target || 
            root.getSE() == target || root.getSO() == target) {
            return root;
        }
    
        // Recherche récursive dans les fils
        Qtree found = findParent(root.getNE(), target);
        if (found != null) return found;
    
        found = findParent(root.getNO(), target);
        if (found != null) return found;
    
        found = findParent(root.getSE(), target);
        if (found != null) return found;
    
        return findParent(root.getSO(), target);
    }
    

    public static void majRectangle(Qtree rect, Image rectDraw){
        rectDraw.setRectangle(rect.getPlan().getDownLeft().getX(), rect.getPlan().getDownRight().getX(), rect.getPlan().getDownLeft().getY(), rect.getPlan().getUpLeft().getY(), rect.getPlan().getColor());
    }

    public static void recolor(List<Centre> listPairR, Qtree root, Image finaldraw, String file){
        System.err.println("\n");
        for (int i = 0; i < nbpairRecolor; i++){
            Qtree temp = root.searchLeaf(listPairR.get(i)); 
            temp.getPlan().setColor(listPairR.get(i).getC1());
            majRectangle(temp, finaldraw);
            Qtree parentTemp = findParent(root, temp);
            Qtree parenDeParent = findParent(root, parentTemp);  //mauvaise compléxité car on le fait deux fois mais évite de devoir le faire pour tout l'arbre
            drawOutline(parenDeParent, finaldraw, file);
        }
        compressQTree(root, finaldraw, file);
        try{
            finaldraw.save(file);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    //creer une fonction qui va retrouver le pere a partir des fils a voir si cela est mieux que introduire un attribut parent
    public static void compressQTree(Qtree qroot, Image compressDraw, String file){
        if (qroot == null) return;
        if((!qroot.getNE().estFeuille())){
            compressQTree(qroot.getNE(), compressDraw, file);
        }else{
            if(qroot.getNE().getPlan().getColor() == qroot.getNO().getPlan().getColor() &&
               qroot.getSE().getPlan().getColor() == qroot.getSO().getPlan().getColor() &&
               qroot.getNE().getPlan().getColor() == qroot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(qroot.getPlan().getDownLeft().getX(), 
                                            qroot.getPlan().getDownRight().getX(), 
                                            qroot.getPlan().getDownLeft().getY(), 
                                            qroot.getPlan().getUpLeft().getY(),
                                            qroot.getNO().getPlan().getColor());
                    qroot.getPlan().setColor(qroot.getNE().getPlan().getColor());
                    qroot.setNullSon();
                    qroot.setCenter(null);
                    System.out.println("l'arbre a été compressé");
                    return;                         
            }
        }
        if((!qroot.getNO().estFeuille())){
            compressQTree(qroot.getNO(), compressDraw, file);
        }else{
            if(qroot.getNE().getPlan().getColor() == qroot.getNO().getPlan().getColor() &&
               qroot.getSE().getPlan().getColor() == qroot.getSO().getPlan().getColor() &&
               qroot.getNE().getPlan().getColor() == qroot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(qroot.getPlan().getDownLeft().getX(), 
                                            qroot.getPlan().getDownRight().getX(), 
                                            qroot.getPlan().getDownLeft().getY(), 
                                            qroot.getPlan().getUpLeft().getY(),
                                            qroot.getNO().getPlan().getColor());
                    qroot.getPlan().setColor(qroot.getNE().getPlan().getColor());
                    qroot.setNullSon();
                    qroot.setCenter(null);
                    System.out.println("l'arbre a été compressé");
                    return;                         
            }
        }
        if((!qroot.getSE().estFeuille())){
            compressQTree(qroot.getSE(), compressDraw, file);
        }else{
            if(qroot.getNE().getPlan().getColor() == qroot.getNO().getPlan().getColor() &&
               qroot.getSE().getPlan().getColor() == qroot.getSO().getPlan().getColor() &&
               qroot.getNE().getPlan().getColor() == qroot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(qroot.getPlan().getDownLeft().getX(), 
                                            qroot.getPlan().getDownRight().getX(), 
                                            qroot.getPlan().getDownLeft().getY(), 
                                            qroot.getPlan().getUpLeft().getY(),
                                            qroot.getNO().getPlan().getColor());
                    qroot.getPlan().setColor(qroot.getNE().getPlan().getColor());
                    qroot.setNullSon();
                    qroot.setCenter(null);
                    System.out.println("l'arbre a été compressé");
                    return;                         
            }
        }
        if((!qroot.getSO().estFeuille())){
            compressQTree(qroot.getSO(), compressDraw, file);
        }else{
            if(qroot.getNE().getPlan().getColor() == qroot.getNO().getPlan().getColor() &&
               qroot.getSE().getPlan().getColor() == qroot.getSO().getPlan().getColor() &&
               qroot.getNE().getPlan().getColor() == qroot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(qroot.getPlan().getDownLeft().getX(), 
                                            qroot.getPlan().getDownRight().getX(), 
                                            qroot.getPlan().getDownLeft().getY(), 
                                            qroot.getPlan().getUpLeft().getY(),
                                            qroot.getNO().getPlan().getColor());
                    qroot.getPlan().setColor(qroot.getNE().getPlan().getColor());
                    qroot.setNullSon();
                    qroot.setCenter(null);
                    System.out.println("l'arbre a été compressé");
                    return;                         
            }
        }
        try{
            compressDraw.save(file);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    

    public static void toText(Qtree qtext, String filePath) {
        // Construire le texte à écrire dans un StringBuilder
        StringBuilder textBuilder = new StringBuilder();

        // Appel récursif pour construire le texte du quadtree
        buildTreeText(qtext, textBuilder);

        // Écriture dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(textBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildTreeText(Qtree qtext, StringBuilder textBuilder) {
        if (qtext == null) return;

        if (qtext.isEmpty()) {
            // Si le nœud est une feuille, ajouter sa couleur
            textBuilder.append(Couleurs.colorToString(qtext.getPlan().getColor()));
        } else {
            // Ajouter une parenthèse ouvrante
            textBuilder.append("(");
            
            // Parcours récursif pour les fils
            buildTreeText(qtext.getNE(), textBuilder);
            buildTreeText(qtext.getNO(), textBuilder);
            buildTreeText(qtext.getSE(), textBuilder);
            buildTreeText(qtext.getSO(), textBuilder);
            
            // Ajouter une parenthèse fermante
            textBuilder.append(")");
        }
    }
    

    public static void main(String[] args){
        String text1 = "RivardPiard/sortie/RivardPiardFichierEntree_B.txt";
        String text2 = "RivardPiard/sortie/RivardPiardFichierEntree_R.txt";
        String draw1 = "RivardPiard/sortie/RivardPiardFichierEntree_B.png";
        String draw2 = "RivardPiard/sortie/RivardPiardFichierEntree_R.png";

        lectureFichier();
        Qtree painting = new Qtree(centers.get(0), surface);
        painting.addQtree();
        painting.buildQtree(centers);
        painting.printTree(10);
        Image masterpiece = new Image (taille, taille);
        toImage(painting, masterpiece, draw1);
        toText(painting, text1);
        drawOutline(painting, masterpiece, draw1);
        recolor(lpairRecolor, painting, masterpiece, draw2);
        drawOutline(painting, masterpiece, draw2);
        toText(painting, text2);
    }
}


