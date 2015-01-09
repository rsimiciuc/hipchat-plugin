package jenkins.plugins.zabbix;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Result;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class ActiveNotifier implements FineGrainedNotifier {

    private static final Logger logger = Logger.getLogger(ActiveNotifier.class.getName());

    private final ZabbixNotifier notifier;

    public ActiveNotifier(ZabbixNotifier notifier) {
        super();
        this.notifier = notifier;
    }

    private ZabbixService getZabbix(AbstractBuild r) {
        return notifier.newZabbixService();
    }

    public void deleted(AbstractBuild r) {
    }

    public void started(AbstractBuild build) {
        /** do nothing **/
    }

    private void notifyStart(AbstractBuild build, String message) {
        /** do nothing **/
    }

    public void finalized(AbstractBuild r) {
        /** do nothing **/
    }

    public void completed(AbstractBuild r) {
        AbstractProject<?, ?> project = r.getProject();
        Result result = r.getResult();
        AbstractBuild<?, ?> previousBuild = project.getLastBuild().getPreviousBuild();
        Result previousResult = (previousBuild != null) ? previousBuild.getResult() : Result.SUCCESS;
        if ((result == Result.ABORTED && notifier.isNotifyAborted())
                || (result == Result.FAILURE && notifier.isNotifyFailure())
                || (result == Result.NOT_BUILT && notifier.isNotifyNotBuilt())
                || (result == Result.SUCCESS && previousResult == Result.FAILURE && notifier.isNotifyBackToNormal())
                || (result == Result.SUCCESS && notifier.isNotifySuccess())
                || (result == Result.UNSTABLE && notifier.isNotifyUnstable())) {
            getZabbix(r).publish(getJobName(r), getResultCode(result));
        }
    }

    static Integer getResultCode(Result result) {
        if (result == Result.SUCCESS) {
            return 0;
        }

        if (result == Result.FAILURE) {
            return 1;
        }

        if (result == Result.UNSTABLE) {
            return 2;
        }

        if (result == Result.ABORTED) {
            return 3;
        }

        if (result == Result.NOT_BUILT) {
            return 4;
        }

        return 5;
    }

    String getJobName(AbstractBuild r) {
        return r.getProject().getDisplayName();
    }    
}
