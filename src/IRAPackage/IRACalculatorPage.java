/*
 * Retirement Account Calculator
 * CSCI 185
 * Date: 2026-05-15
 */
package IRAPackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// IRA calculator window
public class IRACalculatorPage extends JFrame {

    // input fields with defaults
    private final JTextField startingBalanceField = new JTextField("1000", 8);
    private final JTextField currentAgeField      = new JTextField("25", 8);
    private final JTextField retirementAgeField   = new JTextField("65", 8);
    private final JTextField annualContribField   = new JTextField("6000", 8);
    private final JTextField rateOfReturnField    = new JTextField("7", 8);
    private final JTextField marginalTaxField     = new JTextField("22", 8);

    private final JCheckBox  maxContributionBox   = new JCheckBox("Maximize contribution");

    // result labels
    private final JLabel rothSummaryLabel = new JLabel(" ");
    private final JLabel tradSummaryLabel = new JLabel(" ");

    // the two charts
    private final ChartPanel rothChartPanel = new ChartPanel(emptyChart("Roth IRA"));
    private final ChartPanel tradChartPanel = new ChartPanel(emptyChart("Traditional IRA"));

    public IRACalculatorPage() {
        super("IRA Calculator");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.add(buildInputPanel(), BorderLayout.WEST);
        mainPanel.add(buildChartsPanel(), BorderLayout.CENTER);
        setContentPane(mainPanel);

        setMinimumSize(new Dimension(900, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // left side
    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Inputs"));

        panel.add(new JLabel("Starting balance ($):"));        panel.add(startingBalanceField);
        panel.add(new JLabel("Current age:"));                 panel.add(currentAgeField);
        panel.add(new JLabel("Retirement age:"));              panel.add(retirementAgeField);
        panel.add(new JLabel("Annual contribution ($):"));     panel.add(annualContribField);
        panel.add(new JLabel("Expected rate of return (%):")); panel.add(rateOfReturnField);
        panel.add(new JLabel("Marginal tax rate (%):"));       panel.add(marginalTaxField);
        panel.add(new JLabel(""));                             panel.add(maxContributionBox);

        JButton calculateBtn = new JButton("Calculate");
        JButton saveBtn      = new JButton("Save Image");
        calculateBtn.addActionListener(e -> onCalculate());
        saveBtn.addActionListener(e -> onSaveImage());
        panel.add(calculateBtn);
        panel.add(saveBtn);

        return panel;
    }

    // right side
    private JPanel buildChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 8, 8));

        JPanel roth = new JPanel(new BorderLayout());
        roth.add(rothSummaryLabel, BorderLayout.NORTH);
        roth.add(rothChartPanel, BorderLayout.CENTER);

        JPanel trad = new JPanel(new BorderLayout());
        trad.add(tradSummaryLabel, BorderLayout.NORTH);
        trad.add(tradChartPanel, BorderLayout.CENTER);

