package cherish.backend.common.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * submodule csvReader가 import 안되는 이슈로 임의 생성하였습니다.
 * 이슈 해결 후 삭제 할 예정입니다.
 */
public class csvReader {

    //filePath 는 재가공된 csv 파일이 저장된 경로를 입력해주세요
    public static List<String[]> readCsv(String filePath, String columnName) throws IOException, CsvException {

        List<String[]> filteredDataList;

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {

            String[] headerRow = csvReader.readNext();
            int columnIndex = findColumnIndex(columnName, headerRow);

            if (columnIndex == -1) {
                throw new IllegalArgumentException("Invalid column name!");
            }

            List<String[]> dataList = csvReader.readAll();
            filteredDataList = dataList.stream().map(row -> new String[] { row[columnIndex] }).collect(Collectors.toList());
        }

        return filteredDataList;
    }

    private static int findColumnIndex(String columnName, String[] headerRow) {
        for (int i = 0; i < headerRow.length; i++) {
            if (headerRow[i].equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    public static List<String[]> read(String columnName) throws IOException, CsvException {

        List<String[]> filteredDataList;

        try (InputStream inputStream = csvReader.class.getResourceAsStream("/resources/(Full Stack 설문 응답지) - 데이터 .csv");
             CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] headerRow = csvReader.readNext();
            int columnIndex = findColumnIndex(columnName, headerRow);

            if (columnIndex == -1) {
                throw new IllegalArgumentException("Invalid column name!");
            }

            List<String[]> dataList = csvReader.readAll();
            filteredDataList = dataList.stream().map(row -> new String[] { row[columnIndex] }).collect(Collectors.toList());
        }

        return filteredDataList;
    }

    public static List<String[]> readCSV(String filePath) throws IOException, CsvException {

        List<String[]> dataList;

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {

            dataList = csvReader.readAll();
        }

        return dataList;
    }
}