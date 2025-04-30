
import java.util.ArrayList;



public class KortStokk{

    private String[] sortene = {"hearts","clubs","diamonds","spades"};
    private String[] tallene = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
    private ArrayList<Kort> kortene = new ArrayList<>();
    ArrayList<Kort> blanda = new ArrayList<>();
    public KortStokk(){
        this.lagKortStokk();
        this.blandekort();
    }
    public void lagKortStokk(){
        for (String sort : this.sortene) {
            for (String tall : this.tallene) {
                Kort kort = new Kort(tall, sort);
                kortene.add(kort);
            }
        }

    }
    public void blandekort(){
        
        for(int i =kortene.size(); i>0;i--){
        int random = (int)(Math.random()*i);
            blanda.add(kortene.get(random));
            kortene.remove(random);
        }  
    }
    public Kort trekkKort(){
        return blanda.removeFirst();
    }
}