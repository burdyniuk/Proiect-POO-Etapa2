package utils;

import common.Constants;
import output.Distributor;

import java.util.ArrayList;
import java.util.HashMap;

public final class Utils {

    /**
     * choose the cheaper contract
     * @param distributors
     * @return
     */
    public static HashMap<Distributor, Long> findInitialContract(
            final ArrayList<Distributor> distributors) {
        long smallestPrice = Long.MAX_VALUE;
        Distributor distributor = distributors.get(0);
        HashMap<Distributor, Long> distributorMap = new HashMap<Distributor, Long>();
        for (Distributor dist : distributors) {
            long profit = Math.round(Math.floor(Constants.PROFIT_RATE
                                                * dist.getProduction()));
            long price;
            price = dist.getInfrastructure() + dist.getProduction() + profit;
            if (price < smallestPrice) {
                smallestPrice = price;
                distributor = dist;
            }
        }
        distributorMap.put(distributor, smallestPrice);
        return distributorMap;
    }
}
