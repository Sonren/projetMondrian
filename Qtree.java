public class Qtree {

    //Attributs
    private Centre p;
    private Qtree NO, NE, SE, SO;

    //Constructeur
    public Qtree(Centre p){
        this.p = p;
        NO = null;
        NE = null;
        SE = null;
        SO = null;
    }

    //Constructeur
    public Qtree(Centre p, Qtree NO, Qtree NE, Qtree SE, Qtree SO) {
        this.p = p;
        this.NO = NO;
        this.NE = NE;
        this.SE = SE;
        this.SO = SO;
    }
    
    //Méthodes
    //Getter

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

    /*public Qtree searchQtree(Centre p) {

    }*/
}
