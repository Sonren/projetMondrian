package src;
import java.util.List;
import java.awt.Color;
import src.Main.Couleurs;
public class Qtree {

    //Attributs
    private Plan plan; // partie divisé entre les quatres fils
    private Centre center;
    private Qtree NO, NE, SE, SO;

    //Construits un Qtree vide
    public Qtree(){
        plan = null;
        center = null;
        NO = null;
        NE = null;
        SE = null;
        SO = null;
    }
    //Constructeur avec seulement un Centre
    public Qtree(Centre p, Plan plan){
        this.plan = plan;
        this.center = p;
        NO = null;
        NE = null;
        SE = null;
        SO = null;
    }
    
    //Méthodes

    //Retourne vrai si le Qtree est vide (Soit c'est une feuille)
    public boolean isEmpty (){
        return center == null;
    }

    public boolean noSon(){
        return this.NE.isEmpty() && this.NO.isEmpty() && this.SE.isEmpty() && this.SO.isEmpty();
    }


    //Getter

    public Plan getPlan() {
        return plan;
    }

    public Centre getCentre() {
        return center;
    }

    public Qtree getNO() {
        return NO;
    }

    public Qtree getNE() {
        return NE;
    }

    public Qtree getSE() {
        return SE;
    }

    public Qtree getSO() {
        return SO;
    }

    public void setNullNE(){
        this.NE = null;
    }

    public void setNullNO(){
        this.NO = null;
    }

    public void setNullSE(){
        this.SE = null;
    }

    public void setNullSO(){
        this.SO = null;
    }

    public void setNullSon(){
        this.setNullNE();
        this.setNullNO();
        this.setNullSE();
        this.setNullSO();
    }



    
   //Etant donné un Centre C, retourne la région divisible (Qtree) à laquelle appartient C
    public Qtree searchQtree(Centre c) {
        // Si le nœud actuel est une feuille, on a trouvé le bon endroit
        if (this.isEmpty()) {
            this.center = c; // On affecte le centre ici
            return this;
        }
    
        // Détermine dans quel sous-quadrant se trouve le point `c`
        if (c.getCoordPoint().getX() < this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() < this.center.getCoordPoint().getY()) {
            // Sud-Ouest
            if (this.SO == null) this.SO = new Qtree(); // Initialise si nécessaire
            return this.SO.searchQtree(c);
        } else if (c.getCoordPoint().getX() > this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() < this.center.getCoordPoint().getY()) {
            // Sud-Est
            if (this.SE == null) this.SE = new Qtree(); // Initialise si nécessaire
            return this.SE.searchQtree(c);
        } else if (c.getCoordPoint().getX() < this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() > this.center.getCoordPoint().getY()) {
            // Nord-Ouest
            if (this.NO == null) this.NO = new Qtree(); // Initialise si nécessaire
            return this.NO.searchQtree(c);
        } else {
            // Nord-Est
            if (this.NE == null) this.NE = new Qtree(); // Initialise si nécessaire
            return this.NE.searchQtree(c);
        }
    }

    //divise le plan en 4 fils et assigne chaque sous-plan a un fils
    public void addQtree(){
        //On initialise les fils
        this.NO = new Qtree();
        this.NE = new Qtree();
        this.SE = new Qtree();
        this.SO = new Qtree();
        
        

        this.NO.plan = new Plan(this.plan.getUpLeft(), 
                            new Point (this.center.getCoordPoint().getX(),this.plan.getUpLeft().getY())
                            , this.center.getCoordPoint() 
                            , new Point(this.plan.getUpLeft().getX(), this.center.getCoordPoint().getY()),
                            this.center.getC1()
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
        this.SO.plan = new Plan(new Point(this.plan.getDownLeft().getX(),this.center.getCoordPoint().getY()),
                            this.center.getCoordPoint(),
                            new Point(this.center.getCoordPoint().getX(),this.plan.getDownLeft().getY()),
                            this.plan.getDownLeft(),
                            this.center.getC4()
                        );
    }
    //Etant donnée une liste de centre, construit le Qtree en entier
    public void buildQtree(List<Centre> centers) {
        for (Centre c : centers) {
            if(!(c == centers.getFirst())){  //pour eviter la redondance du premier arbre masi voir pour changer cela autrement
                Qtree target = this.searchQtree(c);
                if (!target.isEmpty()) { // Vérifiez si le nœud est une feuille
                    target.addQtree(); // Divisez seulement si nécessaire
                } else {
                    System.out.println("ya un truc bizarre");
                }
            }
        }
    }

    public void toText(Map<Color, String> couleurMap) {
        if(this.isEmpty()) {
            String colorName = couleurMap.get(this.getPlan().getColor());
            System.out.print(colorName);
        } else {
            System.out.print("(");
            this.NO.toText(couleurMap);
            this.NE.toText(couleurMap);
            this.SE.toText(couleurMap);
            this.SO.toText(couleurMap);
            System.out.print(")");
        }
    }


    public void printTree(int level) {
        // Indentation en fonction du niveau
        String indent = "  ".repeat(level);
    
        // Affiche la couleur du plan courant
        if (this.plan != null) {
            System.out.println(indent + this.plan.getColor());
        } else {
            System.out.println(indent + "vide");
        }
    
        // Appelle récursivement pour les sous-arbres
        if (this.NO != null) {
            System.out.println(indent + "NO :");
            this.NO.printTree(level + 1);
        }
        if (this.NE != null) {
            System.out.println(indent + "NE :");
            this.NE.printTree(level + 1);
        }
        if (this.SE != null) {
            System.out.println(indent + "SE :");
            this.SE.printTree(level + 1);
        }
        if (this.SO != null) {
            System.out.println(indent + "SO :");
            this.SO.printTree(level + 1);
        }
    }
    
    
    

    


}
