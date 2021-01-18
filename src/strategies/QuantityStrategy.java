package strategies;

import output.EnergyProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class QuantityStrategy implements ChooseStrategy {

    public QuantityStrategy() {
    }

    @Override
    public EnergyProducer chooseProducer(ArrayList<EnergyProducer> producers) {
        ArrayList<EnergyProducer> prods = new ArrayList<EnergyProducer>();
        prods.addAll(producers);
        Collections.sort(prods, Comparator.comparing(EnergyProducer::getEnergyPerDistributor)
                         .reversed());
        return prods.get(0);
    }
}
