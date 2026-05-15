/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 3
 * Date: 2026-05-15
 */
package IRAPackage;

// This is the parent class for both Roth and Traditional IRA.
// Abstract because we never actually make a plain "IRAAccount" --
// we only make one of the two subclasses. They share most of the
// math, but tax treatment is different, so that part is abstract.
public abstract class IRAAccount {

    // IRS contribution limits (2024). If you're 50+ you get a catch-up bump.
    public static final double CONTRIB_LIMIT_UNDER_50 = 7000.0;
    public static final double CONTRIB_LIMIT_50_PLUS  = 8000.0;

    // All the inputs the user types in. Private = encapsulation.
    private final double startingBalance;
    private final int currentAge;
    private final int retirementAge;
    private final double annualContribution;
    private final double rateOfReturn;     // stored as decimal, so 7% = 0.07
    private final double marginalTaxRate;  // also a decimal, 22% = 0.22

    // Protected constructor -- only subclasses can call it.
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

    // Helper so the GUI can grab the right contribution limit for the user's age.
    public static double limitForAge(int age) {
        return age >= 50 ? CONTRIB_LIMIT_50_PLUS : CONTRIB_LIMIT_UNDER_50;
    }

    // Standard getters. Needed because the fields are private.
    public double getStartingBalance()    { return startingBalance; }
    public int    getCurrentAge()         { return currentAge; }
    public int    getRetirementAge()      { return retirementAge; }
    public double getAnnualContribution() { return annualContribution; }
    public double getRateOfReturn()       { return rateOfReturn; }
    public double getMarginalTaxRate()    { return marginalTaxRate; }

    // Each subclass tells us its own name ("Roth IRA" or "Traditional IRA").
    public abstract String getPlanName();

    // The big difference between Roth and Traditional happens here.
    // Roth = no tax on withdrawal, Traditional = taxed at marginal rate.
    public abstract double applyTaxTreatment(double preTaxBalance);

    // Year-by-year simulation. Add contribution, grow it, store the result.
    // This is the same loop for both Roth and Traditional -- only the
    // applyTaxTreatment call at the end of each year changes the answer.
    public ProjectionResult project() {
        int years = retirementAge - currentAge;

        // Arrays to hold age, balance, and total contributions for each year.
        double[] balances = new double[years + 1];
        double[] contributionsTotal = new double[years + 1];
        int[] ages = new int[years + 1];

        // Year 0 = today (no contribution yet, just whatever they started with).
        balances[0] = startingBalance;
        contributionsTotal[0] = 0.0;
        ages[0] = currentAge;

        double balance = startingBalance;
        double totalContrib = 0.0;
        for (int i = 1; i <= years; i++) {
            balance += annualContribution;           // put in this year's money
            totalContrib += annualContribution;      // keep a running total
            balance *= (1.0 + rateOfReturn);         // compounding interest
            balances[i] = applyTaxTreatment(balance); // tax adjusted (subclass decides)
            contributionsTotal[i] = totalContrib;
            ages[i] = currentAge + i;
        }
        return new ProjectionResult(ages, balances, contributionsTotal);
    }

    // Little helper class so I can return all three arrays + totals together.
    // Made it static so it doesn't need an outer IRAAccount instance to exist.
    public static class ProjectionResult {
        public final int[] ages;
        public final double[] balances;
        public final double[] contributions;

        public ProjectionResult(int[] ages, double[] balances, double[] contributions) {
            this.ages = ages;
            this.balances = balances;
            this.contributions = contributions;
        }
        // Last value in the array = the value at retirement age.
        public double endingBalance()      { return balances[balances.length - 1]; }
        public double totalContributions() { return contributions[contributions.length - 1]; }
    }
}
