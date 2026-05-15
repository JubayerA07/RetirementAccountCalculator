package FourZeroOneKPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

public class FourZeroOneKGUI extends JFrame {

    private JTextField annualSalary;
    private  JTextField contributionPercentPerMonth;
    private  JTextField estimatedSalaryPercentIncrease;
    private  JTextField currentAge;
    private  JTextField plannedRetirementAge;
    private  JTextField expectedRateOfReturn;
    private  JTextField current401kBalance;
    private  JTextField employerMatchPercent;
    private JTextField salaryLimitPercent;
    private JTextField marginalTaxRate;
    private JButton calculateButton;
    private  JButton resetButton;
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset rothDataset;
    private ChartPanel rothChartPanel;



    public FourZeroOneKGUI() {

        super("401K Calculator");
        setSize(1000,1000);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        addGUIElements();



        setVisible(true);


    }

    private void addGUIElements(){

        JLabel TitleLabel = new JLabel("401(K) Calculator");
        TitleLabel.setFont(new Font("Title", Font.BOLD, 32));
        TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TitleLabel.setBounds(160, 10, 540, 39);
        add(TitleLabel);

        //User Info label
        JLabel userInfoLabel = new JLabel("User Info");
        userInfoLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        userInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userInfoLabel.setBounds(70,100, 275,40);
        userInfoLabel.setBackground(new Color(50, 96, 168));
        userInfoLabel.setForeground(Color.WHITE);
        userInfoLabel.setOpaque(true);
        add(userInfoLabel);

        //Annual Salary Label
        JLabel annualSalaryLabel = new JLabel("Annual Salary($)");
        annualSalaryLabel.setFont(new Font("Dialog", Font.BOLD, 17));
        annualSalaryLabel.setBounds(40, 175,130,40);
        add(annualSalaryLabel);

        //Salary Text Field
        annualSalary = new JTextField();
        annualSalary.setText("40000");
        annualSalary.setBounds(240, 175, 200, 40);
        add(annualSalary);

        //Contribution Label
        JLabel contributionLabel = new JLabel("Contribution(%)");
        contributionLabel.setFont(new Font("Dialog", Font.BOLD,17));
        contributionLabel.setBounds(40, 240, 130,40);
        add(contributionLabel);

        //Contribution Text Field
        contributionPercentPerMonth = new JTextField();
        contributionPercentPerMonth.setText("10");
        contributionPercentPerMonth.setBounds(240, 240, 200,40);
        add(contributionPercentPerMonth);


        //Employer Match Percent Label
        JLabel employerMatchLabel = new JLabel("Employer Match(%)");
        employerMatchLabel.setFont(new Font("Dialog", Font.BOLD, 17));
        employerMatchLabel.setBounds(40, 305, 160, 40);
        add(employerMatchLabel);

        //Employer Match Percent Text Field
        employerMatchPercent = new JTextField();
        employerMatchPercent.setText("50");
        employerMatchPercent.setBounds(240, 305, 200,40);
        add(employerMatchPercent);

        //Salary Increase Percent Label
        JLabel salaryIncreaseLabel = new JLabel("Salary Increase(%)");
        salaryIncreaseLabel.setFont(new Font("Dialog", Font.BOLD, 17));
        salaryIncreaseLabel.setBounds(40, 370, 160, 40);
        add(salaryIncreaseLabel);

        //Salary Increase Text field
        estimatedSalaryPercentIncrease = new JTextField();
        estimatedSalaryPercentIncrease.setText("0");
        estimatedSalaryPercentIncrease.setBounds(240,370,200,40);
        add(estimatedSalaryPercentIncrease);

        //Rate of return label
        JLabel rateOfReturnLabel = new JLabel("Rate of Return(%)");
        rateOfReturnLabel.setFont(new Font("Dialog", Font.BOLD,17));
        rateOfReturnLabel.setBounds(40, 435, 160,40);
        add(rateOfReturnLabel);

        //Rate of return Text field
        expectedRateOfReturn = new JTextField();
        expectedRateOfReturn.setText("7");
        expectedRateOfReturn.setBounds(240,435,200,40);
        add(expectedRateOfReturn);

        //Salary Limit Label
        JLabel salaryLimitLabel = new JLabel("Salary Limit(%)");
        salaryLimitLabel.setFont(new Font("Dialog", Font.BOLD,17));
        salaryLimitLabel.setBounds(40, 500,160,40);
        add(salaryLimitLabel);

        //Salary Limit Text Field
        salaryLimitPercent = new JTextField();
        salaryLimitPercent.setText("6");
        salaryLimitPercent.setBounds(240,500,200,40);
        add(salaryLimitPercent);


        //Current Age Label
        JLabel currentAgeLabel = new JLabel("Current Age");
        currentAgeLabel.setFont(new Font("Dialog", Font.BOLD, 17));
        currentAgeLabel.setBounds(40, 565, 130, 40);
        add(currentAgeLabel);

       //Current Age Text field
        currentAge = new JTextField();
        currentAge.setText("30");
        currentAge.setBounds(240, 565, 200, 40);
        add(currentAge);

        //Planned Retirement Age Label
        JLabel plannedRetirementLabel = new JLabel("Retirement Age");
        plannedRetirementLabel.setFont(new Font("Dialog", Font.BOLD, 17));
        plannedRetirementLabel.setBounds(40, 630, 130,40);
        add(plannedRetirementLabel);

        //Planned retirement Age Text field
        plannedRetirementAge = new JTextField();
        plannedRetirementAge.setText("65");
        plannedRetirementAge.setBounds(240,630,200,40);
        add(plannedRetirementAge);

        //Current 401K Balance Label
        JLabel currentBalanceLabel = new JLabel("Current Balance($)");
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD,17));
        currentBalanceLabel.setBounds(40, 695, 150, 40);
        add(currentBalanceLabel);

        //Current 401k Balance Text field
        current401kBalance = new JTextField();
        current401kBalance.setText("0");
        current401kBalance.setBounds(240,695,200,40);
        add(current401kBalance);

        //Marginal Tax Rate Label
        JLabel marginalTaxLabel = new JLabel("Marginal Tax(%)");
        marginalTaxLabel.setFont(new Font("Dialog", Font.BOLD,17));
        marginalTaxLabel.setBounds(40, 760, 150, 40);
        add(marginalTaxLabel);

        //Marginal Tax Rate Text field
        marginalTaxRate = new JTextField();
        marginalTaxRate.setText("22");
        marginalTaxRate.setBounds(240,760,200,40);
        add(marginalTaxRate);

        //Results Labels//
        //Label Heading
        JLabel resultsAreaLabel = new JLabel("Your Estimated Retirement");
        resultsAreaLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        resultsAreaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsAreaLabel.setBackground(new Color(50, 96, 168));
        resultsAreaLabel.setForeground(Color.WHITE);
        resultsAreaLabel.setOpaque(true);
        resultsAreaLabel.setBounds(500, 100, 375, 60);
        add(resultsAreaLabel);

        //Traditional 401K Label
        JLabel trad401kLabel = new JLabel("Traditional 401k");
        trad401kLabel.setFont(new Font("Dialog", Font.BOLD,17));
        trad401kLabel.setOpaque(true);
        trad401kLabel.setHorizontalAlignment(SwingConstants.CENTER);
        trad401kLabel.setBackground(new Color(50,96,168));
        trad401kLabel.setForeground(Color.WHITE);
        trad401kLabel.setBounds(500,160,188, 40);
        add(trad401kLabel);


        //Trad 401k Result area
        JTextField resultsArea = new JTextField();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Dialog", Font.BOLD, 25));
        resultsArea.setCaretColor(Color.WHITE);
        JScrollPane resultsAreaPane = new JScrollPane(resultsArea);
        resultsAreaPane.setBounds(500, 205, 188, 40);
        resultsArea.setHorizontalAlignment(SwingConstants.CENTER);
        resultsArea.setBackground(Color.WHITE);
        add(resultsAreaPane);

        //Roth 401k Label
        JLabel roth401kLabel = new JLabel("Roth 401k");
        roth401kLabel.setFont(new Font("Dialog", Font.BOLD,17));
        roth401kLabel.setOpaque(true);
        roth401kLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roth401kLabel.setBackground(new Color(50,96,168));
        roth401kLabel.setForeground(Color.WHITE);
        roth401kLabel.setBounds(687,160,188, 40);
        add(roth401kLabel);

        //Roth 401k result
        JTextField rothResultsArea = new JTextField();
        rothResultsArea.setEditable(false);
        rothResultsArea.setFont(new Font("Dialog", Font.BOLD, 25));
        rothResultsArea.setCaretColor(Color.WHITE);
        JScrollPane rothResultsAreaPane = new JScrollPane(rothResultsArea);
        rothResultsAreaPane.setBounds(687, 205, 187, 40);
        rothResultsArea.setHorizontalAlignment(SwingConstants.CENTER);
        rothResultsArea.setBackground(Color.WHITE);
        add(rothResultsAreaPane);

        //Traditional 401k total Employee Cont area
        JTextField totalEmployeeContArea = new JTextField();
        totalEmployeeContArea.setEditable(false);
        totalEmployeeContArea.setFont(new Font("Dialog", Font.BOLD, 20));
        totalEmployeeContArea.setCaretColor(Color.WHITE);
        JScrollPane totalEmployeeContAreaPane = new JScrollPane(totalEmployeeContArea);
        totalEmployeeContAreaPane.setBounds(500, 247, 94, 40);
        totalEmployeeContArea.setHorizontalAlignment(SwingConstants.CENTER);
        totalEmployeeContArea.setBackground(Color.WHITE);
        add(totalEmployeeContAreaPane);

        //Traditional 401k total Employer Cont area
        JTextField totalEmployerContArea = new JTextField();
        totalEmployerContArea.setEditable(false);
        totalEmployerContArea.setFont(new Font("Dialog", Font.BOLD, 20));
        totalEmployerContArea.setCaretColor(Color.WHITE);
        JScrollPane totalEmployerContAreaPane = new JScrollPane(totalEmployerContArea);
        totalEmployerContAreaPane.setBounds(594, 247, 94, 40);
        totalEmployerContArea.setHorizontalAlignment(SwingConstants.CENTER);
        totalEmployerContArea.setBackground(Color.WHITE);
        add(totalEmployerContAreaPane);

        //Roth 401k total Employee Cont area
        JTextField rothEmployeeContArea = new JTextField();
        rothEmployeeContArea.setEditable(false);
        rothEmployeeContArea.setFont(new Font("Dialog", Font.BOLD, 20));
        rothEmployeeContArea.setCaretColor(Color.WHITE);
        JScrollPane rothEmployeeContAreaPane = new JScrollPane(rothEmployeeContArea);
        rothEmployeeContAreaPane.setBounds(688, 247, 94, 40);
        rothEmployeeContArea.setHorizontalAlignment(SwingConstants.CENTER);
        rothEmployeeContArea.setBackground(Color.WHITE);
        add(rothEmployeeContAreaPane);

        //Roth 401k total Employee Cont area
        JTextField rothEmployerContArea = new JTextField();
        rothEmployerContArea.setEditable(false);
        rothEmployerContArea.setFont(new Font("Dialog", Font.BOLD, 20));
        rothEmployerContArea.setCaretColor(Color.WHITE);
        JScrollPane rothEmployerContAreaPane = new JScrollPane(rothEmployerContArea);
        rothEmployerContAreaPane.setBounds(782, 247, 94, 40);
        rothEmployerContArea.setHorizontalAlignment(SwingConstants.CENTER);
        rothEmployerContArea.setBackground(Color.WHITE);
        add(rothEmployerContAreaPane);


        //Result Legend Label//
        //Total label
        JLabel totalLabel = new JLabel("Total");
        totalLabel.setFont(new Font("Dialog", Font.BOLD,10));
        totalLabel.setOpaque(true);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setBackground(new Color(50,96,168));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setBounds(880, 205, 100, 40);
        add(totalLabel);

        //Employee Cont/Employer Cont label
        JLabel employContsLabel = new JLabel("Employee Cont(L)|Employer Cont(R)");
        employContsLabel.setFont(new Font("Dialog", Font.BOLD,10));
        employContsLabel.setOpaque(true);
        employContsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        employContsLabel.setBackground(new Color(50,96,168));
        employContsLabel.setForeground(Color.WHITE);
        employContsLabel.setBounds(500, 287, 187, 40);
        add(employContsLabel);

        //Employee Cont/Employer Cont label
        JLabel rothEmployContsLabel = new JLabel("Employee Cont(L)|Employer Cont(R)");
        rothEmployContsLabel.setFont(new Font("Dialog", Font.BOLD,10));
        rothEmployContsLabel.setOpaque(true);
        rothEmployContsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rothEmployContsLabel.setBackground(new Color(50,96,168));
        rothEmployContsLabel.setForeground(Color.WHITE);
        rothEmployContsLabel.setBounds(688, 287, 187, 40);
        add(rothEmployContsLabel);




        //Screenshot Button
        JButton screenshot = new JButton("Screenshot");
        screenshot.setBackground(new Color(50, 96,168));
        screenshot.setForeground(Color.WHITE);
        screenshot.setOpaque(true);
        screenshot.setBounds(20, 20, 130, 40);
        add(screenshot);
        screenshot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Robot robot = new Robot();
                    Rectangle screenRect = FourZeroOneKGUI.this.getBounds();
                    BufferedImage screenshot = robot.createScreenCapture(screenRect);

                    File file = new File("C:\\Users\\jubay\\IdeaProjects\\RetirementAccountCalculator\\src\\RetirementAccountPackage\\retirement_calculator_screenshot.png");
                    ImageIO.write(screenshot, "png", file);

                    JOptionPane.showMessageDialog(FourZeroOneKGUI.this, "Screenshot saved!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FourZeroOneKGUI.this, "Screenshot failed.");
                    ex.printStackTrace();
                }
            }
        });






        //Calculate Button
        calculateButton = new JButton("Calculate!");
        calculateButton.setFont(new Font("Dialog", Font.BOLD,17));
        calculateButton.setHorizontalAlignment(SwingConstants.CENTER);
        calculateButton.setBackground(new Color(50,96,168));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setOpaque(true);
        calculateButton.setBounds(285,815, 150,40 );
        add(calculateButton);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] parameterArray = {annualSalary.getText(), contributionPercentPerMonth.getText(),
                        estimatedSalaryPercentIncrease.getText(), currentAge.getText(), plannedRetirementAge.getText(),
                        expectedRateOfReturn.getText(), current401kBalance.getText(), employerMatchPercent.getText(),
                        salaryLimitPercent.getText(), marginalTaxRate.getText()};

                try {
                    validateParameterExists(parameterArray);
                } catch (InvalidParameterException ex) {
                    ex.printStackTrace();
                    return;
                }

                try {
                    validateParameter(parameterArray);
                } catch (InvalidParameterException ex) {
                    ex.printStackTrace();
                    return;
                }



                resultsArea.setText("$" + calculate401k(Integer.parseInt(plannedRetirementAge.getText())));
                rothResultsArea.setText("$" + calculateRoth401k(Integer.parseInt(plannedRetirementAge.getText())));

                int integerEmployeeCont = (int) trad401kEmployeeCont(Integer.parseInt(plannedRetirementAge.getText()));

                totalEmployeeContArea.setText("$" + integerEmployeeCont);

                int integerRothEmployeeCont = (int) roth401kEmployeeCont(Integer.parseInt(plannedRetirementAge.getText()));

                rothEmployeeContArea.setText("$" + integerRothEmployeeCont);
                updateChart();

                int integerEmployerCont = (int) trad401kEmployerCont(Integer.parseInt(plannedRetirementAge.getText()));

                totalEmployerContArea.setText("$" + integerEmployerCont);
                rothEmployerContArea.setText("$" + integerEmployerCont);
                updateChart();



                revalidate();
                repaint();




            }
        });


        //reset Button
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Dialog", Font.BOLD, 17));
        resetButton.setHorizontalAlignment(SwingConstants.CENTER);
        resetButton.setBackground(new Color(50,96,168));
        resetButton.setForeground(Color.WHITE);
        resetButton.setOpaque(true);
        resetButton.setBounds(100,815,150,40);
        add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                annualSalary.setText("");
                contributionPercentPerMonth.setText("");
                employerMatchPercent.setText("");
                estimatedSalaryPercentIncrease.setText("");
                expectedRateOfReturn.setText("");
                salaryLimitPercent.setText("");
                currentAge.setText("");
                plannedRetirementAge.setText("");
                current401kBalance.setText("");
                marginalTaxRate.setText("");
                resultsArea.setText("");
                rothResultsArea.setText("");
                dataset.clear();
                rothDataset.clear();



            }
        });

        //Graphs//
        //create new 401k graph
        dataset = new DefaultCategoryDataset();

        JFreeChart lineChart = ChartFactory.createLineChart(
                "401k Growth Over Time",
                "Age",
                "Balance ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chartPanel = new ChartPanel(lineChart);
        chartPanel.setBounds(500, 350, 375, 270);
        add(chartPanel);

        //create new roth 401k graph

        rothDataset = new DefaultCategoryDataset();

        JFreeChart rothLineChart = ChartFactory.createLineChart(
                "Roth 401k Growth Over Time",
                "Age",
                "Balance ($)",
                rothDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        rothChartPanel = new ChartPanel(rothLineChart);
        rothChartPanel.setBounds(500, 630, 375, 260);
        add(rothChartPanel);






    }

    private void updateChart(){
        dataset.clear();
        rothDataset.clear();

        int ageNow = Integer.parseInt(currentAge.getText());
        int retiredAge = Integer.parseInt(plannedRetirementAge.getText());
        int sub = (retiredAge - ageNow)/ 5;

        for(int i = ageNow; i <= retiredAge; i++){

            double balanceWithEmployer = calculate401k(i);
            double balanceWithOutEmployer = calculate401kWithoutMatch(i);
            double totalEmployeeCont = trad401kEmployeeCont(i);

            dataset.addValue(balanceWithEmployer,"401k w Employer Match", i + "");
            dataset.addValue(balanceWithOutEmployer,"401k w/o Employer Match", i + "");
            dataset.addValue(totalEmployeeCont,"Employee Contribution", i + "");

        }

        for(int i = ageNow; i <= retiredAge; i++){

            double rothBalanceWithEmployer = calculateRoth401k(i);
            double rothBalanceWithOutEmployer = calculateRoth401kWihOutEmployer(i);
            double totalEmployeeCont = trad401kEmployeeCont(i);



            rothDataset.addValue(rothBalanceWithEmployer,"401k w Employer Match", i + "");
            rothDataset.addValue(rothBalanceWithOutEmployer,"401k w/o Employer Match", i + "");
            rothDataset.addValue(totalEmployeeCont,"Employee Contribution", i + "");



        }


    }








    private int calculate401k(int n){

        //calculate PMT
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;
        double limit = (Double.parseDouble(salaryLimitPercent.getText()))/100;
        double match = (Double.parseDouble(employerMatchPercent.getText()))/100;
        double PMT = (salary * contribution)+((salary*limit)*match);

        int time = n - (Integer.parseInt(currentAge.getText()));
        double currentBalance = Double.parseDouble(current401kBalance.getText());
        double rate = (Double.parseDouble(expectedRateOfReturn.getText()))/100;
        //calculate result
        double resultD = (currentBalance*(Math.pow((1 + rate),time))) + (PMT * (((Math.pow((1+rate),time))- 1)/rate));
        return (int) resultD;




    }

    private double calculate401kWithoutMatch(int n){

        //calculate PMT
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;
        double limit = (Double.parseDouble(salaryLimitPercent.getText()))/100;
        double match = (Double.parseDouble(employerMatchPercent.getText()))/100;
        double PMT = (salary * contribution);

        int time = n - (Integer.parseInt(currentAge.getText()));
        double currentBalance = Double.parseDouble(current401kBalance.getText());
        double rate = (Double.parseDouble(expectedRateOfReturn.getText()))/100;
        //calculate result
        return (currentBalance*(Math.pow((1 + rate),time))) + (PMT * (((Math.pow((1+rate),time))- 1)/rate));





    }

    private double trad401kEmployeeCont(int n){

        int time = n - (Integer.parseInt(currentAge.getText()));
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;

        return salary * contribution * time;




    }

    private double trad401kEmployerCont(int n){

        int time = n - (Integer.parseInt(currentAge.getText()));
        double salary = Double.parseDouble(annualSalary.getText());
        double limit = (Double.parseDouble(salaryLimitPercent.getText()))/100;
        double match = (Double.parseDouble(employerMatchPercent.getText()))/100;

        return salary * limit * match * time;




    }

    private double roth401kEmployeeCont(int n){

        int time = n - (Integer.parseInt(currentAge.getText()));
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;
        double marginalTax = (Double.parseDouble(marginalTaxRate.getText()))/ 100;

        return salary * contribution * time * (1- marginalTax);




    }



    private int calculateRoth401k(int n){

        //calculate PMT
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;
        double limit = (Double.parseDouble(salaryLimitPercent.getText()))/100;
        double match = (Double.parseDouble(employerMatchPercent.getText()))/100;
        double mTax = (Double.parseDouble(marginalTaxRate.getText()))/100;
        double PMT = (salary * contribution* (1-mTax))+((salary*limit)*match);

        int time = n - (Integer.parseInt(currentAge.getText()));
        double currentBalance = Double.parseDouble(current401kBalance.getText());
        double rate = (Double.parseDouble(expectedRateOfReturn.getText()))/100;
        //calculate result
        double resultD = (currentBalance*(Math.pow((1 + rate),time))) + (PMT * (((Math.pow((1+rate),time))- 1)/rate));
        return  (int) resultD;




    }

    private int calculateRoth401kWihOutEmployer(int n){

        //calculate PMT
        double salary = Double.parseDouble(annualSalary.getText());
        double contribution = (Double.parseDouble(contributionPercentPerMonth.getText()))/100;
        double mTax = (Double.parseDouble(marginalTaxRate.getText()))/100;
        double PMT = (salary * contribution* (1-mTax));

        int time = n - (Integer.parseInt(currentAge.getText()));
        double currentBalance = Double.parseDouble(current401kBalance.getText());
        double rate = (Double.parseDouble(expectedRateOfReturn.getText()))/100;
        //calculate result
        double resultD = (currentBalance*(Math.pow((1 + rate),time))) + (PMT * (((Math.pow((1+rate),time))- 1)/rate));
        return  (int) resultD;




    }

    public static boolean isNumber(String input){


        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e){

            return false;
        }
    }

    private void numberErrorGUI(){

        JOptionPane parameterError = new JOptionPane();
        JOptionPane.showMessageDialog(parameterError, "Please enter numbers only!", "Error!", JOptionPane.ERROR_MESSAGE);
        add(parameterError);
    }

    private void missingParameterErrorGUI(){

        JOptionPane missingParameterError = new JOptionPane();
        JOptionPane.showMessageDialog(missingParameterError, "No empty parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        add(missingParameterError);
    }

    public boolean validateParameter(String[] parameterArray) throws InvalidParameterException{

       for (String parameter : parameterArray){

           if(!isNumber(parameter)){

               numberErrorGUI();
               throw new InvalidParameterException("Please enter numbers only!");




           }


       }


        return true;


    }

    public boolean validateParameterExists(String[] parameterArray) throws InvalidParameterException{

        for (String parameter : parameterArray){

            if(parameter.trim().isEmpty()){

                missingParameterErrorGUI();
                throw new InvalidParameterException("No empty parameters!");




            }


        }


        return true;


    }




    public static void main(String[] args) {
        new FourZeroOneKGUI();
    }

}
