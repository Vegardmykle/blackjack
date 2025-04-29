import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuiBlackjackGame {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }

        JFrame vindu = new JFrame("Blackjack");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        vindu.add(panel);

        // Flyttet nytt spill-komponenter utenfor while-løkken
        JLabel nyttspillabel = new JLabel("Vil du spille igjen?");
        nyttspillabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nyttspillabel.setVisible(false);
        
        JPanel nyttspillPanel = new JPanel();
        nyttspillPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nyttspillPanel.setVisible(false);

        JButton jaKnapp = new JButton("Ja");
        JButton neiKnapp = new JButton("Nei");
        
        panel.add(nyttspillabel);
        panel.add(nyttspillPanel);
        nyttspillPanel.add(jaKnapp);
        nyttspillPanel.add(neiKnapp);

        // Start første spill
        startSpill(panel, vindu, nyttspillabel, nyttspillPanel, jaKnapp, neiKnapp);
        
        vindu.pack();
        vindu.setVisible(true);
    }

    private static void startSpill(JPanel panel, JFrame vindu, JLabel nyttspillabel, 
                                 JPanel nyttspillPanel, JButton jaKnapp, JButton neiKnapp) {
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
                
                if (spiller.bust()) {
                    avsluttSpill(panel, vindu, nyttspillabel, nyttspillPanel, "Dealer vant! Spiller bust.");
                }
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

                String resultat;
                if ((spiller.totalpoeng() > dealer.totalpoeng() || dealer.bust()) && !spiller.bust()) {
                    resultat = "Spiller vant!";
                } else if (spiller.totalpoeng() == dealer.totalpoeng() && !spiller.bust()) {
                    resultat = "Split! Ble likt!";
                } else {
                    resultat = "Dealer vant!";
                }
                
                avsluttSpill(panel, vindu, nyttspillabel, nyttspillPanel, resultat);
            }
        }
        standKnapp.addActionListener(new standVelger());
        knappPanel.add(standKnapp);

        panel.add(knappPanel);
        
        // Knappelyttere for nytt spill
        jaKnapp.addActionListener(e -> {
            // Fjern spillkomponentene
            panel.remove(dealerPanel);
            panel.remove(spillerPanel);
            panel.remove(knappPanel);
            panel.remove(panel.getComponent(panel.getComponentCount()-1)); // Fjern resultatmelding
            
            nyttspillabel.setVisible(false);
            nyttspillPanel.setVisible(false);
            
            startSpill(panel, vindu, nyttspillabel, nyttspillPanel, jaKnapp, neiKnapp);
            vindu.pack();
        });
        
        neiKnapp.addActionListener(e -> System.exit(0));
        
        vindu.pack();
    }

    private static void avsluttSpill(JPanel panel, JFrame vindu, JLabel nyttspillabel, 
                                   JPanel nyttspillPanel, String melding) {
        JLabel vinner = new JLabel(melding);
        vinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(vinner);
        
        nyttspillabel.setVisible(true);
        nyttspillPanel.setVisible(true);
        
        vindu.pack();
    }
}