package strategies;

import output.Distributor;
import output.EnergyProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class QuantityStrategy implements ChooseStrategy {

    public QuantityStrategy() {
    }

    @Override
    public ArrayList<EnergyProducer> chooseProducer(ArrayList<EnergyProducer> producers,
                                         Distributor distributor) {
        ArrayList<EnergyProducer> prods = new ArrayList<EnergyProducer>();
        prods.addAll(producers);
        Collections.sort(prods, Comparator.comparing(EnergyProducer::getEnergyPerDistributor)
                         .reversed());
        return prods;
    }
}
