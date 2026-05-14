package RetirementAccountPackage;

import javax.swing.*;

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





        //JLabel personalInfoLabel = new JLabel("Personal Information");
        //add(personalInfoLabel);


        /*


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
        add(annualSalaryLabel);
        add(current401kBalanceLabel);
        add(contributionLabel);
        add(employerMatchLabel);
        add(salaryLimitLabel);
        add(currentAge);
        add(annualSalary);
        add(current401kBalance);
        add(contributionPercentPerMonth);
        add(employerMatchPercent);
        add(salaryLimitPercent);
        add(projectionsLabel);
        add(plannedRetirementAgeLabel);
        add(estimatedSalaryPercentIncreaseLabel);
        add(expectedRateOfReturnLabel);
        add(marginalTaxRateLabel);
        add(plannedRetirementAge);
        add(estimatedSalaryPercentIncrease);
        add(expectedRateOfReturn);
        add(marginalTaxRate);

     */

        setVisible(true);






    }

    private void calculate401k(){

        //calculate PMT
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;
        double limit = (Double.parseDouble(salaryLimitPercent.getText()))/100;
        double match = (Double.parseDouble(employerMatchPercent.getText()))/100;
        double PMT = (salary * contribution)+((salary*limit)*match);

        int time = (Integer.parseInt(plannedRetirementAge.getText())) - (Integer.parseInt(currentAge.getText()));
        double currentBalance = Double.parseDouble(current401kBalance.getText());
        double rate = (Double.parseDouble(expectedRateOfReturn.getText()))/100;
        //calculate result
        double result = (currentBalance*(Math.pow((1 + rate),time))) + (PMT * (((Math.pow((1+rate),time))- 1)/rate));

    }

    public static void main(String[] args){

        new FourZeroOneK();
    }






}
