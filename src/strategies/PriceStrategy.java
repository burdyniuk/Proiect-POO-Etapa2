package strategies;

import output.EnergyProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class PriceStrategy implements ChooseStrategy {

    public PriceStrategy() {
    }

    @Override
    public EnergyProducer chooseProducer(ArrayList<EnergyProducer> producers) {
        ArrayList<EnergyProducer> prods = new ArrayList<EnergyProducer>();
        for (EnergyProducer prod : producers) {
            prod.calcCosts();
        }
        prods.addAll(producers);
        Collections.sort(prods, Comparator.comparing(EnergyProducer::getCost));
        return prods.get(0);
    }
}
