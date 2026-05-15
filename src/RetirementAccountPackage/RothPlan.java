/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 1- Nikolas Tsagaris
 * Date: 2026-05-14
 */

package RetirementAccountPackage;

/**
 * Roth tax treatment (after-tax contributions), common to Roth 401(k) and Roth IRA accounts.
 */
public class RothPlan extends RetirementPlan {

    public RothPlan() {
        super("Roth (after-tax)");
    }

    @Override
    public String getTagline() {
        return "Pay taxes now, qualified withdrawals may be tax-free.";
    }

    @Override
    public String getContributionTaxSummary() {
        return "Contributions are made with after-tax dollars. They do not reduce your "
                + "current taxable income, but grow tax-free for qualified distributions "
                + "(subject to IRS rules such as the five-year rule).";
    }

    @Override
    public String getWithdrawalTaxSummary() {
        return "Qualified withdrawals of contributions and earnings may be completely tax-free. "
                + "Non-qualified withdrawals of earnings may be taxed and penalized.";
    }

    @Override
    public String getRmdSummary() {
        return "Roth IRAs have no RMDs during the original owner's lifetime. "
                + "Roth 401(k) accounts may have RMD rules while at an employer, but can often "
                + "be rolled to a Roth IRA to avoid RMDs during life (check current IRS guidance).";
    }

    @Override
    public String getBestForSummary() {
        return "Often considered when you expect higher taxes later, want tax-free growth, "
                + "or want flexibility on withdrawals of contributions.";
    }
}
