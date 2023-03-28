package cherish.backend.test.data;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Slf4j
@Component
public class DataParser {

    public void read() {
        try (CSVReader csvReader = new CSVReader(new FileReader("data.csv"))) {

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
