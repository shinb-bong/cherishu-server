package cherish.backend.test.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class DataRow {
    private String item;
    private String brand;
    private String category;
    private Set<String> categoryChild;
    private int price;
    private String brandUrl;
    private String kakaoUrl;
    private String coupangUrl;
    private String naverUrl;
    private String description;
    private Set<String> job;
    private Set<String> jobChild;
    private int minAge;
    private int maxAge;
    private Set<String> situation;
    private Set<String> emotion;
    private Set<String> gender;
    private Set<String> preference;
    private Set<String> type;
    private Set<String> relation;

    private static String toEmpty(String original) {
        return original == null || original.equals("-") ? "" : original;
    }

    public static DataRow of(String[] row) {
        return new DataRow(
            row[0],
            row[1],
            row[2],
            Arrays.stream(row[3].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Integer.parseInt(row[4]),
            row[5],
            row[6],
            row[7],
            row[8],
            row[9],
            Arrays.stream(row[10].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Arrays.stream(row[11].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Integer.parseInt(row[12]),
            Integer.parseInt(row[13]),
            Arrays.stream(row[14].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Arrays.stream(row[15].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Arrays.stream(row[16].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Arrays.stream(row[17].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Arrays.stream(row[18].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet()),
            Arrays.stream(row[19].split(",")).map(StringUtils::deleteWhitespace).collect(Collectors.toSet())
        );
    }
}
