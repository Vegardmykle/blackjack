
import java.util.ArrayList;

public class Spiller{
    private ArrayList<Kort> kort = new ArrayList<>();

    public Kort trekkKort(KortStokk kortStokk){
        Kort korte = kortStokk.trekkKort();
        kort.add(korte);


        return korte;
    }
    public int totalpoeng (){
        int sum = 0;
        int a = 0;
        for (Kort kortet : kort) {
            if(kortet.verdi()==11){
                a++;
            }
            sum += kortet.verdi();
        }
        if(sum>21 && a>0){
            sum-=10;
            a--;
        }
        return sum;
    }
    public boolean  bust(){
        if(this.totalpoeng()>21){
            System.out.println("Bust!");
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String streng= "";
        for(Kort kortet : kort){
            streng+= kortet.toString() + " ";
        }
        return streng + "\n poengsum: " + String.valueOf(totalpoeng());
    }
}