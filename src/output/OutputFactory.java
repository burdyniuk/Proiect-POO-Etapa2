package output;

public class OutputFactory {
    private static OutputFactory instance = null;

    public OutputFactory() {
    }

    /**
     * Get instance of output factory
     * @return OutputFactory
     */
    public static OutputFactory getInstance() {
        if (instance == null) {
            instance = new OutputFactory();
        }
        return instance;
    }

    /**
     * Create element of Output
     * @param type
     * @return Output element
     */
    public static OutElement createOutput(final String type) {
        OutElement elem = null;

        switch (type) {
            case "Distributor":
                elem = new Distributor();
                break;
            case "Consumer":
                elem = new Consumer();
                break;
        }

        return elem;
    }
}
