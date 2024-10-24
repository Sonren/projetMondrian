package src;
public class Qtree {

    //Attributs
    private Plan plan; // partie divisé entre les quatres fils
    private Centre o;
    private Qtree NO, NE, SE, SO;

    //Constructeur avec seulement un Centre
    public Qtree(Centre p, Plan plan){
        this.plan = plan;
        this.o = p;
        this.fourDivision();
    }
    
    //Méthodes
    //Getter

    public Plan getPlan() {
        return plan;
    }

    public Centre getCentre() {
        return o;
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
    public void fourDivision(){
        this.NO.plan = new Plan(this.plan.getUpLeft(), 
                            new Point (this.o.getCoordPoint().getX(),this.plan.getUpLeft().getY())
                            , this.o.getCoordPoint() 
                            , new Point(this.plan.getUpLeft().getX(), this.o.getCoordPoint().getY()),
                            this.o.getC1()
                        );   
        this.NE.plan = new Plan(new Point(this.o.getCoordPoint().getX(),this.plan.getUpLeft().getY()),
                            this.plan.getUpright(),
                            new Point(this.plan.getUpright().getX(), this.o.getCoordPoint().getY()),
                            this.o.getCoordPoint(),
                            this.o.getC2()
                        );
        this.SE.plan = new Plan(this.o.getCoordPoint(),
                            new Point(this.plan.getUpright().getX(),this.o.getCoordPoint().getY()),
                            this.plan.getDownRight(),
                            new Point(this.o.getCoordPoint().getX(),this.plan.getDownRight().getY()),
                            this.o.getC3()
                        );
        this.SO.plan = new Plan(new Point(this.plan.getDownLeft().getX(),this.o.getCoordPoint().getY()),
                            this.o.getCoordPoint(),
                            new Point(this.o.getCoordPoint().getX(),this.plan.getDownLeft().getY()),
                            this.plan.getDownLeft(),
                            this.o.getC4()
                        );
    }
}
