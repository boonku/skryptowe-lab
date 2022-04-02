import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Aggregate {
    public static final String COLUMN   = "column";
    public static final String ARG      = "func";
    public static final String COLUMN_DELIMITER = "\t";

    public static String getValue(List<String> args, String argument) {
        return args.stream().
                filter(arg -> arg.contains(String.format("--%s=", argument)))
                .findFirst()
                .map(arg -> arg.split("=")[1])
                .orElse(null);
    }

    public static int getColumnNumber(String value) {
        int number = 0;
        try {
            number = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            System.err.println("Invalid column value");
            System.exit(1);
        }
        return number;
    }

    public static List<String> readLines() {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    public static List<String> getColumn(List<String> lines, int column) {
        // if column index is out of bounds, return empty
        // change it to separate method which will stderr error(, and exit?)
        if (column < 0 || column >= lines.get(0).split(COLUMN_DELIMITER).length) {
            return List.of();
        }
        return lines.stream()
//                .skip(1)
                .map(line -> line.split(COLUMN_DELIMITER)[column - 1])
                .collect(Collectors.toList());
    }


    private static Number aggregate(List<String> data, String function) {
        Number result = null;
        switch (function) {
            case "min" -> result = min(data);
            case "max" -> result = max(data);
            case "sum" -> result = sum(data);
            case "avg" -> result = average(data);
            case "count" -> result = count(data);
            // add two more
        }
        if (result instanceof Double) {
            double d = (Double) result;
            // if result has no decimal places (is int) return it as int
            if (d % 1 == 0) {
                return Math.round(d);
            }
            // round the result to 2 decimal places
            return Math.round(d * 100.0) / 100.0;
        }
        return result;
    }

    private static Double min(List<String> data) {
        return data.stream().mapToDouble(Double::parseDouble).min().getAsDouble();
    }

    private static Double max(List<String> data) {
        return data.stream().mapToDouble(Double::parseDouble).max().getAsDouble();
    }

    private static Double sum(List<String> data) {
        return data.stream().mapToDouble(Double::parseDouble).sum();
    }

    private static Double average(List<String> data) {
        double sum = data.stream().mapToDouble(Double::parseDouble).sum();
        return sum / data.size();
    }

    private static Integer count(List<String> data) {
        return data.size();
    }

    public static void main(String[] args) {
        List<String> systemArgs = List.of(args);
        String function = getValue(systemArgs, ARG);
        int columnNumber = getColumnNumber(getValue(systemArgs, COLUMN));
        List<String> lines = readLines();
        List<String> data = getColumn(lines, columnNumber);
        if (function == null) {
            System.err.println("No aggregation function given!");
            System.exit(1);
        }
        Number result = aggregate(data, function);
        System.out.println("result: " + result);
    }
}
