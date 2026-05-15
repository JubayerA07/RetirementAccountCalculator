/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 1- Nikolas Tsagaris
 * Date: 2026-05-14
 */

package RetirementAccountPackage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
import java.awt.Dimension;

/**
 * Primary window and simple card-based navigation between major areas of the app.
 */
public class MainApp extends JFrame {

    public static final String CARD_HOME = "HOME";
    public static final String CARD_INFO = "INFO";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    public MainApp() {
        super("Retirement accounts");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 520));
        setLocationByPlatform(true);

        HomePage home = new HomePage(() -> cardLayout.show(cards, CARD_INFO));
        InfoPage info = new InfoPage(() -> cardLayout.show(cards, CARD_HOME));

        cards.add(home, CARD_HOME);
        cards.add(info, CARD_INFO);
        add(cards);

        cardLayout.show(cards, CARD_HOME);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.pack();
            app.setVisible(true);
        });
    }
}
