import java.util.*;
import java.util.stream.Collectors;

public class Process {
    public static final String IGNORE_FIRST = "ignorefirst";
    public static final String IGNORE_LAST  = "ignorelast";
    public static final String DELIMITER    = "delimiter";
    public static final String SEPARATOR    = "separator";
    public static final String PROJECT      = "project";
    public static final String SELECT       = "select";

    public static final String PROJECT_ARG_DELIMITER = ",";

    // returns string value of given argument
    public static String getValue(List<String> args, String argument) {
        return args.stream().
                filter(arg -> arg.contains(String.format("--%s=", argument)))
                .findFirst()
                .map(arg -> arg.split("=")[1])
                .orElse(null);
    }

    public static int getIgnoreValue(String value) {
        int number = 0;
        try {
            number = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {

        }
        return number;
    }

    public static List<Integer> getProjectValues(String value) {
        List<Integer> values = new ArrayList<>();
        if (value != null) {
            values = Arrays.stream(value.split(PROJECT_ARG_DELIMITER))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        return values;
    }

    public static List<String> readLines() {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    public static List<String> filterLines(List<String> lines,
                                           int ignoreFirst,
                                           int ignoreLast,
                                           String delimiter,
                                           String separator,
                                           String select) {
        return lines.stream()
                .skip(ignoreFirst)
                .limit(lines.size() - ignoreLast)
                .map(line -> line.replaceAll(delimiter, "\t"))
                .map(line -> line.replaceAll("\t", separator))
                .filter(line -> line.contains(select))
                .collect(Collectors.toList());
    }

    public static void displayColumns(List<String> lines, List<Integer> project, String separator) {
        if (project.isEmpty()) {
            int numberOfColumns = lines.get(0).split(separator).length;
            for (int i = 0; i < numberOfColumns; i++) {
                project.add(i + 1);
            }
        }
        for (String line: lines) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> columns = List.of(line.split(separator));
            for (Integer index: project) {
                if (index - 1 < columns.size()) {
                    stringBuilder.append(columns.get(index - 1));
                    stringBuilder.append("\t");
                }
            }
            if (stringBuilder.length() == 0) {
                System.exit(1);
            } else {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
            System.out.println(stringBuilder);
        }
    }

    public static void main(String[] args) {
        List<String> systemArgs = List.of(args);
        int ignoreFirst = getIgnoreValue(getValue(systemArgs, IGNORE_FIRST));
        int ignoreLast = getIgnoreValue(getValue(systemArgs, IGNORE_LAST));
        String delimiter = getValue(systemArgs, DELIMITER);
        String separator = getValue(systemArgs, SEPARATOR);
        List<Integer> project = getProjectValues(getValue(systemArgs, PROJECT));
        String select = getValue(systemArgs, SELECT);
        if (select == null) {
            select = "";
        }
        List<String> lines = readLines();
        if (lines.isEmpty()) {
            System.exit(2);
        }
        lines = filterLines(lines, ignoreFirst, ignoreLast, delimiter, separator, select);
        if (lines.isEmpty()) {
            System.exit(1);
        }
        displayColumns(lines, project, separator);
    }
}
