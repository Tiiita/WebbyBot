package net.propvp.webbybot.util;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on April 10, 2023 | 17:46:08
 * (●'◡'●)
 */
public class Config {
    private final Map<String, Object> config;

    public Config(String filePath) {
        config = new HashMap<>();
        try {
            load(filePath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setString(String path, String value) {
        config.put(path, value);
    }

    public void setInt(String path, int value) {
        config.put(path, value);
    }

    public String getString(String path) {
        return (String) config.get(path);
    }

    public int getInt(String path) {
        return (int) config.get(path);
    }


    public void load(String filePath) throws URISyntaxException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource(filePath);
        Path resourcePath = Paths.get(resourceUrl.toURI());
        String fileExtension = getFileExtension(resourcePath);
        if (fileExtension.equals("yml") || fileExtension.equals("yaml")) {
            loadYaml(resourcePath);
        } else {
            try {
                loadPlainText(resourcePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    private void loadPlainText(Path path) throws IOException {
        for (String line : Files.readAllLines(path)) {
            String[] parts = line.split(":");
            config.put(parts[0].trim(), parts[1].trim());
        }
    }

    private void loadYaml(Path path) throws IOException {
        if (Files.size(path) == 0) {
            throw new IllegalArgumentException("Empty YAML file: " + path.toString());
        }
        Yaml yaml = new Yaml();
        try (Reader reader = Files.newBufferedReader(path)) {
            Map<String, Object> yamlConfig = yaml.load(reader);
            if (yamlConfig == null) {
                throw new IllegalArgumentException("Invalid YAML syntax: " + path.toString());
            }
            config.putAll(yamlConfig);
        }
    }

    public void save(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String fileExtension = getFileExtension(path);

        if (fileExtension.equals("yml") || fileExtension.equals("yaml")) {
            saveYaml(path);
        } else {
            savePlainText(path);
        }
    }

    private void savePlainText(Path path) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Object> entry : config.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        Files.writeString(path, sb.toString());
    }

    private void saveYaml(Path path) throws IOException {
        Yaml yaml = new Yaml();
        String yamlString = yaml.dump(config);
        Files.writeString(path, yamlString);
    }
}