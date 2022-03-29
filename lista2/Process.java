import java.util.*;
import java.util.stream.Collectors;

public class Process {
    // argument names
    public static final String IGNORE_FIRST = "ignorefirst";
    public static final String IGNORE_LAST  = "ignorelast";
    public static final String DELIMITER    = "delimiter";
    public static final String SEPARATOR    = "separator";
    public static final String PROJECT      = "project";
    public static final String SELECT       = "select";

    public static final String PROJECT_ARG_DELIMITER = ",";
    public static final String PROJECT_DELIMITER = "\t";
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_SEPARATOR = "\t";

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
                                           String select) {
        return lines.stream()
                .map(line -> line.substring(ignoreFirst, line.length() - ignoreLast))
                .map(line -> line.replaceAll(delimiter, "\t"))
                .filter(line -> line.contains(select))
                .collect(Collectors.toList());
    }

    public static void displayColumns(List<String> lines, List<Integer> project, String separator) {
        // if project arg is empty, get all columns
        if (project.isEmpty()) {
            int numberOfColumns = lines.get(0).split(PROJECT_DELIMITER).length;
            for (int i = 0; i < numberOfColumns; i++) {
                project.add(i + 1);
            }
        }
        for (String line: lines) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> columns = List.of(line.split(PROJECT_DELIMITER));
            for (Integer index: project) {
                if (index - 1 < columns.size()) {
                    stringBuilder.append(columns.get(index - 1));
                    stringBuilder.append(separator);
                }
            }
            if (stringBuilder.length() == 0) {
                System.exit(1);
            } else {
                stringBuilder.setLength(stringBuilder.length() - separator.length());
            }
            System.out.println(stringBuilder);
        }
    }

    public static void main(String[] args) {
        List<String> systemArgs = List.of(args);

        int ignoreFirst = getIgnoreValue(getValue(systemArgs, IGNORE_FIRST));
        int ignoreLast = getIgnoreValue(getValue(systemArgs, IGNORE_LAST));
        String delimiter = getValue(systemArgs, DELIMITER);
        delimiter = delimiter == null ? DEFAULT_DELIMITER : delimiter;
        String separator = getValue(systemArgs, SEPARATOR);
        separator = separator == null ? DEFAULT_SEPARATOR : separator;
        List<Integer> project = getProjectValues(getValue(systemArgs, PROJECT));
        String select = getValue(systemArgs, SELECT);
        select = select == null ? "" : select;

        List<String> lines = readLines();
        ifEmptyExitWithCode(lines, 2);
        lines = filterLines(lines, ignoreFirst, ignoreLast, delimiter, select);
        ifEmptyExitWithCode(lines, 1);
        displayColumns(lines, project, separator);
    }

    private static void ifEmptyExitWithCode(List<String> lines, int code) {
        if (lines.isEmpty()) {
            System.exit(code);
        }
    }
}
