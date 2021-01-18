package input;

import java.util.ArrayList;

public final class MonthlyUpdates {
    private ArrayList<Consumers> newConsumers;
    private ArrayList<DistributorChanges> distributorChanges;
    private ArrayList<ProducerChanges> producerChanges;

    public ArrayList<Consumers> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final ArrayList<Consumers> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public ArrayList<DistributorChanges> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(ArrayList<DistributorChanges> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public ArrayList<ProducerChanges> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(ArrayList<ProducerChanges> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
