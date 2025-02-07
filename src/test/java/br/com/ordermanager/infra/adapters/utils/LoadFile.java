package br.com.ordermanager.infra.adapters.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadFile {

    public LoadFile() {
        // Utility class
    }

    public static String toString(final String fileName) {
        try {
            var path = Path.of("src", "test", "resources", "assets", fileName);
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error loading file", e);
        }
    }
}
