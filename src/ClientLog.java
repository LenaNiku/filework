import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    private String operations = "productNum,amount\n";

    void log(int productNum, int amount){
        operations += (productNum + "," + amount + "\n");
    }
    void exportAsCSV(File csvFile){
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {

            String[] opArrayForCsv = operations.split("\n");
            for (String operation : opArrayForCsv) {
                String[] op = operation.split(",");
                writer.writeNext(op);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}