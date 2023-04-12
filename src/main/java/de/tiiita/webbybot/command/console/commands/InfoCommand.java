package de.tiiita.webbybot.command.console.commands;

import de.tiiita.webbybot.command.console.ConsoleCommand;
import de.tiiita.webbybot.util.Config;
import net.dv8tion.jda.api.JDA;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created on April 12, 2023 | 17:20:25
 * (●'◡'●)
 */
public class InfoCommand extends ConsoleCommand {

    private final JDA jda;
    private final Config securityConfig;
    public InfoCommand(JDA jda, Config securityConfig) {
        super("info");
        this.jda = jda;
        this.securityConfig = securityConfig;
        setActionOnRun(this::sendInfo);
    }

    private void sendInfo() {
        System.out.println("> Bot Information <");
        System.out.println(getDesignLine());
        System.out.println("Name: " + jda.getSelfUser().getName());
        System.out.println("Guilds: " + jda.getGuilds().size());
        try {
            System.out.println("Version: " + getPomProjectVersion());
        } catch (IOException | XmlPullParserException e) {
            System.out.println("Version: Error by loading version...");
        }
        System.out.println("Creation Date: " + securityConfig.getString("creation-date"));
        System.out.println("Prefix: /");
        System.out.println(getDesignLine());
    }

    private String getPomProjectVersion() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return model.getVersion();
    }
}
