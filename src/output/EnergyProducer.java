package output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import entities.EnergyType;
import java.util.ArrayList;

@JsonPropertyOrder({"id", "maxDistributors", "priceKW", "energyType", "energyPerDistributor",
                    "monthlyStats"})
@JsonIgnoreProperties({"distributorCount", "cost"})
public final class EnergyProducer {
    private int id;
    private int maxDistributors;
    private float priceKW;
    private EnergyType energyType;
    private int energyPerDistributor;
    private ArrayList<MonthlyStats> monthlyStats = new ArrayList<MonthlyStats>();
    private int distributorCount = 0;
    private ArrayList<Distributor> distributors = new ArrayList<Distributor>();
    private float cost;

    public EnergyProducer() {
    }

    /**
     * Add a distributor to list of producer.
     * @param dist
     */
    public void addDistributor(Distributor dist) {
        this.distributors.add(dist);
        distributorCount++;
    }

    /**
     * Remove the distributor from producer's list.
     * @param dist
     */
    public void removeDistributor(Distributor dist) {
        this.distributors.remove(dist);
        distributorCount--;
    }

    public int getDistributorCount() {
        return distributorCount;
    }

    public void setDistributorCount(int distributorCount) {
        this.distributorCount = distributorCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(float priceKW) {
        this.priceKW = priceKW;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public ArrayList<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * Calculate costs of resources.
     */
    public void calcCosts() {
        this.cost = priceKW * energyPerDistributor;
    }

    /**
     * Make stats for this month
     * @param monthNumber - number of turn
     */
    public void makeStats(int monthNumber) {
        MonthlyStats month = new MonthlyStats();
        month.setMonth(monthNumber);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        if (distributors.size() != 0) {
            for (Distributor dist : distributors) {
                ids.add(dist.getId());
            }
        }
        month.setDistributorsIds(ids);
        monthlyStats.add(month);
    }
}
