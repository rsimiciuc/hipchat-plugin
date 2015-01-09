package jenkins.plugins.zabbix;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.listeners.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jenkins.model.Jenkins;
import jenkins.plugins.zabbix.ZabbixNotifier.ZabbixJobProperty;

@Extension
public class ConfigurationMigrator extends ItemListener {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationMigrator.class.getName());

    @Override
    public void onLoaded() {
        for (AbstractProject<?, ?> item : Jenkins.getInstance().getAllItems(AbstractProject.class)) {
            ZabbixJobProperty property = item.getProperty(ZabbixJobProperty.class);
            if (property != null) {
                ZabbixNotifier notifier = item.getPublishersList().get(ZabbixNotifier.class);
                if (notifier != null) {
                    notifier.setNotifyAborted(property.getNotifyAborted());
                    notifier.setNotifyBackToNormal(property.getNotifyBackToNormal());
                    notifier.setNotifyFailure(property.getNotifyFailure());
                    notifier.setNotifyNotBuilt(property.getNotifyNotBuilt());
                    notifier.setNotifySuccess(property.getNotifySuccess());
                    notifier.setNotifyUnstable(property.getNotifyUnstable());
                }
                try {
                    item.removeProperty(ZabbixJobProperty.class);
                } catch (IOException ioe) {
                    LOGGER.log(Level.WARNING, "An error occurred while trying to update job configuration for "
                            + item.getName(), ioe);
                }
            }
        }
    }
}
