package output;

public interface OutElement {
    /**
     * set id of element
     * @param id
     */
    void setId(int id);

    /**
     * get id of element
     * @return id
     */
    int getId();

    /**
     * set if is bankrupt
     * @param bankrupt
     */
    void setIsBankrupt(boolean bankrupt);

    /**
     * get if is bankrupt
     * @return bankrupt
     */
    boolean getIsBankrupt();

    /**
     * get budget of element
     * @return
     */
    long getBudget();

    /**
     * set budget for element
     * @param budget
     */
    void setBudget(long budget);
}
