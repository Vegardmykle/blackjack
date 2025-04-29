
import java.util.Scanner;


public class BlackjackGame{
    
    public static void main(String[] args){
    KortStokk deck = new KortStokk();
        Scanner sc = new Scanner(System.in);
        String spille = "ja";
    while(!spille.equals("nei")){
        if (spille.equals("ja")){
        Spiller spiller = new Spiller();
        Spiller dealer = new Spiller();

        
    
    spiller.trekkKort(deck);
    spiller.trekkKort(deck);
    dealer.trekkKort(deck);
    
        
        System.out.println(spiller.toString()+" spiller");
        System.out.println(dealer.toString()+ " dealer");
        String hit = "";

        while(!hit.equals("nei")){
            System.out.println("vil du hitte? (ja/nei)");
            hit = sc.nextLine();
            if(hit.equals("ja")){
                spiller.trekkKort(deck);
                System.out.println(spiller.toString()+" spiller");
            }
            if(spiller.bust() == true){
                hit = "nei";
            }
            
            
        }
        //poeng spiller bus?
        spiller.bust();

        //dealer trekker til 17
        dealer.trekkKort(deck);
        while(dealer.totalpoeng()<17){
            dealer.trekkKort(deck);
        }

        //sjekker om dealer har busted
        System.out.println(dealer.toString()+ " dealer");

        
        if((spiller.totalpoeng()> dealer.totalpoeng() || dealer.bust()==true)&& spiller.bust()!=true){
            System.out.println("spiller vant");
        }
        else if (spiller.totalpoeng() == dealer.totalpoeng()&&spiller.bust()!=true){
            System.out.println("split! ble likt!");
        }
        else{
            System.out.println("dealer vant!");
        }
        System.out.println("vil du spille igjen?(ja/nei)");
        spille = sc.nextLine();
    }


    }
        sc.close();
        
        
    }
}