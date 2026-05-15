/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 1- Nikolas Tsagaris
 * Date: 2026-05-14
 */

package RetirementAccountPackage;

/**
 * Traditional tax treatment (pre-tax contributions), common to traditional 401(k)
 * and traditional IRA accounts.
 */
public class TraditionalPlan extends RetirementPlan {

    public TraditionalPlan() {
        super("Traditional (pre-tax)");
    }

    @Override
    public String getTagline() {
        return "Save now, pay taxes later on withdrawals.";
    }

    @Override
    public String getContributionTaxSummary() {
        return "Contributions are typically made with pre-tax dollars, which can lower "
                + "your taxable income for the year you contribute (subject to IRS limits "
                + "and plan rules).";
    }

    @Override
    public String getWithdrawalTaxSummary() {
        return "Qualified withdrawals in retirement are taxed as ordinary income. "
                + "Non-qualified withdrawals may add penalties and taxes.";
    }

    @Override
    public String getRmdSummary() {
        return "Traditional IRAs and many traditional 401(k) accounts are subject to "
                + "required minimum distributions (RMDs) after you reach the IRS starting age, "
                + "unless still working under certain 401(k) exceptions.";
    }

    @Override
    public String getBestForSummary() {
        return "Often considered when you expect to be in a lower tax bracket in retirement "
                + "or want current-year tax deductions.";
    }
}
