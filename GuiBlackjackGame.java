import java.awt.event.*;
import javax.swing.*;

public class GuiBlackjackGame{
    static String valg = "vet ikke";
    static boolean ferdig = false;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {System.exit(1);}

        JFrame vindu = new JFrame("Blackjack");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);


        Spiller spiller = new Spiller();
        Spiller dealer = new Spiller();
        KortStokk deck = new KortStokk();
        

        spiller.trekkKort(deck);
        spiller.trekkKort(deck);
        dealer.trekkKort(deck);

        JLabel spillerKort = new JLabel(spiller.toString()+" spiller\n");
        panel.add(spillerKort);

        JLabel dealerKort = new JLabel(dealer.toString()+ " dealer");
        panel.add(dealerKort);
        JButton trekkKnapp = new JButton("trekk");
        class trekkVelger implements ActionListener{
            @Override
            public void actionPerformed (ActionEvent e){
                if(spiller.bust() == true) return; 

                spiller.trekkKort(deck);
                spillerKort.setText(spiller.toString()+" spiller");


            }
        }
        trekkKnapp.addActionListener(new trekkVelger());
        panel.add(trekkKnapp);
        
        JButton standKnapp = new JButton("stand");
        class standVelger implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                dealer.trekkKort(deck);
                while(dealer.totalpoeng()<17){
                    dealer.trekkKort(deck);
                }
                JLabel vinner = new JLabel("");
                panel.add(vinner);
                dealerKort.setText(dealer.toString()+ " dealer");
                if((spiller.totalpoeng()> dealer.totalpoeng() || dealer.bust()==true)&& spiller.bust()!=true){
            
                    vinner.setText("spiller vant");
        }
        else if (spiller.totalpoeng() == dealer.totalpoeng()&&spiller.bust()!=true){
            vinner.setText("split! ble likt!");
        }
        else{
            vinner.setText("dealer vant!");
        }
            }
        }
        standKnapp.addActionListener(new standVelger());
        panel.add(standKnapp);

        vindu.pack();
        vindu.setVisible(true);
    }

}