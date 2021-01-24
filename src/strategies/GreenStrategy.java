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
            return price;
        }
        return null;
    }
}
