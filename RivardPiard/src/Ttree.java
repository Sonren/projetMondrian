import java.util.List;


public class Ttree extends Qtree {

    //Attributs
    private Plan plan; // partie divisé entre les quatres fils
    private Centre center;
    private Ttree NO, NE, SE;

    //Construits un Qtree vide
    public Ttree(){
        plan = null;
        center = null;
        NO = null;
        NE = null;
    }
    //Constructeur avec seulement un Centre
    public Ttree(Centre p, Plan plan){
        this.plan = plan;
        this.center = p;
        NO = null;
        NE = null;
        SE = null;
    }
    
    //Méthodes

    //Retourne vrai si le Qtree est vide (Soit c'est une feuille)
    public boolean isEmpty (){
        return center == null;
    }

    /**
     * Retourne vrai si le Ttree n'a pas de fils (aucune division).
     * Complexité : O(1)
     * @return vrai si le Ttree n'a pas de fils
     */
    public boolean noSon(){
        boolean son;
        if (this.NE == null || this.NO == null || this.SE == null) {
            return true;
        }
        
        son = this.NE.isEmpty() && this.NO.isEmpty() && this.SE.isEmpty();
        return son;
    }


    //Getter

    public Plan getPlan() {
        return plan;
    }

    public Centre getCentre() {
        return center;
    }

    public Ttree getNO() {
        return NO;
    }

    public Ttree getNE() {
        return NE;
    }

    public Ttree getSE() {
        return SE;
    }

    //Setter

    public void setNullNE(){
        this.NE = null;
    }

    public void setNullNO(){
        this.NO = null;
    }

    public void setNullSE(){
        this.SE = null;
    }

    public void setNullSon(){
        this.setNullNE();
        this.setNullNO();
        this.setNullSE();
    }

    public void setCenter(Centre c){
        this.center = c;
    }

    public boolean estFeuille(){
        return this.getCentre() == null;
    }

    public boolean estVide(){
        return this.getPlan() == null;
    }


    /**
     * Cherche la feuille du Ttree qui correspond au Centre c.
     * Si le nœud actuel est une feuille, on a trouvé le bon endroit.
     * Sinon, on détermine dans quel sous-quadrant se trouve le point
     * et on appelle récursivement la méthode sur le sous-quadrant approprié.
     * Si le sous-quadrant n'existe pas ou n'est pas initialisé, on retourne null.
     * Complexité : O(log n), où n est le nombre de nœuds dans le Ttree.
     * @param c le Centre à chercher
     * @return la feuille du Ttree qui correspond au Centre c, ou null si elle n'existe pas
     */
    public Ttree searchLeafTtree(Centre c) {
        if (estFeuille()) {
            return this; 
        }
           
        if (c.getCoordPoint().getX() > this.center.getCoordPoint().getX() &&
                   c.getCoordPoint().getY() < this.center.getCoordPoint().getY()) {
            // Sud-Est
            if (this.SE != null) {
                return this.SE.searchLeafTtree(c);
            }
        } else if (c.getCoordPoint().getX() < this.center.getCoordPoint().getX()) {
            // Nord-Ouest
            if (this.NO != null) {
                return this.NO.searchLeafTtree(c);
            }
        } else if (this.NE != null) {
            // Nord-Est
            return this.NE.searchLeafTtree(c);
        }
    
        return null;
    }
    
    

    
/**
 * Cherche la région du Ttree qui correspond au Centre c.
 * Si le nœud actuel est une feuille, on a trouvé le bon endroit.
 * Sinon, on détermine dans quel sous-quadrant se trouve le point
 * et on appelle récursivement la méthode sur le sous-quadrant approprié.
 * Si le sous-quadrant n'existe pas, il est initialisé.
 * Complexité : O(log n), où n est le nombre de nœuds dans le Ttree.
 * @param c le Centre à chercher
 * @return la région du Ttree qui correspond au Centre c
 */
    public Ttree searchTtree(Centre c) {
        // Si le nœud actuel est une feuille, on a trouvé le bon endroit
        if (this.isEmpty()) {
            this.center = c; // On affecte le centre ici
            return this;
        }
    
        // Détermine dans quel sous-quadrant se trouve le point `c`
        if (c.getCoordPoint().getX() > this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() < this.center.getCoordPoint().getY()) {
            // Sud-Est
            if (this.SE == null) this.SE = new Ttree(); // Initialise si nécessaire
            return this.SE.searchTtree(c);
        } else if (c.getCoordPoint().getX() < this.center.getCoordPoint().getX()) {
            // Nord-Ouest
            if (this.NO == null) this.NO = new Ttree(); // Initialise si nécessaire
            return this.NO.searchTtree(c);
        } else {
            // Nord-Est
            if (this.NE == null) this.NE = new Ttree(); // Initialise si nécessaire
            return this.NE.searchTtree(c);
        }
    }

    /**
     * Initialise les sous-quadrants du Ttree actuel.
     * La complexité est O(1) car on affecte simplement des valeurs.
     */
    public void addTtree(){
        //On initialise les fils
        this.NO = new Ttree();
        this.NE = new Ttree();
        this.SE = new Ttree();        
        

        this.NO.plan = new Plan(this.plan.getUpLeft(), 
                            new Point (this.center.getCoordPoint().getX(),this.plan.getUpLeft().getY())
                            , new Point(this.center.getCoordPoint().getX(),this.plan.getDownRight().getY()) 
                            , this.plan.getDownLeft()
                            , this.center.getC1()
                        );   
        this.NE.plan = new Plan(new Point(this.center.getCoordPoint().getX(),this.plan.getUpLeft().getY()),
                            this.plan.getUpright(),
                            new Point(this.plan.getUpright().getX(), this.center.getCoordPoint().getY()),
                            this.center.getCoordPoint(),
                            this.center.getC2()
                        );
        this.SE.plan = new Plan(this.center.getCoordPoint(),
                            new Point(this.plan.getUpright().getX(),this.center.getCoordPoint().getY()),
                            this.plan.getDownRight(),
                            new Point(this.center.getCoordPoint().getX(),this.plan.getDownRight().getY()),
                            this.center.getC3()
                        );
    }
    /**
     * Construit entièrement le Ttree à partir d'une liste de centres.
     * 
     * Pour chaque centre de la liste, si ce n'est pas le premier centre,
     * cette méthode cherche la région du Ttree correspondante et la divise
     * si nécessaire. Si le nœud trouvé n'est pas une feuille, on l'ajoute
     * comme sous-quadrant. Sinon, un message de log est affiché pour indiquer
     * une situation inattendue.
     * 
     * Complexité : O(n log n), où n est le nombre de centres.
     * 
     * @param centers la liste des centres à intégrer dans le Ttree
     */
    public void buildTtree(List<Centre> centers) {
        for (Centre c : centers) {
            if(!(c == centers.get(0))){  
                Ttree target = this.searchTtree(c);
                if (!target.isEmpty()) { // Vérifiez si le nœud est une feuille
                    target.addTtree(); // Divisez seulement si nécessaire
                } else {
                    System.out.println("ya un truc bizarre");
                }
            }
        }
    }

    

    


}
