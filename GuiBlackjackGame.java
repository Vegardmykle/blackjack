import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuiBlackjackGame {

    static boolean ferdig = false;

    public static void main(String[] args) throws InterruptedException {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }

        JFrame vindu = new JFrame("Blackjack");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Bruk en panel med BoxLayout i vertikal retning
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        vindu.add(panel);

        JLabel nyttspillabel = new JLabel();
        nyttspillabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel nyttspillPanel = new JPanel();
        nyttspillPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton jaKnapp = new JButton();
        class jaVelger implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                ferdig =false;
            }
        }
        nyttspillabel.add(jaKnapp);
        JButton neiKnapp = new JButton();
        class neiVelger implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                ferdig = true;
            }
        }
        nyttspillabel.add(neiKnapp);
        
        while(!ferdig){

        
            Spiller spiller = new Spiller();
            Spiller dealer = new Spiller();
            KortStokk deck = new KortStokk();
            
            spiller.trekkKort(deck);
            spiller.trekkKort(deck);
            dealer.trekkKort(deck);

            // Opprett en panel for dealerens kort
            JPanel dealerPanel = new JPanel();
            dealerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel dealerLabel = new JLabel("Dealer: ");
            dealerPanel.add(dealerLabel);
            JLabel dealerKort = new JLabel(dealer.toString());
            dealerPanel.add(dealerKort);
            panel.add(dealerPanel);

            // Legg til litt mellomrom
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Opprett en panel for spillerens kort
            JPanel spillerPanel = new JPanel();
            spillerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel spillerLabel = new JLabel("Spiller: ");
            spillerPanel.add(spillerLabel);
            JLabel spillerKort = new JLabel(spiller.toString());
            spillerPanel.add(spillerKort);
            panel.add(spillerPanel);

            // Legg til litt mellomrom
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Opprett en panel for knappene
            JPanel knappPanel = new JPanel();
            knappPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton trekkKnapp = new JButton("Trekk");
            class trekkVelger implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (spiller.bust()) return;

                    spiller.trekkKort(deck);
                    spillerKort.setText(spiller.toString());
                }
            }
            trekkKnapp.addActionListener(new trekkVelger());
            knappPanel.add(trekkKnapp);

            JButton standKnapp = new JButton("Stand");
            class standVelger implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dealer.trekkKort(deck);
                    while (dealer.totalpoeng() < 17) {
                        dealer.trekkKort(deck);
                    }
                    dealerKort.setText(dealer.toString());

                    JLabel vinner = new JLabel();
                    vinner.setAlignmentX(Component.CENTER_ALIGNMENT);
                    
                    if ((spiller.totalpoeng() > dealer.totalpoeng() || dealer.bust()) && !spiller.bust()) {
                        vinner.setText("Spiller vant!");
                    } else if (spiller.totalpoeng() == dealer.totalpoeng() && !spiller.bust()) {
                        vinner.setText("Split! Ble likt!");
                    } else {
                        vinner.setText("Dealer vant!");
                    }
                    
                    panel.add(Box.createRigidArea(new Dimension(0, 10)));
                    panel.add(vinner);
                    vindu.pack();
                    
                    // Deaktiver knappene etter at spillet er over
                    trekkKnapp.setEnabled(false);
                    standKnapp.setEnabled(false);
                    

                }
            }
            standKnapp.addActionListener(new standVelger());
            knappPanel.add(standKnapp);

            panel.add(knappPanel);

            
            Thread.sleep(5000);
            panel.add(nyttspillabel);
            panel.add(nyttspillPanel);
        

            vindu.pack();
            vindu.setVisible(true);
        }//while loop ferdig
    }
}