package airport.simulation;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

class OpenCSVReader {
    private String[] data;

    String[] readCSV(String fileName, Integer id) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get("src", "main", "resources", fileName));
                CSVReader csvReader = new CSVReader(reader, ';', '\'', id + 1)
        ) {
            // Reading Records One by One in a String array
            if ((data = csvReader.readNext()) != null) {
                csvReader.close();
                return data;
            }
            else {
                return null;
            }
        }
    }
}