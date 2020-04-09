package psucharzewski.simulation;

import java.util.Random;

/**
 * @author
 */
public class DataForOneAgeOfAnimal {
    private AgeType kindOfAnimal;
    private int amountOfHealthy;
    private int amountOfResistant;
    private int amountOfFirstPhaseSick;
    private int amountOfSecondPhaseSick;

    public DataForOneAgeOfAnimal(DataForOneAgeOfAnimal dataForOneAgeOfAnimal) {
        this.kindOfAnimal = dataForOneAgeOfAnimal.getKindOfAnimal();
        this.amountOfHealthy = dataForOneAgeOfAnimal.getAmountOfHealthy();
        this.amountOfResistant = dataForOneAgeOfAnimal.getAmountOfResistant();
        this.amountOfFirstPhaseSick = dataForOneAgeOfAnimal.getAmountOfFirstPhaseSick();
        this.amountOfSecondPhaseSick = dataForOneAgeOfAnimal.getAmountOfSecondPhaseSick();
    }

    public DataForOneAgeOfAnimal(AgeType kindOfAnimal, int amountOfHealthy, int amountOfHealthyAndResistant, int amountOfFirstPhaseSick, int amountOfSecondPhaseSick) {
        this.kindOfAnimal = kindOfAnimal;
        this.amountOfHealthy = amountOfHealthy;
        this.amountOfResistant = amountOfHealthyAndResistant;
        this.amountOfFirstPhaseSick = amountOfFirstPhaseSick;
        this.amountOfSecondPhaseSick = amountOfSecondPhaseSick;
    }

    public void setNewData(int amountOfHealthy, int amountOfResistant, int amountOfFirstPhaseSick, int amountOfSecondPhaseSick) {
        this.amountOfHealthy = amountOfHealthy;
        this.amountOfResistant = amountOfResistant;
        this.amountOfFirstPhaseSick = amountOfFirstPhaseSick;
        this.amountOfSecondPhaseSick = amountOfSecondPhaseSick;
    }

    public ResultOfCalculation makeCalculations(double meetSickAnimalProbability) {
        subtractDeathByAgeCase();
        int newPopulation = calculateNewPopulation();
        int amountOfSickInNewPop = calculateAmountOfSickAnimalInNewPopulation(newPopulation);
        int morbidity = calculateMorbidity(meetSickAnimalProbability);
        int deathCases = calculateDeathCases();

        return new ResultOfCalculation(newPopulation, amountOfSickInNewPop, morbidity, deathCases);
    }

    public AgeType getKindOfAnimal() {
        return kindOfAnimal;
    }

    public int getAllAnimalsOfThisAge() {
        return amountOfHealthy + amountOfFirstPhaseSick + amountOfSecondPhaseSick;
    }

    public int getAmountOfHealthy() {
        return amountOfHealthy;
    }

    public int getAmountOfResistant() {
        return amountOfResistant;
    }

    public int getAmountOfFirstPhaseSick() {
        return amountOfFirstPhaseSick;
    }

    public int getAmountOfSecondPhaseSick() {
        return amountOfSecondPhaseSick;
    }

    public int getAmountOfSickAnimals() {
        return this.amountOfFirstPhaseSick + this.amountOfSecondPhaseSick;
    }

    private int calculateNewPopulation() {
        return roundDoubleNumbersAndCastToInteger(getAllAnimalsOfThisAge() * getRandomBetweenRateAndDeviation(kindOfAnimal.getNaturalIncreaseRate(),
                kindOfAnimal.getDeviatonForIncreaseRateRate()) / 100.0);
    }

    private int calculateAmountOfSickAnimalInNewPopulation(int newPopulation) {
        double sickRateInCurrentPopulation = (double) (amountOfFirstPhaseSick + amountOfSecondPhaseSick) / getAllAnimalsOfThisAge();
        if (newPopulation < 0) calculateNewPopulation();
        return roundDoubleNumbersAndCastToInteger(newPopulation * sickRateInCurrentPopulation);
    }

    private int calculateMorbidity(double meetSickAnimalProbability) {
        return roundDoubleNumbersAndCastToInteger(amountOfHealthy * meetSickAnimalProbability);
    }

    private int calculateDeathCases() {
        double percent = getRandomBetweenRateAndDeviation(kindOfAnimal.getMortalityRate(),
                kindOfAnimal.getDeviationForMortalityRate()) / 100.0;

        return roundDoubleNumbersAndCastToInteger(amountOfSecondPhaseSick * percent);
    }

    private void subtractDeathByAgeCase() {
        switch (this.kindOfAnimal) {
            case FIVE:
                subtractDeath(0.33);
                break;
            case SIX:
                subtractDeath(0.5);
                break;
            case SEVEN:
                subtractDeath(1);
                break;
            default:
        }
    }

    private void subtractDeath(double percent) {
        int amountOfHealthNotResistantGroup = (this.amountOfHealthy - this.amountOfResistant);
        int newAmountOfHealthNotResistantGroup = roundDoubleNumbersAndCastToInteger(amountOfHealthNotResistantGroup -
                amountOfHealthNotResistantGroup * percent);
        int amountOfResistant = roundDoubleNumbersAndCastToInteger(this.amountOfResistant - this.amountOfResistant * percent);

        this.amountOfHealthy = newAmountOfHealthNotResistantGroup + amountOfResistant;
        this.amountOfResistant = amountOfResistant;
        this.amountOfFirstPhaseSick = roundDoubleNumbersAndCastToInteger(this.amountOfFirstPhaseSick - this.amountOfFirstPhaseSick * percent);
        this.amountOfSecondPhaseSick = roundDoubleNumbersAndCastToInteger(this.amountOfSecondPhaseSick - this.amountOfSecondPhaseSick * percent);
    }

    private int getRandomBetweenRateAndDeviation(int rate, int deviation) {
        int min = rate - deviation;
        int max = rate + deviation;

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private int roundDoubleNumbersAndCastToInteger(double number) {
        return (int) Math.floor(number);
    }
}