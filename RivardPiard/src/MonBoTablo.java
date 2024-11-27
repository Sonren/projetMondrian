
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


        /**
         * Retourne la couleur associée à cette constante
         * @return la couleur
         */
        public Color getCouleurs(){
            return this.couleurs;
        }
        /**
         * Permet de récupérer une couleur associée à un String donné
         * @param c la chaine de caractère correspondant à la couleur
         * @return la couleur associée
         */
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

        /**
         * Renvoie la chaine de caractere correspondant a la couleur.
         * @param col la couleur dont on cherche le nom
         * @return la chaine de caractere correspondant a la couleur
         * @throws RuntimeException si la couleur n'est pas reconnue
         */
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
    

    /**
     * Lit le fichier d'entrée pour collecter les données utiles 
     * à la construction de la toile. Le fichier est composé de 
     * 7 lignes : 
     * 1. La taille du carré de l'image
     * 2. Le nombre de centre
     * 3. Les centres de la toile (leur position et les couleurs associées)
     * 4. L'épaisseur des traits qui séparent chaque région
     * 5. Le nombre de paires de recoloration
     * 6. Les paires de recoloration (leur position et la couleur associée)
     * Complexité : O(n), où n est le nombre de lignes dans le fichier.
     */
    public static void lectureFichier(){
        //lecture du fichier d'entrée pour pouvoir collecter les données utiles a la construction de la toile
        try(Scanner scanner = new Scanner(new File("RivardPiard/test/Exemple1.txt"))){
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

    /**
     * Construit l'image final en parcourant l'arbre Qtree et en dessinant
     * les rectangles correspondants aux feuilles de l'arbre.
     * Complexité : O(n), où n est le nombre de feuilles dans le Qtree.
     * 
     * @param qfinal l'arbre Qtree
     * @param draw l'image a modifie
     * @param file le nom du fichier a sauvegarder
     */
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

/**
 * Trace les contours du Qtree sur l'image donnée.
 * 
 * Cette méthode prend un Qtree et dessine ses contours en noir sur l'image spécifiée.
 * Elle utilise l'épaisseur spécifiée pour tracer les lignes séparant les régions.
 * Si le Qtree a des sous-arbres, la méthode est appelée récursivement sur ceux-ci.
 * Complexité : O(n), où n est le nombre de nœuds dans le Qtree.
 * 
 * @param qfinal le Qtree dont les contours doivent être dessinés
 * @param draw l'objet Image sur lequel dessiner
 * @param file le nom du fichier où l'image doit être sauvegardée après le dessin
 */
    public static void drawOutline(Qtree qfinal, Image draw, String file){
        if(qfinal != null){

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
    }

    /**
     * Retourne le parent du nœud cible dans l'arbre.
     * Complexité : O(n), où n est le nombre de nœuds dans le Qtree.
     * 
     * @param root   racine de l'arbre
     * @param target nœud cible
     * @return le parent du nœud cible, ou null si le nœud cible est null ou n'a pas de parent
     */
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
    

    /**
     * Modifie l'image rectDraw en mettant à jour le rectangle qui correspond au Qtree rect.
     * On utilise les coordonnées du plan de rect pour définir le rectangle.
     */
    public static void majRectangle(Qtree rect, Image rectDraw){
        rectDraw.setRectangle(rect.getPlan().getDownLeft().getX(), rect.getPlan().getDownRight().getX(), rect.getPlan().getDownLeft().getY(), rect.getPlan().getUpLeft().getY(), rect.getPlan().getColor());
    }

    /**
     * Recolorie un Qtree en prenant en compte les paires de centre/récolleur fournie en argument.
     * Pour chaque paire, on recherche le Qtree qui correspond à ce centre et on le recolore.
     * On remplace ensuite la forme de ce Qtree par sa couleur.
     * Enfin, on appelle la fonction compressQTree pour supprimer les sous-plans qui ont la même couleur.
     * Complexité : O(m + k * h), où m est le nombre de paires, k est le nombre de nœuds parcourus pour chaque recoloration, et h est la hauteur du Qtree.
     * @param listPairR La liste des paires de centre/récolleur
     * @param root Le Qtree à recolorier
     * @param finaldraw L'image qui sera modifiée pour prendre en compte la recoloration
     * @param file Le nom du fichier où sera enregistrée l'image
     */
    public static void recolor(List<Centre> listPairR, Qtree root, Image finaldraw, String file){
        System.err.println("\n");
        for (int i = 0; i < nbpairRecolor; i++){
            Qtree temp = root.searchLeaf(listPairR.get(i)); 
            temp.getPlan().setColor(listPairR.get(i).getC1());
            majRectangle(temp, finaldraw);
            Qtree parentTemp = findParent(root, temp);
            Qtree parenDeParent = findParent(root, parentTemp);  
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

    /**
     * Fonction qui permet de compresser un Qtree en supprimant les sous-plans qui ont la même couleur.
     * La compression est faite récursivement en explorant les sous-plans de l'arbre.
     * Si un sous-plan a la même couleur que ses voisins, il est supprimé et son plan est coloré en conséquence.
     * Complexité : O(n), où n est le nombre de nœuds dans le Qtree
     * @param qroot Le Qtree à compresser
     * @param compressDraw L'image qui sera modifiée pour prendre en compte la compression
     * @param file Le nom du fichier où sera enregistrée l'image
     */
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
    

    /**
     * Exporte la représentation textuelle du Qtree qtext dans un fichier.
     * La représentation textuelle est une chaîne de caractères qui contient
     * la couleur de chaque feuille du Qtree, ainsi que des parenthèses pour
     * indiquer la structure de l'arbre.
     * Complexité : O(n), où n est le nombre de nœuds dans le Qtree.
     * @param qtext le Qtree dont on veut extraire la représentation textuelle
     * @param filePath le chemin du fichier où sera écrite la représentation textuelle
     */
    public static void toText(Qtree qtext, String filePath) {
        StringBuilder textBuilder = new StringBuilder();

        buildTreeText(qtext, textBuilder);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(textBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute la représentation textuelle du Qtree qtext dans le StringBuilder textBuilder.
     * Si le nœud est une feuille, ajoute simplement sa couleur, sinon ajoute une parenthèse
     * ouvrante, parcourt récursivement les fils et ajoute une parenthèse fermante.
     * Complexité : O(n), où n est le nombre de nœuds dans le Qtree.
     * @param qtext le Qtree dont on veut extraire la représentation textuelle
     * @param textBuilder le StringBuilder dans lequel on écrit la représentation textuelle
     */
    private static void buildTreeText(Qtree qtext, StringBuilder textBuilder) {
        if (qtext == null) return;

        if (qtext.isEmpty()) {
            // Si le nœud est une feuille, ajouter sa couleur
            textBuilder.append(Couleurs.colorToString(qtext.getPlan().getColor()));
        } else {
            // Ajouter une parenthèse ouvrante
            textBuilder.append("(");
            
            // Parcours récursif pour les fils
            buildTreeText(qtext.getNO(), textBuilder);
            buildTreeText(qtext.getNE(), textBuilder);
            buildTreeText(qtext.getSE(), textBuilder);
            buildTreeText(qtext.getSO(), textBuilder);
            
            // Ajouter une parenthèse fermante
            textBuilder.append(")");
        }
    }
    

//----------------------------------------------Ttree------------------------------------------------------------------------------------


    /**
     * Lit le fichier d'entrée pour collecter les données utiles 
     * à la construction de la toile. Le fichier est composé de 
     * 7 lignes : 
     * 1. La taille du carré de l'image
     * 2. Le nombre de centre
     * 3. Les centres de la toile (leur position et les couleurs associées)
     * 4. L'épaisseur des traits qui séparent chaque région
     * 5. Le nombre de paires de recoloration
     * 6. Les paires de recoloration (leur position et la couleur associée)
     * Complexité : O(n + m), où n est le nombre de centres et m le nombre de paires de recoloration.
     */
    public static void lectureFichierTtree(){
        //lecture du fichier d'entrée pour pouvoir collecter les données utiles a la construction de la toile
        try(Scanner scanner = new Scanner(new File("RivardPiard/test/Exemple2.txt"))){
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
                centers.add(new Centre(x, y, c1, c2, c3, null)); 
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



    /**
     * Dessine le Ttree en noir sur l'image draw et la sauvegarde 
     * dans le fichier file.
     * Complexité : O(n), où n est le nombre de nœuds dans le Ttree.
     * @param tfinal Ttree dont on veut dessiner le contour.
     * @param draw Image sur laquelle on veut dessiner le contour.
     * @param file Chemin du fichier dans lequel on veut sauvegarder l'image.
     */
public static void toImageTtree(Ttree tfinal, Image draw, String file){
        if(tfinal.getNE().isEmpty()){
            draw.setRectangle(tfinal.getNE().getPlan().getDownLeft().getX(), 
                              tfinal.getNE().getPlan().getDownRight().getX(), 
                              tfinal.getNE().getPlan().getDownLeft().getY(), 
                              tfinal.getNE().getPlan().getUpLeft().getY(), 
                              tfinal.getNE().getPlan().getColor());    
        }else{
            toImageTtree(tfinal.getNE(), draw, file);
        }

        if(tfinal.getNO().isEmpty()){
            draw.setRectangle(tfinal.getNO().getPlan().getDownLeft().getX(), 
                              tfinal.getNO().getPlan().getDownRight().getX(), 
                              tfinal.getNO().getPlan().getDownLeft().getY(), 
                              tfinal.getNO().getPlan().getUpLeft().getY(),                              
                              tfinal.getNO().getPlan().getColor());

        }else{
            toImageTtree(tfinal.getNO(), draw, file);
        }

        if(tfinal.getSE().isEmpty()){
            draw.setRectangle(tfinal.getSE().getPlan().getDownLeft().getX(), 
                              tfinal.getSE().getPlan().getDownRight().getX(), 
                              tfinal.getSE().getPlan().getDownLeft().getY(), 
                              tfinal.getSE().getPlan().getUpLeft().getY(),                              
                              tfinal.getSE().getPlan().getColor());
        }else{
            toImageTtree(tfinal.getSE(), draw, file);
        }
        try{
            draw.save(file);
        }
        catch (Exception e){
            System.out.println(e);
        }
        
    }


    /**
     * Écrit la représentation textuelle du Ttree ttext dans un fichier au chemin filePath.
     * O(n), où n est le nombre de nœuds dans le Ttree.
     * @param ttext Ttree dont on veut écrire la représentation textuelle.
     * @param filePath Chemin du fichier dans lequel on veut écrire la représentation textuelle.
     */
    public static void toTextTtree(Ttree ttext, String filePath) {
        StringBuilder textBuilder = new StringBuilder();

        buildTtreeText(ttext, textBuilder);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(textBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute la représentation textuelle du Qtree ttext dans le StringBuilder textBuilder.
     * Si le nœud est une feuille, ajoute simplement sa couleur, sinon ajoute une parenthèse
     * ouvrante, parcourt récursivement les fils et ajoute une parenthèse fermante.
     * O(n), où n est le nombre de nœuds dans le Ttree.
     * @param ttext le Qtree dont on veut extraire la représentation textuelle
     * @param textBuilder le StringBuilder dans lequel on écrit la représentation textuelle
     */
    private static void buildTtreeText(Ttree ttext, StringBuilder textBuilder) {
        if (ttext == null) return;

        if (ttext.isEmpty()) {
            // Si le nœud est une feuille, ajouter sa couleur
            textBuilder.append(Couleurs.colorToString(ttext.getPlan().getColor()));
        } else {
            // Ajouter une parenthèse ouvrante
            textBuilder.append("(");
            
            // Parcours récursif pour les fils
            buildTreeText(ttext.getNE(), textBuilder);
            buildTreeText(ttext.getNO(), textBuilder);
            buildTreeText(ttext.getSE(), textBuilder);
            
            // Ajouter une parenthèse fermante
            textBuilder.append(")");
        }
    }


    /**
     * Dessine le contour du Ttree en noir sur l'image draw et la sauvegarde 
     * dans le fichier file.
     * Complexité : O(n), où n est le nombre de nœuds dans le Ttree.
     * @param tfinal Ttree dont on veut dessiner le contour.
     * @param draw Image sur laquelle on veut dessiner le contour.
     * @param file Chemin du fichier dans lequel on veut sauvegarder l'image.
     */
    public static void drawOutlineTtree(Ttree tfinal, Image draw, String file){
        if(tfinal != null){

            int firstX = tfinal.getCentre().getCoordPoint().getX() - epaisseur/2;
            int lastX = firstX + epaisseur;
            int firstY = tfinal.getPlan().getDownRight().getY();
            int lastY = tfinal.getPlan().getUpright().getY();
            draw.setRectangle(firstX, lastX ,firstY , lastY , Color.BLACK);
            int secondX = tfinal.getCentre().getCoordPoint().getX();
            int finishX = tfinal.getPlan().getUpright().getX();
            int secondY = tfinal.getCentre().getCoordPoint().getY() - epaisseur/2;
            int finishY = secondY + epaisseur;
            draw.setRectangle(secondX, finishX, secondY, finishY, Color.BLACK);
            if(!(tfinal.getNE().getCentre() == null)){
                drawOutlineTtree(tfinal.getNE(), draw, file);
            }
            if(!(tfinal.getNO().getCentre() == null)){
                drawOutlineTtree(tfinal.getNO(), draw, file);
            }
            if(!(tfinal.getSE().getCentre() == null)){
                drawOutlineTtree(tfinal.getSE(), draw, file);
            }
            try{
                draw.save(file);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    /**
     * Fonction qui permet de trouver le parent d'un nœud Ttree cible dans l'arbre
     * O(n), où n est le nombre de nœuds dans le Ttree.
     * @param root le nœud racine de l'arbre
     * @param target le nœud cible
     * @return le parent du nœud cible ou null si il n'y a pas de parent
     */
    public static Ttree findParentTtree(Ttree root, Ttree target) {
        if (root == null || root.estFeuille()) {
            return null; // Si c'est une feuille ou un arbre vide, pas de parent
        }
    
        // Vérifie si le nœud courant est le parent du nœud cible
        if (root.getNE() == target || root.getNO() == target || 
            root.getSE() == target) {
            return root;
        }
    
        // Recherche récursive dans les fils
        Ttree found = findParentTtree(root.getNE(), target);
        if (found != null) return found;
    
        found = findParentTtree(root.getNO(), target);
        if (found != null) return found;
    
        return findParentTtree(root.getSE(), target);
    }
    

    /**
     * Modifie l'image rectDraw en mettant à jour le rectangle qui correspond au Qtree rect.
     * On utilise les coordonnées du plan de rect pour définir le rectangle.
     * Complexité : O(1)
     * @param rect le Qtree dont le rectangle doit être mis à jour
     * @param rectDraw l'image a modifie
     */
    public static void majRectangleTtree(Ttree rect, Image rectDraw){
        rectDraw.setRectangle(rect.getPlan().getDownLeft().getX(), rect.getPlan().getDownRight().getX(), rect.getPlan().getDownLeft().getY(), rect.getPlan().getUpLeft().getY(), rect.getPlan().getColor());
    }

    /**
     * Cette fonction prend une liste de paires de points et un Qtree.
     * Pour chaque paire, elle remplace le plan du Qtree qui correspond
     * au point de la paire par le plan de la paire.
     * Ensuite, elle compress le Qtree et le sauvegarde dans un fichier.
     * Complexité : O(p + n), où p est le nombre de paires et n est le nombre de nœuds dans le Ttree.
     * @param listPairR liste de paires de points
     * @param root le Qtree
     * @param finaldraw l'image
     * @param file le nom du fichier
     */
    public static void recolorTtree(List<Centre> listPairR, Ttree root, Image finaldraw, String file){
        System.err.println("\n");
        for (int i = 0; i < nbpairRecolor; i++){
            Ttree temp = root.searchLeafTtree(listPairR.get(i)); 
            temp.getPlan().setColor(listPairR.get(i).getC1());
            majRectangleTtree(temp, finaldraw);
            Ttree parentTemp = findParentTtree(root, temp);
            Ttree parenDeParent = findParentTtree(root, parentTemp);  
            drawOutlineTtree(parenDeParent, finaldraw, file);
        }
        compressQTtree(root, finaldraw, file);
        try{
            finaldraw.save(file);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Compression d'un arbre Ttree.
     * Cette fonction parcourt l'arbre Ttree en profondeur et regroupe les feuilles
     * qui ont la même couleur. Si les feuilles ont la même couleur, cela signifie 
     * qu'il y a une zone de la toile qui a la même couleur. On peut donc fusionner 
     * ces feuilles et les remplace par un seul rectangle.
     * Complexité : O(n), où n est le nombre de nœuds dans le Ttree.
     * @param troot le nœud racine de l'arbre Ttree
     * @param compressDraw l'image qui sera compressée
     * @param file le nom du fichier pour sauvegarder l'image compressée
     */
    public static void compressQTtree(Ttree troot, Image compressDraw, String file){
        if (troot == null) return;
        if((!troot.getNE().estFeuille())){
            compressQTtree(troot.getNE(), compressDraw, file);
        }else{
            if(troot.getNE().getPlan().getColor() == troot.getNO().getPlan().getColor() &&
               troot.getNE().getPlan().getColor() == troot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(troot.getPlan().getDownLeft().getX(), 
                                            troot.getPlan().getDownRight().getX(), 
                                            troot.getPlan().getDownLeft().getY(), 
                                            troot.getPlan().getUpLeft().getY(),
                                            troot.getNO().getPlan().getColor());
                    troot.getPlan().setColor(troot.getNE().getPlan().getColor());
                    troot.setNullSon();
                    troot.setCenter(null);
                    System.out.println("l'arbre a été compressé");
                    return;                         
            }
        }
        if((!troot.getNO().estFeuille())){
            compressQTtree(troot.getNO(), compressDraw, file);
        }else{
            if(troot.getNE().getPlan().getColor() == troot.getNO().getPlan().getColor() &&
               troot.getNE().getPlan().getColor() == troot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(troot.getPlan().getDownLeft().getX(), 
                                            troot.getPlan().getDownRight().getX(), 
                                            troot.getPlan().getDownLeft().getY(), 
                                            troot.getPlan().getUpLeft().getY(),
                                            troot.getNO().getPlan().getColor());
                    troot.getPlan().setColor(troot.getNE().getPlan().getColor());
                    troot.setNullSon();
                    troot.setCenter(null);
                    System.out.println("l'arbre a été compressé");
                    return;                         
            }
        }
        if((!troot.getSE().estFeuille())){
            compressQTtree(troot.getSE(), compressDraw, file);
        }else{
            if(troot.getNE().getPlan().getColor() == troot.getNO().getPlan().getColor() &&
               troot.getNE().getPlan().getColor() == troot.getSE().getPlan().getColor()){
                    compressDraw.setRectangle(troot.getPlan().getDownLeft().getX(), 
                                            troot.getPlan().getDownRight().getX(), 
                                            troot.getPlan().getDownLeft().getY(), 
                                            troot.getPlan().getUpLeft().getY(),
                                            troot.getNO().getPlan().getColor());
                    troot.getPlan().setColor(troot.getNE().getPlan().getColor());
                    troot.setNullSon();
                    troot.setCenter(null);
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

    public static void main(String[] args){

        String text1 = "RivardPiard/sortie/RivardPiardFichierEntree_B.txt";
        String text2 = "RivardPiard/sortie/RivardPiardFichierEntree_R.txt";
        String draw1 = "RivardPiard/sortie/RivardPiardFichierEntree_B.png";
        String draw2 = "RivardPiard/sortie/RivardPiardFichierEntree_R.png";

        
        if(args[0].equals("1")){
            lectureFichier();
            Qtree painting = new Qtree(centers.get(0), surface);
            painting.addQtree();
            painting.buildQtree(centers);
            Image masterpiece = new Image (taille, taille);
            toImage(painting, masterpiece, draw1);
            toText(painting, text1);
            drawOutline(painting, masterpiece, draw1);
            recolor(lpairRecolor, painting, masterpiece, draw2);
            drawOutline(painting, masterpiece, draw2);
            toText(painting, text2);
        }else if(args[0].equals("2")){

            lectureFichierTtree();
            Ttree painting = new Ttree(centers.get(0), surface);
            painting.addTtree();
            painting.buildTtree(centers);
            Image masterpiece = new Image (taille, taille);
            toImageTtree(painting, masterpiece, draw1);
            toTextTtree(painting, text1);
            drawOutlineTtree(painting, masterpiece, draw1);
            recolorTtree(centers, painting, masterpiece, draw2);
            drawOutlineTtree(painting, masterpiece, draw2);
            toTextTtree(painting, text2);
        }


    }
}


