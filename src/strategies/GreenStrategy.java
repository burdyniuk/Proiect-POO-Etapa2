package strategies;

import output.Distributor;
import output.EnergyProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class GreenStrategy implements ChooseStrategy {

    public GreenStrategy() {
    }

    @Override
    public ArrayList<EnergyProducer> chooseProducer(ArrayList<EnergyProducer> producers,
                                         Distributor distributor) {
        ArrayList<EnergyProducer> prods = new ArrayList<EnergyProducer>();
        for (EnergyProducer producer : producers) {
            if (producer.getEnergyType().isRenewable()) {
                prods.add(producer);
            }
        }
        if (prods.size() == 0) {
            prods = producers;
        }

        ArrayList<EnergyProducer> price = (ArrayList<EnergyProducer>) prods
                .stream().sorted(Comparator.comparing(EnergyProducer::getPriceKW).reversed())
                .collect(Collectors.toList());
        Collections.sort(price, Comparator.comparing(EnergyProducer::getEnergyPerDistributor));
        if (price.size() != 0) {
            //if (price.get(0).getEnergyPerDistributor())
            return price;
        }
        return null;
    }
}

class SortingGreen implements Comparator<EnergyProducer> {
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
