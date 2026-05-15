/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 3
 * Date: 2026-05-15
 */
package IRAPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// This is the main GUI page for the IRA calculator.
// I extended JFrame so it's its own window. My teammates can just call
// "new IRACalculatorPage()" from their home page button and this opens up.
public class IRACalculatorPage extends JFrame {

    // Text fields for all the user inputs. Default values are filled in so
    // the user can hit Calculate right away and see something work.
    private final JTextField startingBalanceField = new JTextField("1000", 8);
    private final JTextField currentAgeField      = new JTextField("25", 8);
    private final JTextField retirementAgeField   = new JTextField("65", 8);
    private final JTextField annualContribField   = new JTextField("6000", 8);
    private final JTextField rateOfReturnField    = new JTextField("7", 8);
    private final JTextField marginalTaxField     = new JTextField("22", 8);

    // Checkbox: if ticked, we ignore the annual contribution field and just
    // use the IRS max for the user's age.
    private final JCheckBox  maxContributionBox   = new JCheckBox("Maximize contribution");

    // Labels above each chart that show end balance + total contributions.
    private final JLabel rothSummaryLabel = new JLabel(" ");
    private final JLabel tradSummaryLabel = new JLabel(" ");

    // The two chart panels (one for each IRA type).
    private final ChartPanel rothChart = new ChartPanel("Roth IRA");
    private final ChartPanel tradChart = new ChartPanel("Traditional IRA");

    // Constructor builds the whole window and shows it.
    public IRACalculatorPage() {
        super("IRA Calculator");

        // Just close this window, don't kill the whole app.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inputs on the left, charts on the right.
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.add(buildInputPanel(), BorderLayout.WEST);
        mainPanel.add(buildChartsPanel(), BorderLayout.CENTER);
        setContentPane(mainPanel);

        setMinimumSize(new Dimension(900, 600));
        pack();
        setLocationRelativeTo(null); // center on screen
        setVisible(true);
    }

    // Builds the left side: labels + text fields + the two buttons.
    // GridLayout makes everything line up in two columns automatically.
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

        // Hook up the buttons to their methods using lambdas.
        JButton calculateBtn = new JButton("Calculate");
        JButton saveBtn      = new JButton("Save Image");
        calculateBtn.addActionListener(e -> onCalculate());
        saveBtn.addActionListener(e -> onSaveImage());
        panel.add(calculateBtn);
        panel.add(saveBtn);

