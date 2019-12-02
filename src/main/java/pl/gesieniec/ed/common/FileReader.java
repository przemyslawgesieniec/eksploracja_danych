package pl.gesieniec.ed.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileReader {

    public static <T> List<T> readFile(final String filePath,
                                       final Function<String, T> objectConverter) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return lines.stream().map(objectConverter).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
