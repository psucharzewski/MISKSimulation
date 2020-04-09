package psucharzewski.simulation;

import java.util.LinkedList;
import java.util.Map;

public class RunSimulation {

    public static void main(String[] arg) throws Exception {
        Simulation simulation = new Simulation();
        SimulationHelper.fillWithStartData();
        Map<Integer, LinkedList<Integer>> resultForCSV = simulation.runSimulation();
        CsvFileGenerator.createCSVFile(resultForCSV);
    }
}
