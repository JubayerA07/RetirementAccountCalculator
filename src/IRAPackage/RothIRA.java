/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 3
 * Date: 2026-05-15
 */
package IRAPackage;

// Roth IRA -- you pay tax NOW on contributions, but withdrawals later are
// completely tax-free. So when we look at the projected balance, we don't
// have to subtract anything for taxes.
public class RothIRA extends IRAAccount {

    // Just passes everything up to the parent. No extra fields needed.
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

    // No tax adjustment -- Roth withdrawals are tax-free.
    @Override
    public double applyTaxTreatment(double preTaxBalance) {
        return preTaxBalance;
    }
}
