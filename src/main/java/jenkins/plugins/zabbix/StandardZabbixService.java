package jenkins.plugins.zabbix;

import hudson.ProxyConfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import jenkins.model.Jenkins;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StandardZabbixService implements ZabbixService {

    /**
     * HTTP Connection timeout when making calls to HipChat
     */
    public static final Integer DEFAULT_TIMEOUT = 10000;
    private static final Logger logger = Logger.getLogger(StandardZabbixService.class.getName());

    private final String server;
    private final String host;

    public StandardZabbixService(String server, String host) {
        super();
        this.server = server;
        this.host = host;
    }

    public void publish(String jobName, Integer status) {
        logger.log(Level.INFO, "Posting: {0} -> {1} for {1} to {2}", new Object[]{jobName, status.toString(), getHost(), getServer()});

        try {
            Process p = Runtime.getRuntime().exec("zabbix_sender -z " + getServer() + " -s \"" + getHost() + "\" -k \"" + jobName + "\" -o " + status.toString());
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String output = "";
            String line = null;
            while ((line = in.readLine()) != null) {
                output += line + "\n";
            }
            int exitVal = p.waitFor();
            if (exitVal != 0) {
                logger.log(Level.WARNING, "Error running zabbix_sender \n{0}", output);
            } else {
                logger.log(Level.INFO, "Succesfully running zabbix_sender \n{0}", output);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error running zabbix_sender", e);
        } catch (InterruptedException ex) {
            logger.log(Level.WARNING, "Error running zabbix_sender", ex);
        }

    }

    public String getServer() {
        return server;
    }

    public String getHost() {
        return host;
    }
}
