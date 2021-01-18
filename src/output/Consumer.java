package output;

import common.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "isBankrupt", "budget" })
@JsonIgnoreProperties({"income", "lastPrice"})
public final class Consumer implements OutElement {
    private int id;
    private boolean isBankrupt;
    private long budget;
    private int income;

    public Consumer() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(final int income) {
        this.income = income;
    }

    /**
     * Add income to budget.
     */
    public void income() {
        if (!isBankrupt) {
            budget += income;
        }
    }

    /**
     * Consumer pay the price of contract.
     * @param contract
     */
    public void payContract(final Contract contract) {
        boolean payed = false;
        long price = contract.getPrice();
        long lastPrice = contract.getLastPrice();
        long notPayedPrice = Math.round(Math.floor(Constants.PENALISE_RATE * lastPrice)) + price;
        if (budget - price > 0 && contract.getPayed()) {
            budget -= price;
            payed = true;
        } else if (budget - notPayedPrice > 0 && !contract.getPayed()) {
            budget -= notPayedPrice;
            payed = true;
        } else if (budget - notPayedPrice < 0 && !contract.getPayed()) {
            isBankrupt = true;
            payed = false;
        }
        contract.setPayed(payed);
    }
}
