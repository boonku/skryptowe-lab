import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KodPowrotu {
    public static int count(String line, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(line);
        int count = 0;
        int position = 0;
        while (matcher.find(position)) {
            count++;
            position = matcher.start() + 1;
        }
        return count;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        }
        Map<String, Integer> counter = new HashMap<>();
        for (String arg: args) {
            counter.put(arg, 0);
        }
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            counter.forEach((key, value) -> {
                counter.compute(key, (k, v) -> v + count(line, k));
            });
        }
        String mostCommon = counter.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
        System.exit(List.of(args).indexOf(mostCommon) + 1);
    }
}
