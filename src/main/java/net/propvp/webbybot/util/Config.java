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
        } catch (IOException e) {
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


    public void load(String filePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        String fileExtension = getFileExtension(filePath);
        if (fileExtension.equals("yml") || fileExtension.equals("yaml")) {
            loadYaml(inputStream);
        } else {
            loadPlainText(inputStream);
        }
    }

    private String getFileExtension(String filePath) {
        String fileName = new File(filePath).getName();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    private void loadPlainText(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                config.put(parts[0].trim(), parts[1].trim());
            }
        }
    }

    private void loadYaml(InputStream inputStream) throws IOException {
        Yaml yaml = new Yaml();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Map<String, Object> yamlConfig = yaml.load(reader);
            if (yamlConfig == null) {
                throw new IllegalArgumentException("Invalid YAML syntax");
            }
            config.putAll(yamlConfig);
        }
    }

    public void save(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String fileExtension = getFileExtension(filePath);

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