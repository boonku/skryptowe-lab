import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Aggregate {
    public static final String COLUMN   = "column";
    public static final String ARG      = "func";

    public static final String COLUMN_DELIMITER = "\t";

    public static void main(String[] args) {
        List<String> systemArgs = List.of(args);
        String function = getValue(systemArgs, ARG);
        exitIfNoFunction(function);
        int columnNumber = getColumnNumber(getValue(systemArgs, COLUMN));
        List<String> lines = readLines();
        ExitIfInputEmpty(lines);
        List<String> data = getColumn(lines, columnNumber);
        Number result = aggregate(data, function);
        System.out.print(result);
    }

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
        if (column < 0 || column > lines.get(0).split(COLUMN_DELIMITER).length) {
            return List.of();
        }
        return lines.stream()
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
            case "median" -> result = median(data);
            case "mode" -> result = mode(data);
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

    private static Double mode(List<String> data) {
        String mode =  data.stream()
                .collect(Collectors.groupingBy(Function.identity()
                        , Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>("", 0L))
                .getKey();
        return Double.parseDouble(mode);
    }

    private static Double min(List<String> data) {
        return data.stream().mapToDouble(Double::parseDouble).min().orElse(0d);
    }

    private static Double max(List<String> data) {
        return data.stream().mapToDouble(Double::parseDouble).max().orElse(0d);
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

    private static Double median(List<String> data) {
        List<Double> sortedData = data.stream().map(Double::parseDouble).sorted().collect(Collectors.toList());
        int len = sortedData.size();
        if (len % 2 != 0) {
            return sortedData.get(len / 2);
        } else {
            return (sortedData.get(len / 2) + sortedData.get(len / 2 - 1)) / 2;
        }
    }

    public static void exitIfNoFunction(String function) {
        if (function == null) {
            System.err.println("No aggregation function given!");
            System.exit(1);
        }
    }

    public static void ExitIfInputEmpty(List<String> lines) {
        if (lines.isEmpty()) {
            System.exit(0);
        }
    }
}