        return panel;
    }

    // Builds the right side: two stacked panels, one per chart.
    private JPanel buildChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 8, 8));

        JPanel roth = new JPanel(new BorderLayout());
        roth.add(rothSummaryLabel, BorderLayout.NORTH);
        roth.add(rothChart, BorderLayout.CENTER);

        JPanel trad = new JPanel(new BorderLayout());
        trad.add(tradSummaryLabel, BorderLayout.NORTH);
        trad.add(tradChart, BorderLayout.CENTER);

        panel.add(roth);
        panel.add(trad);
        return panel;
    }

    // Runs when the user clicks Calculate.
    // First reads + validates everything, then builds both IRA objects,
    // projects them, and pushes the results into the chart panels.
    private void onCalculate() {
        try {
            // Read each field. The parse helpers throw if anything's bad.
            double startingBalance = parseNonNegativeDouble(startingBalanceField.getText(), "Starting balance");
            int currentAge = parseAge(currentAgeField.getText(), "Current age");
            int retirementAge = parseAge(retirementAgeField.getText(), "Retirement age");
            if (retirementAge <= currentAge) {
                throw new IllegalArgumentException("Retirement age must be greater than current age.");
            }
            double rateOfReturn = parsePercent(rateOfReturnField.getText(), "Expected rate of return");
            double marginalTax  = parsePercent(marginalTaxField.getText(), "Marginal tax rate");

            // Figure out the contribution amount. If "maximize" is checked,
            // just use the IRS limit for this user's age. Otherwise read the
            // field and make sure it isn't over the limit.
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

            // Polymorphism in action -- same inputs, two different subclasses,
            // each returns a different projection because of applyTaxTreatment.
            RothIRA roth = new RothIRA(startingBalance, currentAge, retirementAge,
                                       annualContrib, rateOfReturn, marginalTax);
            TraditionalIRA trad = new TraditionalIRA(startingBalance, currentAge, retirementAge,
                                                    annualContrib, rateOfReturn, marginalTax);

            IRAAccount.ProjectionResult rothResult = roth.project();
            IRAAccount.ProjectionResult tradResult = trad.project();

            // Push the data into the chart panels (they repaint themselves).
            rothChart.setData(rothResult);
            tradChart.setData(tradResult);

            // Update the summary labels above each chart.
            rothSummaryLabel.setText(summary(roth.getPlanName(), rothResult));
            tradSummaryLabel.setText(summary(trad.getPlanName(), tradResult));

        } catch (IllegalArgumentException ex) {
            // If anything was wrong, show the user a popup with the reason.
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                                          "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Input parsing helpers ---
    // These throw IllegalArgumentException with a friendly message if the
    // user typed something we can't use. onCalculate() catches those and
    // shows the message in a JOptionPane.

    private static double parseNonNegativeDouble(String text, String fieldName) {
        try {
            double v = Double.parseDouble(text.trim());
            if (v < 0) throw new IllegalArgumentException(fieldName + " cannot be negative.");
            return v;
        } catch (NumberFormatException e) {
            // Re-throw as IllegalArgumentException so onCalculate has one type to catch.
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

    // Reads a percent and converts to decimal (so 7 becomes 0.07).
    private static double parsePercent(String text, String fieldName) {
        double v = parseNonNegativeDouble(text, fieldName);
        if (v > 100) throw new IllegalArgumentException(fieldName + " cannot exceed 100%.");
        return v / 100.0;
    }

    // Builds the one-line summary that goes above each chart.
    private String summary(String planName, IRAAccount.ProjectionResult r) {
        return String.format("%s   End balance: $%,.2f   Total contributions: $%,.2f",
                             planName, r.endingBalance(), r.totalContributions());
    }

    // --- File I/O part ---
    // Save button: takes the current charts + input values and writes a PNG.
    private void onSaveImage() {
        // Make sure they hit Calculate first -- otherwise there's no data.
        if (rothChart.getData() == null || tradChart.getData() == null) {
            JOptionPane.showMessageDialog(this, "Calculate first, then save.",
                                          "Nothing to save", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pop up a file chooser so the user picks where to save.
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("ira_projection.png"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File out = chooser.getSelectedFile();
        // Add .png if they didn't type it.
        if (!out.getName().toLowerCase().endsWith(".png")) {
            out = new File(out.getParentFile(), out.getName() + ".png");
        }

        // Make a blank image and draw into it (inputs at top, charts below).
        int width = 700;
        int height = 900;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        try {
            // White background.
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // Title at the top.
            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g.drawString("IRA Projection Report", 10, 24);

            // List all the input values and the two summary lines.
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            int y = 48;
            for (String line : inputLines()) {
                g.drawString(line, 10, y);
                y += 16;
            }

            // Reuse the same chart drawing code from ChartPanel.
            rothChart.paintToGraphics(g, 0, 220, width, 320);
            tradChart.paintToGraphics(g, 0, 560, width, 320);
        } finally {
            // Always release the Graphics object even if something blew up.
            g.dispose();
        }

        // Actually write the image to disk. ImageIO.write can throw IOException
        // (e.g. read-only folder), so handle it.
        try {
            ImageIO.write(img, "png", out);
            JOptionPane.showMessageDialog(this, "Saved to:\n" + out.getAbsolutePath(),
                                          "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not save image:\n" + e.getMessage(),
                                          "Save failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // The lines of text that go at the top of the saved image (so the
    // image is self-explanatory and doesn't lose context).
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

    // Lets me run just this page on its own for testing.
    // My teammates won't use this -- they'll just call "new IRACalculatorPage()"
    // from their main app.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(IRACalculatorPage::new);
    }
}
