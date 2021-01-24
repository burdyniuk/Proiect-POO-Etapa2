package strategies;

import output.Distributor;
import output.EnergyProducer;

import java.util.ArrayList;

public interface ChooseStrategy {
    /**
     * Function to choose the producer which response to the strategy.
     * @param producers, distributors
     * @return a list of producers
     */
    ArrayList<EnergyProducer> chooseProducer(ArrayList<EnergyProducer> producers,
                                             Distributor distributor);
}
