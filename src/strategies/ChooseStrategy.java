package strategies;

import output.EnergyProducer;

import java.util.ArrayList;

public interface ChooseStrategy {
    /**
     * Function to choose the producer which response to the strategy.
     * @param producers
     * @return
     */
    EnergyProducer chooseProducer(ArrayList<EnergyProducer> producers);
}
