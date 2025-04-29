
import java.util.HashMap;

public class Kort{
    private HashMap<String,Integer> rankene = new  HashMap<>();
    private String denneRank;
    private String denneFarge;


    public Kort(String rank, String farge){
        this.denneFarge = farge;
        this.denneRank = rank;

        for (int i = 2;i<=10;i++){
            rankene.put(String.valueOf(i),i);
        }
        rankene.put("J", 10);
        rankene.put("Q", 10);
        rankene.put("K", 10);
        rankene.put("A", 11);
    }
    public int verdi (){
        return rankene.get(denneRank);
    }
    @Override
    public String toString(){
        return denneRank +" " +denneFarge;
    }
}