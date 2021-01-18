package output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({"consumers", "distributors", "energyProducers"})
public final class Output {
    private ArrayList<Consumer> consumers;
    private ArrayList<Distributor> distributors;
    private ArrayList<EnergyProducer> energyProducers;

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<Distributor> distributors) {
        this.distributors = distributors;
    }

    public ArrayList<EnergyProducer> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(ArrayList<EnergyProducer> energyProducers) {
        this.energyProducers = energyProducers;
    }
}
