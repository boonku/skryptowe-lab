import java.util.*;
import java.util.stream.Collectors;

public class PokazPodobne {
    public static final String NONE = "NONE";
    public static final String DELIMITER = ";";

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        List<String> systemArgs = Arrays.asList(args);
        env = filter(env, systemArgs);
        display(env, systemArgs);
    }

    public static void display(Map<String, String> env, List<String> args) {
        Map<String, String> sortedEnv = new TreeMap<>(env);
        for (Map.Entry<String, String> var : sortedEnv.entrySet()) {
            String value = var.getValue();
            if (value.contains(DELIMITER)) {
                List<String> values = List.of(value.split(DELIMITER));
                System.out.printf("%s = %n", var.getKey());
                for (String val : values) {
                    System.out.printf("\t%s%n", val);
                }
            } else {
                System.out.printf("%s = %s%n", var.getKey(), var.getValue());
            }
        }
    }

    public static Map<String, String> filter(Map<String, String> env, List<String> args) {
        Map<String, String> filteredEnv = env.entrySet().stream()
                .filter(e -> args.stream()
                        .anyMatch(e.getKey()::contains))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        // put arg with value NONE if it wasn't found
        for (String arg: args) {
            if (filteredEnv.keySet().stream().noneMatch(key -> key.contains(arg))) {
                filteredEnv.put(arg, NONE);
            }
        }
        return filteredEnv;
    }
}
