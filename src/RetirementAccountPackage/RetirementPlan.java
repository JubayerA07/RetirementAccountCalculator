/*
 * Retirement Account Calculator
 * CSCI 185
 * Contributors: Person 1- Nikolas Tsagaris
 * Date: 2026-05-14
 */

package RetirementAccountPackage;

/**
 * Base type for retirement savings plans used on the information screens.
 * Subclasses capture Roth vs. traditional tax treatment in plain language.
 */
public abstract class RetirementPlan {

    private final String displayName;

    protected RetirementPlan(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /** One-line summary shown in comparison headers. */
    public abstract String getTagline();

    /** How contributions are taxed (or not). */
    public abstract String getContributionTaxSummary();

    /** How qualified withdrawals are taxed. */
    public abstract String getWithdrawalTaxSummary();

    /** Required minimum distribution notes, if applicable. */
    public abstract String getRmdSummary();

    /** Who this plan type is usually a good fit for. */
    public abstract String getBestForSummary();
}
