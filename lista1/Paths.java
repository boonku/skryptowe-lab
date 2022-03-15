import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Paths {
    public static final String TAB = "\t";
    public static final String CURRENT_DIR = ".";

    public static void main(String[] args) {
        boolean recursive = false; // recursive, shows files in subdirectories
        boolean showSize = false; // show size after file/dir
        boolean onlyDir = false; // show only dirs
        int sortMode; // sorting mode, 0 - none, 1 - alpha, 2 - date
        File currentDir = new File(CURRENT_DIR);
        List<String> systemArgs = Arrays.asList(args);
        if (systemArgs.contains("-R")) {
            recursive = true;
        }
        if (systemArgs.contains("-d")) {
            onlyDir = true;
        }
        if (systemArgs.contains("-s")) {
            showSize = true;
        }
        sortMode = getSortingMode(systemArgs);
        display(currentDir, 0, recursive, onlyDir, showSize, sortMode);
    }

    private static int getSortingMode(List<String> systemArgs) {
        if (systemArgs.contains("--sort")) {
            int index = systemArgs.indexOf("--sort");
            if (systemArgs.size() > index + 1) {
                String mode = systemArgs.get(index + 1);
                if (mode.equals("alpha")) {
                    return 1;
                } else if (mode.equals("date")) {
                    return 2;
                } else {
                    System.out.println("Invalid --sort mode");
                    System.exit(1);
                }
            } else {
                System.out.println("No --sort mode or at invalid index");
                System.exit(1);
            }
        }
        return 0;
    }

    public static void appendSize(StringBuilder stringBuilder, File file) {
        stringBuilder.append(" ");
        long size = 0;
        if (file.isDirectory()) {
            try {
                size = Files.walk(java.nio.file.Paths.get(file.getAbsolutePath()))
                        .filter(p -> p.toFile().isFile())
                        .mapToLong(p -> p.toFile().length())
                        .sum();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            size = file.length();
        }
        stringBuilder.append(size).append(" B");
    }

    public static void sortFiles(List<File> files, int sortMode) {
        if (sortMode == 1) {
            files.sort(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
        } else if (sortMode == 2) {
            files.sort(Comparator.comparing(File::lastModified, Comparator.reverseOrder()));
        }
    }

    public static void display(File dir,
                               int indent,
                               boolean recursive,
                               boolean onlyDir,
                               boolean showSize,
                               int sortMode) {
        // converts all files from directory to list
        List<File> files = new ArrayList<>(Arrays.asList(dir.listFiles()));
        sortFiles(files, sortMode);
        if (onlyDir) {
            files.removeIf(file -> !file.isDirectory());
        }
        for (File file: files) {
            StringBuilder stringBuilder = new StringBuilder();
            String tabs = TAB.repeat(indent);
            stringBuilder.append(tabs);
            stringBuilder.append(file.getName());
            if (file.isDirectory()) {
                stringBuilder.append("/");
            }
            if (showSize) {
                appendSize(stringBuilder, file);
            }
            System.out.println(stringBuilder);
            if (recursive && file.isDirectory()) {
                display(file, indent + 1, recursive, onlyDir, showSize, sortMode);
            }
        }
    }
}
