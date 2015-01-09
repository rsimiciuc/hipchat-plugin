package jenkins.plugins.zabbix;

public interface ZabbixService {
    void publish(String jobName, Integer status);
}
