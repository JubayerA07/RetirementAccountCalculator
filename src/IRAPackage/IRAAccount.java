/*
 * Retirement Account Calculator
 * CSCI 185
 * Date: 2026-05-15
 */
package IRAPackage;

// parent class for Roth and Traditional IRA
public abstract class IRAAccount {

    // IRS contribution limits
    public static final double CONTRIB_LIMIT_UNDER_50 = 7000.0;
    public static final double CONTRIB_LIMIT_50_PLUS  = 8000.0;

    // user inputs
    private final double startingBalance;
    private final int currentAge;
    private final int retirementAge;
    private final double annualContribution;
    private final double rateOfReturn;
    private final double marginalTaxRate;

    protected IRAAccount(double startingBalance,
                         int currentAge,
                         int retirementAge,
                         double annualContribution,
                         double rateOfReturn,
                         double marginalTaxRate) {
        this.startingBalance = startingBalance;
        this.currentAge = currentAge;
        this.retirementAge = retirementAge;
        this.annualContribution = annualContribution;
        this.rateOfReturn = rateOfReturn;
        this.marginalTaxRate = marginalTaxRate;
    }

    // limit changes at 50
    public static double limitForAge(int age) {
        return age >= 50 ? CONTRIB_LIMIT_50_PLUS : CONTRIB_LIMIT_UNDER_50;
    }

    public double getStartingBalance()    { return startingBalance; }
    public int    getCurrentAge()         { return currentAge; }
    public int    getRetirementAge()      { return retirementAge; }
    public double getAnnualContribution() { return annualContribution; }
    public double getRateOfReturn()       { return rateOfReturn; }
    public double getMarginalTaxRate()    { return marginalTaxRate; }

    public abstract String getPlanName();

    // subclasses handle taxes differently
    public abstract double applyTaxTreatment(double preTaxBalance);

    // year by year math
    public ProjectionResult project() {
        int years = retirementAge - currentAge;
        double[] balances = new double[years + 1];
        double[] contributionsTotal = new double[years + 1];
        int[] ages = new int[years + 1];

        balances[0] = startingBalance;
        contributionsTotal[0] = 0.0;
        ages[0] = currentAge;

        double balance = startingBalance;
        double totalContrib = 0.0;
        for (int i = 1; i <= years; i++) {
            balance += annualContribution;
            totalContrib += annualContribution;
            balance *= (1.0 + rateOfReturn);
            balances[i] = applyTaxTreatment(balance);
            contributionsTotal[i] = totalContrib;
            ages[i] = currentAge + i;
        }
        return new ProjectionResult(ages, balances, contributionsTotal);
    }

    // holds the results
    public static class ProjectionResult {
        public final int[] ages;
        public final double[] balances;
        public final double[] contributions;

        public ProjectionResult(int[] ages, double[] balances, double[] contributions) {
            this.ages = ages;
            this.balances = balances;
            this.contributions = contributions;
        }
        public double endingBalance()      { return balances[balances.length - 1]; }
        public double totalContributions() { return contributions[contributions.length - 1]; }
    }
}
