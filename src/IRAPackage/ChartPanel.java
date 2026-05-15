/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 3
 * Date: 2026-05-15
 */
package IRAPackage;

import javax.swing.*;
import java.awt.*;

// Custom chart panel. I didn't want to fight with JFreeChart libraries so
// I just draw the chart myself with Graphics2D. Two lines per chart:
// one for the retirement savings, one for the total contributions.
public class ChartPanel extends JPanel {

    // The title shown above the chart ("Roth IRA" or "Traditional IRA").
    private final String title;

    // Holds the data we got from project(). Null until Calculate is pressed.
    private IRAAccount.ProjectionResult data;

    public ChartPanel(String title) {
        this.title = title;
        setPreferredSize(new Dimension(400, 250));
        setBackground(Color.WHITE);
    }

    // Called from the GUI when new results are ready.
    public void setData(IRAAccount.ProjectionResult data) {
        this.data = data;
        repaint(); // tells Swing to redraw this panel
    }

    public IRAAccount.ProjectionResult getData() {
        return data;
    }

    // Swing calls this whenever it needs to draw this panel.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Delegate to paintToGraphics so the same code is used for saving.
        paintToGraphics((Graphics2D) g, 0, 0, getWidth(), getHeight());
    }

    // The actual drawing logic. Split into its own method so the Save Image
    // button can reuse it to draw onto a BufferedImage instead of the screen.
    public void paintToGraphics(Graphics2D g, int xOff, int yOff, int w, int h) {
        // Title across the top of the chart area.
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g.drawString(title, xOff + 10, yOff + 18);

        // If no data yet, just show a hint and bail out.
        if (data == null) {
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            g.drawString("(Press Calculate to see chart)", xOff + 10, yOff + 40);
            return;
        }

        // Figure out the chart's drawing area (leaves room for axis labels).
        int left   = xOff + 60;
        int right  = xOff + w - 20;
        int top    = yOff + 30;
        int bottom = yOff + h - 40;

        // Draw the x and y axis lines.
        g.setColor(Color.GRAY);
        g.drawLine(left, bottom, right, bottom); // x-axis
        g.drawLine(left, top, left, bottom);     // y-axis

        // Find the biggest value in either dataset -- that's the top of the y-axis.
        int n = data.ages.length;
        int minAge = data.ages[0];
        int maxAge = data.ages[n - 1];
        double maxVal = 0;
        for (int i = 0; i < n; i++) {
            if (data.balances[i] > maxVal) maxVal = data.balances[i];
            if (data.contributions[i] > maxVal) maxVal = data.contributions[i];
        }
        if (maxVal == 0) maxVal = 1; // avoid divide by zero edge case

        // Axis labels.
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.drawString("$" + (int) maxVal, xOff + 5, top + 10);
        g.drawString("$0", xOff + 5, bottom);
        g.drawString(String.valueOf(minAge), left - 5, bottom + 14);
        g.drawString(String.valueOf(maxAge), right - 10, bottom + 14);
        g.drawString("Age", (left + right) / 2 - 10, bottom + 28);

        // Draw the two lines. Blue = savings, green = contributions.
        drawLine(g, data.balances, n, left, right, top, bottom, maxVal, minAge, maxAge, Color.BLUE);
        drawLine(g, data.contributions, n, left, right, top, bottom, maxVal, minAge, maxAge, new Color(0, 150, 0));

        // Little legend in the top right so you know what each color means.
        int legendX = right - 150;
        int legendY = top + 14;
        g.setColor(Color.BLUE);
        g.fillRect(legendX, legendY - 8, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("Retirement savings", legendX + 14, legendY);
        g.setColor(new Color(0, 150, 0));
        g.fillRect(legendX, legendY + 6, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("Total contributions", legendX + 14, legendY + 14);
    }

    // Plots one trace. The math here is just scaling the data to fit
    // inside the chart's pixel rectangle.
    private void drawLine(Graphics2D g, double[] vals, int n,
                          int left, int right, int top, int bottom,
                          double maxVal, int minAge, int maxAge, Color color) {
        g.setColor(color);
        int prevX = 0, prevY = 0;
        for (int i = 0; i < n; i++) {
            // Map age -> x pixel and value -> y pixel.
            int x = left + (int) ((double) (data.ages[i] - minAge) / (maxAge - minAge) * (right - left));
            int y = bottom - (int) (vals[i] / maxVal * (bottom - top));
            // Connect to the previous point (skip on the first one).
            if (i > 0) g.drawLine(prevX, prevY, x, y);
            // Little dot at each data point.
            g.fillOval(x - 2, y - 2, 4, 4);
            prevX = x;
            prevY = y;
        }
    }
}
