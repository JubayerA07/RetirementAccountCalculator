/*
 * Retirement Account Calculator
 * CSCI 185
 * Date: 2026-05-15
 */
package IRAPackage;

// Traditional = taxed on withdrawal
public class TraditionalIRA extends IRAAccount {

    public TraditionalIRA(double startingBalance,
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
        return "Traditional IRA";
    }

    // subtract tax from the balance
    @Override
    public double applyTaxTreatment(double preTaxBalance) {
        return preTaxBalance * (1.0 - getMarginalTaxRate());
    }
}