        panel.add(roth);
        panel.add(trad);
        return panel;
    }

    // calculate button
    private void onCalculate() {
        try {
            double startingBalance = parseNonNegativeDouble(startingBalanceField.getText(), "Starting balance");
            int currentAge = parseAge(currentAgeField.getText(), "Current age");
            int retirementAge = parseAge(retirementAgeField.getText(), "Retirement age");
            if (retirementAge <= currentAge) {
                throw new IllegalArgumentException("Retirement age must be greater than current age.");
            }
            double rateOfReturn = parsePercent(rateOfReturnField.getText(), "Expected rate of return");
            double marginalTax  = parsePercent(marginalTaxField.getText(), "Marginal tax rate");

            // max contribution override
            double limit = IRAAccount.limitForAge(currentAge);
            double annualContrib;
            if (maxContributionBox.isSelected()) {
                annualContrib = limit;
            } else {
                annualContrib = parseNonNegativeDouble(annualContribField.getText(), "Annual contribution");
                if (annualContrib > limit) {
                    throw new IllegalArgumentException(
                        "Annual contribution cannot exceed the IRS limit of $" + (int) limit
                            + " for age " + currentAge + ".");
                }
            }

            // run both IRAs
            RothIRA roth = new RothIRA(startingBalance, currentAge, retirementAge,
                                       annualContrib, rateOfReturn, marginalTax);
            TraditionalIRA trad = new TraditionalIRA(startingBalance, currentAge, retirementAge,
                                                    annualContrib, rateOfReturn, marginalTax);

            IRAAccount.ProjectionResult rothResult = roth.project();
            IRAAccount.ProjectionResult tradResult = trad.project();

            rothChartPanel.setChart(buildChart(roth.getPlanName(), rothResult));
            tradChartPanel.setChart(buildChart(trad.getPlanName(), tradResult));

            rothSummaryLabel.setText(summary(roth.getPlanName(), rothResult));
            tradSummaryLabel.setText(summary(trad.getPlanName(), tradResult));

        } catch (IllegalArgumentException ex) {
            // show error popup
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                          "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // blank chart
    private static JFreeChart emptyChart(String title) {
        return ChartFactory.createLineChart(title, "Age", "Balance ($)",
                new DefaultCategoryDataset(), PlotOrientation.VERTICAL,
                true, true, false);
    }

    // chart with data
    private JFreeChart buildChart(String planName, IRAAccount.ProjectionResult r) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < r.ages.length; i++) {
            String age = String.valueOf(r.ages[i]);
            dataset.addValue(r.balances[i], "Retirement savings", age);
            dataset.addValue(r.contributions[i], "Total contributions", age);
        }
        return ChartFactory.createLineChart(planName, "Age", "Balance ($)",
                dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    // input checks
    private static double parseNonNegativeDouble(String text, String fieldName) {
        try {
            double v = Double.parseDouble(text.trim());
            if (v < 0) throw new IllegalArgumentException(fieldName + " cannot be negative.");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a number.");
        }
    }

    private static int parseAge(String text, String fieldName) {
        try {
            int v = Integer.parseInt(text.trim());
            if (v < 0 || v > 120) throw new IllegalArgumentException(fieldName + " must be between 0 and 120.");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a whole number.");
        }
    }

    // turns % into decimal
    private static double parsePercent(String text, String fieldName) {
        double v = parseNonNegativeDouble(text, fieldName);
        if (v > 100) throw new IllegalArgumentException(fieldName + " cannot exceed 100%.");
        return v / 100.0;
    }

    private String summary(String planName, IRAAccount.ProjectionResult r) {
        return String.format("%s   End balance: $%,.2f   Total contributions: $%,.2f",
                             planName, r.endingBalance(), r.totalContributions());
    }

    // save button - writes PNG
    private void onSaveImage() {
        File out = new File("ira_chart.png");

        int width = 700;
        int chartH = 300;
        int textH = 200;
        int height = textH + chartH * 2;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g.drawString("IRA Projection Report", 10, 24);

            // input values listed at top
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            int y = 48;
            for (String line : inputLines()) {
                g.drawString(line, 10, y);
                y += 16;
            }

            // draw both charts under the text
            rothChartPanel.getChart().draw(g, new Rectangle(0, textH, width, chartH));
            tradChartPanel.getChart().draw(g, new Rectangle(0, textH + chartH, width, chartH));
        } finally {
            g.dispose();
        }

        try {
            ImageIO.write(img, "png", out);
            JOptionPane.showMessageDialog(this, "Saved to:\n" + out.getAbsolutePath(),
                                          "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not save image:\n" + e.getMessage(),
                                          "Save failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // text shown at top of saved image
    private String[] inputLines() {
        return new String[] {
            "Starting balance: $"     + startingBalanceField.getText(),
            "Current age: "           + currentAgeField.getText(),
            "Retirement age: "        + retirementAgeField.getText(),
            "Annual contribution: $"  + annualContribField.getText()
                + (maxContributionBox.isSelected() ? "  (maxed)" : ""),
            "Rate of return: "        + rateOfReturnField.getText() + "%",
            "Marginal tax rate: "     + marginalTaxField.getText() + "%",
            "",
            rothSummaryLabel.getText(),
            tradSummaryLabel.getText(),
        };
    }

    // for testing on its own
    public static void main(String[] args) {
        SwingUtilities.invokeLater(IRACalculatorPage::new);
    }
}
