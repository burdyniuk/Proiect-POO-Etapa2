package output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import strategies.EnergyChoiceStrategyType;

import java.util.ArrayList;

@JsonPropertyOrder({"id", "energyNeededKW", "contractCost", "budget", "producerStrategy",
                    "isBankrupt", "contracts"})
@JsonIgnoreProperties({"infrastructure", "production", "contractLength"})
public final class Distributor implements OutElement {
    private int id;
    private long budget;
    private int energyNeededKW;
    private boolean isBankrupt;
    private long contractCost;
    private EnergyChoiceStrategyType producerStrategy;
    private ArrayList<Contract> contracts = new ArrayList<Contract>();
    private long infrastructure;
    private long production;
    private int contractLength;

    public Distributor() {
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    public long getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(final long infrastructure) {
        this.infrastructure = infrastructure;
    }

    public long getProduction() {
        return production;
    }

    public void setProduction(final long production) {
        this.production = production;
    }

    /**
     * pay the price of contract
     * @param contract
     */
    public void pay(final Contract contract) {
        budget += contract.getPrice();
    }

    /**
     * Pay costs
     */
    public void payCosts() {
        int size = 0;
        for (Contract contract : contracts) {
            if (!contract.getEnded()) {
                size++;
            }
        }
        for (Contract contract : contracts) {
            contract.update();
        }
        long payment = (infrastructure + production * size);
        budget -= payment;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public long getContractCost() {
        return contractCost;
    }

    public void setContractCost(long contractCost) {
        this.contractCost = contractCost;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    /**
     * calculate the number of active contracts
     * @return number of contracts
     */
    public int numberOfContracts() {
        int size = 0;
        for (Contract contract : contracts) {
            if (!contract.getEnded()) {
                size++;
            }
        }
        return size;
    }
}
