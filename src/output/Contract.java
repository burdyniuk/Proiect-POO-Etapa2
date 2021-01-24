package output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"payed", "ended", "lastPrice", "penalised"})
public final class Contract implements Observator {
    private int consumerId;
    private long price;
    private int remainedContractMonths;
    private long lastPrice;
    private boolean payed = true;
    private boolean ended = false;

    public Contract(final int consumerId, final long price, final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.lastPrice = price;
        this.remainedContractMonths = remainedContractMonths;
    }



    public long getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(final long lastPrice) {
        this.lastPrice = lastPrice;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(final long price) {
        this.price = price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public boolean getPayed() {
        return payed;
    }

    public void setPayed(final boolean payed) {
        this.payed = payed;
    }

    public boolean getEnded() {
        return ended;
    }

    public void setEnded(final boolean ended) {
        this.ended = ended;
    }

    @Override
    public void update() {
        if (remainedContractMonths == 0) {
            ended = true;
        }
        if (!ended) {
            remainedContractMonths--;
        }
    }
}
