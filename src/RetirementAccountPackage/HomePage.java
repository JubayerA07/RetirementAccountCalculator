/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 1- Nikolas Tsagaris, Person 3
 * Date: 2026-05-15
 */


package RetirementAccountPackage;

import FourZeroOneKPackage.FourZeroOneKGUI;
import IRAPackage.IRACalculatorPage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

public class HomePage extends JPanel {

    public HomePage(Runnable onOpenInfo) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Retirement account calculator", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("<html><div style='text-align:center;width:420px'>"
                + "Explore how common retirement accounts compare. "
                + "Use the information page for 401(k) vs IRA basics and a Roth vs traditional view."
                + "</div></html>", JLabel.CENTER);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        JButton infoButton = new JButton("Open information page");
        infoButton.setAlignmentX(CENTER_ALIGNMENT);
        infoButton.addActionListener(e -> onOpenInfo.run());

        // Buttons to open the two calculator windows. Each calculator is its
        // own JFrame so we just construct it and it pops up.
        JButton iraButton = new JButton("Open IRA calculator");
        iraButton.setAlignmentX(CENTER_ALIGNMENT);
        iraButton.addActionListener(e -> new IRACalculatorPage());

        JButton fourZeroOneKButton = new JButton("Open 401(k) calculator");
        fourZeroOneKButton.setAlignmentX(CENTER_ALIGNMENT);
        fourZeroOneKButton.addActionListener(e -> new FourZeroOneKGUI());

        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0, 12)));
        center.add(subtitle);
        center.add(Box.createRigidArea(new Dimension(0, 24)));
        center.add(infoButton);
        center.add(Box.createRigidArea(new Dimension(0, 8)));
        center.add(iraButton);
        center.add(Box.createRigidArea(new Dimension(0, 8)));
        center.add(fourZeroOneKButton);

        add(center, BorderLayout.CENTER);
    }
}
