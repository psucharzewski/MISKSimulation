package psucharzewski.simulation;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class CsvFileGenerator {
    static String[] HEADERS = {"Krok", "Zdrowi", "Odporni", "Chorzy - faza 1", "Chorzy - faza 2", "Populacja", "Chorzy ogółem"};

    public static LinkedList<Integer> getList(int healthy, int resistant, int sickFirst, int sickSecond, int allPopulation, int allSickness) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(healthy);
        list.add(resistant);
        list.add(sickFirst);
        list.add(sickSecond);
        list.add(allPopulation);
        list.add(allSickness);

        return list;
    }

    public static void createCSVFile(Map<Integer, LinkedList<Integer>> data) throws IOException {
        Date date = new Date();
        long time = date.getTime();
        FileWriter out = new FileWriter("wyniki" + time + ".csv");

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            data.forEach((step, list) -> {
                try {
                    printer.printRecord(step, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}