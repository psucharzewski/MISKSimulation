package psucharzewski.simulation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
public class SimulationHelper {
    public static Map<AgeType, DataForOneAgeOfAnimal> currentSituation = new HashMap<>();
    public static Map<AgeType, ResultOfCalculation> resultOfCalculationMap = new HashMap<>();

    public static void fillWithStartData() {
        currentSituation.put(AgeType.ONE, new DataForOneAgeOfAnimal(AgeType.ONE, 80, 20, 10, 10));
        currentSituation.put(AgeType.TWO, new DataForOneAgeOfAnimal(AgeType.TWO, 90, 30, 10, 20));
        currentSituation.put(AgeType.THREE, new DataForOneAgeOfAnimal(AgeType.THREE, 80, 10, 5, 10));
        currentSituation.put(AgeType.FOUR, new DataForOneAgeOfAnimal(AgeType.FOUR, 70, 10, 10, 5));
        currentSituation.put(AgeType.FIVE, new DataForOneAgeOfAnimal(AgeType.FIVE, 40, 20, 7, 3));
        currentSituation.put(AgeType.SIX, new DataForOneAgeOfAnimal(AgeType.SIX, 15, 5, 6, 4));
        currentSituation.put(AgeType.SEVEN, new DataForOneAgeOfAnimal(AgeType.SEVEN, 10, 0, 0, 3));
    }

    public static int getAmountOfSickInFirstPhaseForNewPopulation() {
        return resultOfCalculationMap.values().stream()
                .mapToInt(ResultOfCalculation::getAmountOfSickInNewPopulation)
                .sum();
    }

    public static int getNewPopulation() {
        return resultOfCalculationMap.values().stream()
                .mapToInt(ResultOfCalculation::getNewPopulation)
                .sum();
    }

    public static int getAllSickAnimals() {
        return SimulationHelper.currentSituation.values().stream()
                .mapToInt(DataForOneAgeOfAnimal::getAmountOfSickAnimals)
                .sum();
    }

    public static int getAllAnimals() {
        return SimulationHelper.currentSituation.values().stream()
                .mapToInt(DataForOneAgeOfAnimal::getAllAnimalsOfThisAge)
                .sum();
    }

    public static int getAllHealthy() {
        return SimulationHelper.currentSituation.values().stream()
                .mapToInt(DataForOneAgeOfAnimal::getAmountOfHealthy)
                .sum();
    }

    public static int getAllResistant() {
        return SimulationHelper.currentSituation.values().stream()
                .mapToInt(DataForOneAgeOfAnimal::getAmountOfResistant)
                .sum();
    }

    public static int getAllSickInFirstPhase() {
        return SimulationHelper.currentSituation.values().stream()
                .mapToInt(DataForOneAgeOfAnimal::getAmountOfFirstPhaseSick)
                .sum();
    }

    public static int getAllSickInSecondPhase() {
        return SimulationHelper.currentSituation.values().stream()
                .mapToInt(DataForOneAgeOfAnimal::getAmountOfSecondPhaseSick)
                .sum();
    }

    public static boolean isZeroNewInfections() {
        return SimulationHelper.resultOfCalculationMap.values().stream()
                .mapToInt(ResultOfCalculation::getMorbidity)
                .sum() <= 0;
    }
}

