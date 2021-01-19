package strategies;

import output.Distributor;
import output.EnergyProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class PriceStrategy implements ChooseStrategy {

    public PriceStrategy() {
    }

    @Override
    public ArrayList<EnergyProducer> chooseProducer(ArrayList<EnergyProducer> producers,
                                         Distributor distributor) {
        ArrayList<EnergyProducer> prods = new ArrayList<EnergyProducer>();
        for (EnergyProducer prod : producers) {
            prod.calcCosts();
        }
        prods.addAll(producers);
        Collections.sort(prods, new SortingPrice().reversed());
        return prods;
    }
}

class SortingPrice implements Comparator<EnergyProducer> {
    @Override
    public int compare(EnergyProducer o1, EnergyProducer o2) {
        int x = 0;
        float c = o1.getPriceKW() - o2.getPriceKW();
        if (c < 0) {
            x = 1;
        } else if (c > 0) {
            x = -1;
        } else if (c == 0) {
            c = o1.getEnergyPerDistributor() - o2.getEnergyPerDistributor();
            if (c == 0) {
                x = 0;
            } else if (c < 0) {
                x = -1;
            } else if (c > 0) {
                x = 1;
            }
        }

        return x;
    }
}
