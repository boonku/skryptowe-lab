import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Head {
    public static Optional<Integer> getNumberOfLines(List<String> args) {
        return args.stream().
                filter(arg -> arg.contains("--lines="))
                .findFirst()
                .map(line -> Integer.valueOf(line.split("=")[1]));
    }

    public static void checkParam(Optional<?> param) {
        if (param.isEmpty()) {
            System.exit(0);
        }
    }

    public static List<String> getNLines(int numberOfLines, boolean showError) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        String line;
        for (int i = 0; i < numberOfLines; i++) {
            if (scanner.hasNext()) {
                line = scanner.nextLine();
                lines.add(line);
            } else {
                if (showError) {
                    System.err.println("Zabraklo " + (numberOfLines - i) + " linii do wypisana");
                }
                System.exit(2);
            }


        }
        return lines;
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
        checkParam(numberOfLines);
        displayLines(getNLines(numberOfLines.get(), showError));
    }
}
