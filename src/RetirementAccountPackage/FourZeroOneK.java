package RetirementAccountPackage;

import javax.swing.*;
import java.awt.*;

public class FourZeroOneK extends JFrame {
    //instance variables
    private JTextField annualSalary;
    private JTextField contributionPercentPerMonth;
    private JTextField estimatedSalaryPercentIncrease;
    private JTextField currentAge;
    private JTextField plannedRetirementAge;
    private JTextField expectedRateOfReturn;
    private JTextField current401kBalance;
    private JTextField employerMatchPercent;
    private JTextField salaryLimitPercent;
    private JTextField marginalTaxRate;

    public FourZeroOneK() {

        setTitle("401K Calculator");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10,3));

        JLabel personalInfoLabel = new JLabel("Personal Information");

        JLabel ageLabel = new JLabel("Current Age:");
        currentAge = new JTextField();

        JLabel annualSalaryLabel = new JLabel("Annual Salary:");
        annualSalary = new JTextField();

        JLabel current401kBalanceLabel = new JLabel("Current 401(k) Balance:");
        current401kBalance = new JTextField();

        JLabel contributionLabel = new JLabel("Contribution Per Month(%):");
        contributionPercentPerMonth = new JTextField();

        JLabel employerMatchLabel = new JLabel("Employer match(%):");
        employerMatchPercent = new JTextField();

        JLabel salaryLimitLabel = new JLabel("Employer Limit(%):");
        salaryLimitPercent = new JTextField();

        JLabel projectionsLabel = new JLabel("Projections");

        JLabel plannedRetirementAgeLabel = new JLabel();
        plannedRetirementAge = new JTextField("Planned Retirement Age:");

        JLabel estimatedSalaryPercentIncreaseLabel = new JLabel("Expected Salary Increase(%):");
        estimatedSalaryPercentIncrease = new JTextField();

        JLabel expectedRateOfReturnLabel = new JLabel("Expected annual return(%):");
        expectedRateOfReturn = new JTextField();

        JLabel marginalTaxRateLabel = new JLabel("Expected inflation rate(%):");
        marginalTaxRate = new JTextField();


        add(personalInfoLabel);
        add(ageLabel);
        add(currentAge);


    }

    public static void main(String[] args){

        new FourZeroOneK();
    }






}
