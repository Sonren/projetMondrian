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
        //On va créer le plan de chaque fils en fonction du plan et du centre du Qtree parent
        //Fils NO
        NO.plan.setUpLeft(plan.getUpLeft());
        NO.plan.setUpplanight(r.getUpright().getX()/2 , plan.getUpLeft().getY());
        NO.plan.setDownRight(plan.getUpright().getX()/2 , plan.getUpLeft().getY()/2);
        NO.plan.setDownLeft(plan.getUpLeft().getX(), plan.getUpLeft().getY()/2);


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
    public void 4_division(){
        NO.plan = new Plan(this.plan.getUpLeft(), 
                            new Point (this.o.getX(),this.plan.getUpLeft().getY())
                            , this.o, 
                            , new Point(this.plan.getUpLeft().getX(), this.o.getY()),
                            this.o.getC1()
                        );   
        NE.plan = new Plan(new Point(this.o.getX(),this.plan.getUpLeft().getY()),
                            this.plan.getUpright(),
                            new Point(this.plan.getUpright(), this.o.getY()),
                            this.o,
                            this.o.getC2()
                        );
        SE.plan = new Plan(this.o,
                            new Point(this.plan.getUpright().getX(),this.o.getY()),
                            this.plan.getDownRight(),
                            new Point(this.o.getX(),this.plan.getDownRight().getY()),
                            this.o.getC3()
                        );
        SO.plan = new Plan(new Point(this.plan.getDownLeft().getX(),this.o.getY()),
                            this.o,
                            new Point(this.o.getX(),this.plan.getDownLeft().getY()),
                            this.plan.getDownLeft(),
                            this.o.getC4()
                        )
    }
}
