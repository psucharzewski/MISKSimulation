/* @(#) $$Id$$
 *
 * Copyright (c) 2000-2020 Comarch SA All Rights Reserved. Any usage,
 * duplication or redistribution of this software is allowed only according to
 * separate agreement prepared in written between Comarch and authorized party.
 */
package psucharzewski.simulation;


public class ResultOfCalculation {
    private int newPopulation;
    private int amountOfSickInNewPopulation;
    private int morbidity;
    private int deathCases;


    public ResultOfCalculation(int newPopulation, int amountOfSickInNewPopulation, int morbidity, int deathCases) {
        this.newPopulation = newPopulation;
        this.amountOfSickInNewPopulation = amountOfSickInNewPopulation;
        this.morbidity = morbidity;
        this.deathCases = deathCases;
    }

    public int getNewPopulation() {
        return newPopulation;
    }

    public int getAmountOfSickInNewPopulation() {
        return amountOfSickInNewPopulation;
    }

    public int getMorbidity() {
        return morbidity;
    }

    public int getDeathCases() {
        return deathCases;
    }
}