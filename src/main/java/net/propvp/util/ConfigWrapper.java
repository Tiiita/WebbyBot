package net.propvp.util;
import net.propvp.WebbyBot;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on April 10, 2023 | 17:46:08
 * (●'◡'●)
 */
public class ConfigWrapper {
    private final Map<String, Object> config;

    public ConfigWrapper() {
        config = new HashMap<>();
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

    public void load(String filePath) {

        Path path = Path.of(filePath);
        String fileExtension = getFileExtension(path);

        if (fileExtension.equals("yml") || fileExtension.equals("yaml")) {
            try {
                loadYaml(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                loadPlainText(path);
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
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getResourceAsStream("/config.yml");
        Map<String, Object> yamlConfig = yaml.load(inputStream);
        config.putAll(yamlConfig);
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
