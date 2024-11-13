package src;
import java.util.ArrayList;
public class Qtree {

    //Attributs
    private Plan plan; // partie divisé entre les quatres fils
    private Centre center;
    private Qtree NO, NE, SE, SO;

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

    //Retourne vrai si le Qtree n'a pas de fils
    public boolean isEmpty (){
        return (NO == null && NE == null && SE == null && SO == null);
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


    //divise le plan en 4 fils et assigne chaque sous-plan a un fils
    public ArrayList<Qtree> fourDivision(){
        ArrayList<Qtree> LArbre = new ArrayList<Qtree>();
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
        return LArbre;
    }

    public Qtree searchQtree(Centre c) {
        //Si le Qtree n'a pas de fils c'est qu'on a trouvé le bon Qtree
        if (this.isEmpty()) {
            return this;
        }
        else {
            //Soit l'abscisse et l'ordonnée du point sont inférieurs à l'abscisse et l'ordonnée du centre du Qtree principale
            if (c.getCoordPoint().getX() < this.center.getCoordPoint().getX()
             && c.getCoordPoint().getY() < this.center.getCoordPoint().getY()) {
                //Alors on appelle la fonction searchQtree sur le fils SO
                return SO.searchQtree(c);
            }
            //Soit l'abscisse est supérieur à l'abscisse du centre du Qtree principale et l'ordonnée du point est inférieur à l'ordonnée du centre du Qtree principale
            if (c.getCoordPoint().getX() > this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() < this.center.getCoordPoint().getY()) {
                //Alors on appelle la fonction searchQtree sur le fils SE
                return SE.searchQtree(c);
            }
            //Soit l'abscisse est inférieur à l'abscisse du centre du Qtree principale et l'ordonnée du point est supérieur à l'ordonnée du centre du Qtree principale
            if (c.getCoordPoint().getX() < this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() > this.center.getCoordPoint().getY()) {
                return NO.searchQtree(c);
            }
            //Soit l'abscisse et l'ordonnée du point sont supérieurs à l'abscisse et l'ordonnée du centre du Qtree principale
            if (c.getCoordPoint().getX() > this.center.getCoordPoint().getX()
            && c.getCoordPoint().getY() > this.center.getCoordPoint().getY()) {
                //Alors on appelle la fonction searchQtree sur le fils SE
                return NE.searchQtree(c);
            }
            return null;
        }
    }

    public void addQtree(Centre c, Qtree nouveau) {
        nouveau.center = c;
        this.fourDivision();
    }

    
}
