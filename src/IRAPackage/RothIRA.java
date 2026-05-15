/*
 * Retirement Account Calculator
 * CSCI 185
 * Date: 2026-05-15
 */
package IRAPackage;

// Roth = no tax on withdrawal
public class RothIRA extends IRAAccount {

    public RothIRA(double startingBalance,
                   int currentAge,
                   int retirementAge,
                   double annualContribution,
                   double rateOfReturn,
                   double marginalTaxRate) {
        super(startingBalance, currentAge, retirementAge,
              annualContribution, rateOfReturn, marginalTaxRate);
    }

    @Override
    public String getPlanName() {
        return "Roth IRA";
    }

    // no tax adjustment
    @Override
    public double applyTaxTreatment(double preTaxBalance) {
        return preTaxBalance;
    }
}
