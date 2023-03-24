package cherish.backend.common.service;

import cherish.backend.item.model.Item;
import cherish.backend.item.repository.ItemRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class csvReaderTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testReadCSV() throws IOException, CsvException {
        List<String[]> itemName = csvReader.read("itemName");


//        List<String[]> itemName = csvReader.readCsv("C:\\Users\\00003807\\Desktop\\(Full Stack 설문 응답지) - 데이터 .csv", "itemName");
//        List<String[]> csv = csvReader.readCSV("C:\\Users\\00003807\\Desktop\\(Full Stack 설문 응답지) - 데이터 .csv");

        for (String[] data : itemName) {
            System.out.println(data[0]);
            break;
        }

    }


}
