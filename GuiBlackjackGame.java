import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.imageio.imageio;
import java.io.File;

public class GuiBlackjackGame {
    private static JPanel dealerPanel;
    private static JPanel spillerPanel;
    private static JPanel knappPanel;
    private static JLabel vinnerLabel;



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

        // Nytt spill-komponenter
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

    private static void startSpill(JPanel panel, JFrame vindu, JLabel nyttspillabel, JPanel nyttspillPanel, JButton jaKnapp, JButton neiKnapp) {
        Spiller spiller = new Spiller();
        Spiller dealer = new Spiller();
        KortStokk deck = new KortStokk();
        
        spiller.trekkKort(deck);
        spiller.trekkKort(deck);
        dealer.trekkKort(deck);

        // Opprett en panel for dealerens kort

        dealerPanel = new JPanel();
        dealerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel dealerLabel = new JLabel("Dealer: ");
        dealerPanel.add(dealerLabel);



        JLabel dealerKort = new JLabel(dealer.toString());
        dealerPanel.add(dealerKort);
        panel.add(dealerPanel);

        // Legg til litt mellomrom
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Opprett en panel for spillerens kort
        spillerPanel = new JPanel();
        spillerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel spillerKort = new JLabel("Spiller: " + spiller.toString());
        spillerPanel.add(spillerKort);
        panel.add(spillerPanel);

        // Legg til litt mellomrom
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Opprett en panel for knappene
        knappPanel = new JPanel();
        knappPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton trekkKnapp = new JButton("Trekk");
        class trekkVelger implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!spiller.bust()){
                    spiller.trekkKort(deck);
                    spillerKort.setText("Spiller: " + spiller.toString());
                }

                if (spiller.bust()) {
                    avsluttSpill(panel, vindu, nyttspillabel, nyttspillPanel, "Dealer vant! Spiller bust.");
                    spillerKort.setText("Spiller: " + spiller.toString());
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
        class jaVelger implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fjern alle spillkomponenter
                panel.remove(dealerPanel);
                panel.remove(spillerPanel);
                panel.remove(knappPanel);
                
                // Fjern resultatmelding hvis den finnes
                if (vinnerLabel != null && vinnerLabel.getParent() != null) {
                    panel.remove(vinnerLabel);
                }
                
                // Fjern mellomrom hvis det finnes
                if (panel.getComponentCount() > 2) { // 2 er nyttspill-komponentene
                    Component comp = panel.getComponent(panel.getComponentCount()-1);
                    if (comp instanceof Box.Filler) {
                        panel.remove(comp);
                    }
                }
                
                nyttspillabel.setVisible(false);
                nyttspillPanel.setVisible(false);
                
                startSpill(panel, vindu, nyttspillabel, nyttspillPanel, jaKnapp, neiKnapp);
                vindu.pack();
            }
        }
        jaKnapp.addActionListener(new jaVelger());
        
        neiKnapp.addActionListener(e -> System.exit(0));
        
        vindu.pack();
    }

    private static void avsluttSpill(JPanel panel, JFrame vindu, JLabel nyttspillabel, JPanel nyttspillPanel, String melding) {
        vinnerLabel = new JLabel(melding);
        vinnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(vinnerLabel);
        
        nyttspillabel.setVisible(true);
        nyttspillPanel.setVisible(true);
        
        vindu.pack();
    }
}