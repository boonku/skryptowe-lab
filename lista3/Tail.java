import java.util.*;

public class Tail {
    public static Optional<Integer> getNumberOfLines(List<String> args) {
        return args.stream().
                filter(arg -> arg.contains("--lines="))
                .findFirst()
                .map(line -> Integer.valueOf(line.split("=")[1]));
    }

    public static void exitIfParameterNotFound(Optional<?> param) {
        if (param.isEmpty()) {
            System.exit(0);
        }
    }

    public static List<String> getNLines(int numberOfLines, boolean showError) {
        Scanner scanner = new Scanner(System.in);
        Queue<String> lines = new LinkedList<>();
        while(scanner.hasNext()) {
            if (lines.size() == numberOfLines) {
                lines.poll();
            }
            lines.add(scanner.nextLine());
        }
        if (lines.size() < numberOfLines) {
            if (showError) {
                System.err.println("Zabraklo " + (numberOfLines - lines.size()) + " linii do wypisana");
            }
            System.exit(2);
        }

        return new ArrayList<>(lines);
    }

    public static void displayLines(List<String> lines) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line: lines) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        System.out.print(stringBuilder);
    }

    public static void main(String[] args) {
        List<String> systemArgs = List.of(args);
        boolean showError = !systemArgs.contains("-e");
        Optional<Integer> numberOfLines = getNumberOfLines(systemArgs);
        exitIfParameterNotFound(numberOfLines);
        displayLines(getNLines(numberOfLines.get(), showError));
    }
}
