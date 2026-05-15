/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 1- Nikolas Tsagaris
 * Date: 2026-05-14
 */

package RetirementAccountPackage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;


public class InfoPage extends JPanel {

    public InfoPage(Runnable onBackToHome) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel header = new JPanel(new BorderLayout());
        JButton back = new JButton("Back to home");
        back.addActionListener(e -> onBackToHome.run());
        JLabel title = new JLabel("Retirement account information", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        header.add(back, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        body.add(sectionTitle("401(k) vs IRA (overview)"));
        body.add(wrapScroll(textArea(fourOhOneKVsIraText(), 8)));
        body.add(Box.createRigidArea(new Dimension(0, 12)));

        body.add(sectionTitle("Roth vs traditional (side by side)"));
        body.add(buildRothTraditionalComparison());
        body.add(Box.createRigidArea(new Dimension(0, 8)));

        body.add(disclaimer());

        JScrollPane scroll = new JScrollPane(body);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 15f));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    private static JTextArea textArea(String content, int rows) {
        JTextArea area = new JTextArea(content, rows, 42);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setOpaque(false);
        area.setFocusable(false);
        return area;
    }

    private static JScrollPane wrapScroll(JTextArea area) {
        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setAlignmentX(LEFT_ALIGNMENT);
        pane.setPreferredSize(new Dimension(640, 160));
        pane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        return pane;
    }

    private static String fourOhOneKVsIraText() {
        return "401(k): An employer-sponsored retirement plan. You contribute through payroll, "
                + "and many employers offer a matching contribution on part of what you save. "
                + "Investment choices are usually a curated menu from the plan provider. "
                + "Higher annual contribution limits than an IRA (see IRS limits for the tax year).\n\n"
                + "IRA: An individual retirement arrangement you open at a brokerage or bank. "
                + "There is no employer match, but you choose investments directly. "
                + "Lower contribution limits than a 401(k), but useful if you change jobs, "
                + "are self-employed without a plan, or want to consolidate old retirement money.\n\n"
                + "Both can offer traditional and Roth tax treatments depending on the account type "
                + "and what your employer plan allows.";
    }

    private JPanel buildRothTraditionalComparison() {
        TraditionalPlan traditional = new TraditionalPlan();
        RothPlan roth = new RothPlan();

        JPanel grid = new JPanel(new GridLayout(1, 2, 12, 0));
        grid.add(planColumn(traditional));
        grid.add(planColumn(roth));
        grid.setAlignmentX(LEFT_ALIGNMENT);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));
        return grid;
    }

    private JPanel planColumn(RetirementPlan plan) {
        JPanel column = new JPanel(new BorderLayout(0, 6));
        column.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JLabel heading = new JLabel("<html><div style='text-align:center'><b>" + plan.getDisplayName()
                + "</b><br/><span style='font-weight:normal'>" + plan.getTagline() + "</span></div></html>",
                JLabel.CENTER);

        String body = "Contributions\n" + plan.getContributionTaxSummary()
                + "\n\nWithdrawals\n" + plan.getWithdrawalTaxSummary()
                + "\n\nRMDs\n" + plan.getRmdSummary()
                + "\n\nOften a fit when…\n" + plan.getBestForSummary();

        JTextArea area = textArea(body, 18);
        area.setOpaque(true);

        column.add(heading, BorderLayout.NORTH);
        column.add(new JScrollPane(area), BorderLayout.CENTER);
        return column;
    }

    private static JLabel disclaimer() {
        JLabel note = new JLabel("<html><body style='width:620px'>"
                + "<small>This screen is for general education only and is not tax, legal, or "
                + "investment advice. Limits, definitions, and rules change; verify details with "
                + "the IRS, your plan administrator, or a qualified professional.</small>"
                + "</body></html>");
        note.setAlignmentX(LEFT_ALIGNMENT);
        return note;
    }
}
