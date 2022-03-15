import java.util.Map;

public class PokazWszystkie {
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        for(Map.Entry<String ,String> entry: env.entrySet()) {
            System.out.printf("%s=%s ", entry.getKey(), entry.getValue());
        }
        System.out.println();
        for(String arg: args) {
            System.out.printf("%s ", arg);
        }

    }
}