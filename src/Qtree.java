package src;
public class Qtree {

    //Attributs
    private Plan r; // partie divisé entre les quatres fils
    private Centre p;
    private Qtree NO, NE, SE, SO;

    //Constructeur avec seulement un Centre
    public Qtree(Centre p, Plan r){
        this.r = r;
        this.p = p;
        //On va créer le plan de chaque fils en fonction du plan et du centre du Qtree parent
        //Fils NO
        NO.r.setUpLeft(r.getUpLeft());
        NO.r.setUpright(r.getUpright().getX()/2 , r.getUpLeft().getY());
        NO.r.setDownRight(r.getUpright().getX()/2 , r.getUpLeft().getY()/2);
        NO.r.setDownLeft(r.getUpLeft().getX(), r.getUpLeft().getY()/2);


        //TODO
        //Fils NE
        NE = null;

        //Fils SE
        SE = null;

        //Fils SO
        SO = null;
    }
    
    //Méthodes
    //Getter

    public Plan getR() {
        return r;
    }

    public Centre getP() {
        return p;
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
}
