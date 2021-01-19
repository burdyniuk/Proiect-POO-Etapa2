import common.Constants;
import input.DistributorChanges;
import input.MonthlyUpdates;
import input.Consumers;
import input.Distributors;
import input.Producers;
import input.ReadInput;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import output.Consumer;
import output.Contract;
import output.Distributor;
import output.EnergyProducer;
import strategies.GreenStrategy;
import output.Output;
import output.OutputFactory;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(final String[] args) throws Exception {
        File testInputFile = new File(args[0]);
        File testOutputFile = new File(args[1]);
        OutputFactory outputFactory = OutputFactory.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ReadInput input = mapper.readValue(testInputFile, ReadInput.class);

        ArrayList<Distributor> distributors = new ArrayList<Distributor>();
        ArrayList<Consumer> consumers = new ArrayList<Consumer>();
        ArrayList<EnergyProducer> producers = new ArrayList<EnergyProducer>();
        GreenStrategy greenStrategy = new GreenStrategy();
        PriceStrategy priceStrategy = new PriceStrategy();
        QuantityStrategy quantityStrategy = new QuantityStrategy();

        // creating distributors
        for (Distributors distributor : input.getInitialData().getDistributors()) {
            Distributor dist = (Distributor) outputFactory.createOutput("Distributor");
            dist.setId(distributor.getId());
            dist.setIsBankrupt(false);
            dist.setBudget(distributor.getInitialBudget());
            dist.setContractLength(distributor.getContractLength());
            dist.setInfrastructure(distributor.getInitialInfrastructureCost());
            dist.setEnergyNeededKW(distributor.getEnergyNeededKW());
            dist.setProducerStrategy(distributor.getProducerStrategy());
            distributors.add(dist);
        }

        // creating producers
        for (Producers producer : input.getInitialData().getProducers()) {
            EnergyProducer prod = new EnergyProducer();
            prod.setId(producer.getId());
            prod.setMaxDistributors(producer.getMaxDistributors());
            prod.setPriceKW(producer.getPriceKW());
            prod.setEnergyType(producer.getEnergyType());
            prod.setEnergyPerDistributor(producer.getEnergyPerDistributor());
            producers.add(prod);
        }

        // distributor choosing producers
        for (Distributor distributor : distributors) {
            if (distributor.getProducerStrategy().label.equals("GREEN")) {
                ArrayList<EnergyProducer> prod = greenStrategy
                        .chooseProducer(producers, distributor);
                if (prod != null) {
                    if (distributor.getEnergyNeededKW() < prod.get(0).getEnergyPerDistributor()) {
                        prod.get(0).addDistributor(distributor);
                        prod.get(0).calcCosts();
                        distributor.setProduction(Math.round(Math.floor(prod.get(0).getCost()
                                / Constants.DIVIDE_COST)));
                    } else {
                        prod.get(0).addDistributor(distributor);
                        prod.get(1).addDistributor(distributor);
                        prod.get(0).calcCosts();
                        prod.get(1).calcCosts();
                        long price = Math.round(Math.floor((prod.get(0).getCost()
                                + prod.get(1).getCost()) / Constants.DIVIDE_COST));
                        distributor.setProduction(price);
                    }
                }
            }
            if (distributor.getProducerStrategy().label.equals("PRICE")) {
                ArrayList<EnergyProducer> prod = priceStrategy
                        .chooseProducer(producers, distributor);
                if (prod.get(0).getEnergyPerDistributor() > distributor.getEnergyNeededKW()) {
                    prod.get(0).addDistributor(distributor);
                    distributor.setProduction(Math.round(Math.floor(prod.get(0).getCost()
                            / Constants.DIVIDE_COST)));
                } else {
                    prod.get(0).addDistributor(distributor);
                    prod.get(1).addDistributor(distributor);
                    prod.get(0).calcCosts();
                    prod.get(1).calcCosts();
                    long price = Math.round(Math.floor((prod.get(0).getCost()
                            + prod.get(1).getCost()) / Constants.DIVIDE_COST));
                    distributor.setProduction(price);
                }
            }
            if (distributor.getProducerStrategy().label.equals("QUANTITY")) {
                ArrayList<EnergyProducer> prod = quantityStrategy
                        .chooseProducer(producers, distributor);
                if (prod.get(0).getEnergyPerDistributor() > distributor.getEnergyNeededKW()) {
                    prod.get(0).addDistributor(distributor);
                    prod.get(0).calcCosts();
                    distributor.setProduction(Math.round(Math.floor(prod.get(0).getCost()
                            / Constants.DIVIDE_COST)));
                } else {
                    prod.get(0).addDistributor(distributor);
                    prod.get(1).addDistributor(distributor);
                    prod.get(0).calcCosts();
                    prod.get(1).calcCosts();
                    long price = Math.round(Math.floor((prod.get(0).getCost()
                            + prod.get(1).getCost()) / Constants.DIVIDE_COST));
                    distributor.setProduction(price);
                }
            }
        }

        // consumer choosing contract
        for (Consumers consumer : input.getInitialData().getConsumers()) {
            HashMap<Distributor, Long> dist = Utils.findInitialContract(distributors);
            Consumer cons = (Consumer) outputFactory.createOutput("Consumer");
            cons.setId(consumer.getId());
            cons.setIncome(consumer.getMonthlyIncome());
            cons.setBudget(consumer.getInitialBudget());
            cons.setIsBankrupt(false);
            consumers.add(cons);
            for (Distributor distributor : dist.keySet()) {
                Contract contract = new Contract(consumer.getId(), dist.get(distributor),
                        distributor.getContractLength());
                distributor.getContracts().add(contract);
                distributor.setContractCost(contract.getPrice());
            }
        }


        // Simulation
        for (int i = 0; i <= input.getNumberOfTurns(); i++) {
            //System.out.println("Runda: " + i);
            if (i > 0) {
                // computing contract
                for (Distributor dist : distributors) {
                    long profit = Math.round(Math.floor(Constants.PROFIT_RATE
                                                        * dist.getProduction()));
                    long price = Math.round(Math.floor(dist.getInfrastructure())
                            + dist.getProduction() + profit);
                    for (Contract contract : dist.getContracts()) {
                        if (!contract.getEnded()) {
                            contract.setRemainedContractMonths(dist.getContractLength() - 1);
                            contract.setPrice(price);
                            contract.setLastPrice(contract.getPrice());
                        }
                    }
                }

                // new costs
                MonthlyUpdates updates = input.getMonthlyUpdates().get(i - 1);
                Distributor dist = distributors.get(0);
                if (updates.getDistributorChanges() != null) {
                    for (DistributorChanges cost : updates.getDistributorChanges()) {
                        int id = cost.getId();
                        for (Distributor distributor : distributors) {
                            if (distributor.getId() == id) {
                                dist = distributor;
                            }
                        }
                        dist.setInfrastructure(cost.getInfrastructureCost());
                        //dist.setProduction(cost.getProductionCost());
                        //System.out.println(dist.getInfrastructure() + " " + dist.getProduction());
                    }
                }
            }

            // contracts which ended
            ArrayList<Contract> expiredContracts = new ArrayList<Contract>();
            for (Distributor distributor : distributors) {
                for (Contract contract : distributor.getContracts()) {
                    if (contract.getRemainedContractMonths() == 0) {
                        expiredContracts.add(contract);
                    }
                }
            }

            // compute the price for new contracts
            long smallestPrice = Long.MAX_VALUE;
            Distributor distributor1 = distributors.get(0);
            for (Distributor dist : distributors) {
                if (!dist.getIsBankrupt()) {
                    int size = dist.numberOfContracts();
                    long profit = Math.round(Math.floor(Constants.PROFIT_RATE
                                                        * dist.getProduction()));
                    long price;
                    if (size != 0) {
                        price = Math.round(Math.floor(dist.getInfrastructure() / size)
                                + dist.getProduction() + profit);
                    } else {
                        price = Math.round(Math.floor(dist.getInfrastructure())
                                + dist.getProduction() + profit);
                    }
                    if (price < smallestPrice) {
                        smallestPrice = price;
                        distributor1 = dist;
                    }
                }
            }

            // create new contracts for expired contracts
            for (Contract contract : expiredContracts) {
                for (Consumer consumer : consumers) {
                    if (consumer.getId() == contract.getConsumerId()) {
                        Contract cont = new Contract(consumer.getId(), smallestPrice,
                                distributor1.getContractLength());
                        distributor1.getContracts().add(cont);
                    }
                }
            }

            // end contracts
            for (Distributor distributor : distributors) {
                distributor.getContracts().removeIf(contract
                        -> contract.getRemainedContractMonths() == 0);
            }

            for (Distributor distributor : distributors) {
                if (!distributor.getIsBankrupt()) {
                    for (Contract contract : distributor.getContracts()) {
                        // define for bankrupt consumers - contract ended
                        for (Consumer consumer : consumers) {
                            if (contract.getConsumerId() == consumer.getId()) {
                                if (consumer.getIsBankrupt()) {
                                    contract.setEnded(true);
                                }
                            }
                        }
                    }

                    for (Contract contract : distributor.getContracts()) {
                        Consumer cons = consumers.get(0);
                        for (Consumer consumer : consumers) {
                            if (consumer.getId() == contract.getConsumerId()) {
                                cons = consumer;
                            }
                        }

                        cons.income(); // consumer receive income
                        //System.out.println("Price: " + contract.getPrice());
                        if (!contract.getEnded()) {
                            cons.payContract(contract);
                            // minus one month from contract
                            contract.setRemainedContractMonths(
                                    contract.getRemainedContractMonths() - 1);
                        }
                        // distributor receive the payment
                        if (contract.getPayed()) {
                            distributor.pay(contract);
                        }
                    }
                    // distributor pays costs
                    distributor.payCosts();
                    //System.out.println("Distributor: " + distributor.getBudget());
                }
                if (distributor.getBudget() < 0) {
                    distributor.setIsBankrupt(true);
                }
            }

            if (i != 0) {
                for (EnergyProducer prod : producers) {
                    prod.makeStats(i);
                }
            }

            //if (args[0].contains("basic_1")) {
            for (Distributor dist : distributors) {
                long profit = Math.round(Math.floor(Constants.PROFIT_RATE
                                                    * dist.getProduction()));
                long price = Math.round(Math.floor(dist.getInfrastructure())
                        + dist.getProduction() + profit);
                for (Contract contract : dist.getContracts()) {
                    if (!contract.getEnded()) {
                        contract.setRemainedContractMonths(dist.getContractLength() - 1);
                        contract.setPrice(price);
                        contract.setLastPrice(contract.getPrice());
                    }
                }
            }
            //}


        }

        // delete contracts which are ended
        for (Distributor distributor : distributors) {
            distributor.getContracts().removeIf(Contract::getEnded);
        }

        Output out = new Output();
        out.setDistributors(distributors);
        out.setConsumers(consumers);
        out.setEnergyProducers(producers);

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(testOutputFile, out);
    }
}
