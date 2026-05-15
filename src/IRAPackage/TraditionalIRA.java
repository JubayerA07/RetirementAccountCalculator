/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 3
 * Date: 2026-05-15
 */
package IRAPackage;

// Traditional IRA -- opposite of Roth. Contributions are pre-tax (no tax now)
// but when you take the money out in retirement, you owe income tax on it.
// So our "balance" is shown after taxes are taken out, to make it a fair
// comparison with the Roth balance side by side.
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

    // Subtract the marginal tax rate from the balance.
    // Example: $100,000 with 22% tax = $78,000 after taxes.
    @Override
    public double applyTaxTreatment(double preTaxBalance) {
        return preTaxBalance * (1.0 - getMarginalTaxRate());
    }
}
