package psucharzewski.simulation;

import java.util.*;

/**
 * @author
 */
public class Simulation {


    private Map<Integer, LinkedList<Integer>> resultForCSV = new LinkedHashMap<>();

    public Map<Integer, LinkedList<Integer>> runSimulation() throws Exception {
        int i = 0;
        addToResultReport(i);
        do {
            calculateOneSimulationStep(i);
            i++;
        } while(!SimulationHelper.isZeroNewInfections());
        calculateOneSimulationStep(i++);

        System.out.println("Brak nowych zakażonych po " + i + " jednostkach czasu");
        System.out.println("Liczba ocalałych: " + SimulationHelper.getAllAnimals());

        return resultForCSV;
    }

    private void printResult(){
        SimulationHelper.currentSituation.values().stream()
                .sorted(Comparator.comparing(a -> a.getKindOfAnimal().getId()))
                .forEach(age -> {
                    String result = "Grupa wiekowa: " + age.getKindOfAnimal().getId() + "  Zdrowych: " + age.getAmountOfHealthy() +
                            "   W tym odpornych: " + age.getAmountOfResistant() + "  Chorych: " + age.getAmountOfSickAnimals() +
                            "(Pierwsza faza: " + age.getAmountOfFirstPhaseSick() + ", Druga faza: " + age.getAmountOfSecondPhaseSick() + ")";

                    System.out.println(result);
                });
    }

    public void calculateOneSimulationStep(int i) throws Exception {
        System.out.println((i + 1) + " etap");

        Map<AgeType, DataForOneAgeOfAnimal> tmp = copy(SimulationHelper.currentSituation);
        double probabilityOfInfection = (double) SimulationHelper.getAllSickAnimals() / SimulationHelper.getAllAnimals();

        for (AgeType ageType : AgeType.values()) {
            SimulationHelper.resultOfCalculationMap
                    .put(ageType, SimulationHelper.currentSituation.get(ageType).makeCalculations(probabilityOfInfection));
        }

        calculateOneSimulationStepForFirstAgeOnly();
        for(int j=2; j<=7; j++){
            calculateOneSimulationStepForOneOfAnimalAge(tmp, j);
        }

        addToResultReport(i + 1);
        printResult();
        System.out.println("*************************************");
    }
    private void addToResultReport(int step){
        int healthy = SimulationHelper.getAllHealthy();
        int resistant = SimulationHelper.getAllResistant();
        int sickFirst = SimulationHelper.getAllSickInFirstPhase();
        int sickSecond = SimulationHelper.getAllSickInSecondPhase();
        int allPopulation = SimulationHelper.getAllAnimals();
        int allSick = SimulationHelper.getAllSickAnimals();

        resultForCSV.put(step, CsvFileGenerator.getList(healthy, resistant, sickFirst, sickSecond, allPopulation, allSick));
    }

    public void calculateOneSimulationStepForFirstAgeOnly() {
        int newPopulation = SimulationHelper.getNewPopulation();
        int amountOfSickInFirstPhaseForNewPopulation = SimulationHelper.getAmountOfSickInFirstPhaseForNewPopulation();
        int amountOfHealthyInNewPopulation = newPopulation - amountOfSickInFirstPhaseForNewPopulation;

        SimulationHelper.currentSituation.get(AgeType.ONE)
                .setNewData(amountOfHealthyInNewPopulation, 0, amountOfSickInFirstPhaseForNewPopulation, 0);
    }

    public void calculateOneSimulationStepForOneOfAnimalAge(Map<AgeType, DataForOneAgeOfAnimal> current, int ageType)
            throws Exception {

        if (ageType > 7 || ageType < 2) throw new Exception("Wrong age value");
        AgeType next = AgeType.getAgeTypeById(ageType);
        AgeType previous = AgeType.getAgeTypeById(ageType - 1);

        ResultOfCalculation result = SimulationHelper.resultOfCalculationMap.get(previous);
        DataForOneAgeOfAnimal previousSituation = current.get(previous);

        int newHealthy = previousSituation.getAmountOfHealthy();
        int recovered = previousSituation.getAmountOfSecondPhaseSick() - result.getDeathCases();
        int morbidity = result.getMorbidity();
        int firstPhase = previousSituation.getAmountOfFirstPhaseSick();
        SimulationHelper.currentSituation.get(next)
                .setNewData(newHealthy + recovered - morbidity, recovered, morbidity, firstPhase);
    }

    public static HashMap<AgeType, DataForOneAgeOfAnimal> copy(Map<AgeType, DataForOneAgeOfAnimal> original) {
        HashMap<AgeType, DataForOneAgeOfAnimal> copy = new HashMap<>();
        for (Map.Entry<AgeType, DataForOneAgeOfAnimal> entry : original.entrySet()) {
            copy.put(entry.getKey(), new DataForOneAgeOfAnimal(entry.getValue()));
        }
        return copy;
    }
}
