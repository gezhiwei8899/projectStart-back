package com.gezhiwei.projectstart.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: TemplateConfig
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/9 12:13
 * @modified By:
 */
public class TemplateConfig {

    private Map<String, Object> model = new ConcurrentHashMap<>();

    private String packageName;

    private String applicationName;

    private String mysqlUrl;

    private String mysqlUser;

    private String mysqlPassword;

    private String groupId;

    private String artifactId;

    public TemplateConfig(String packageName, String applicationName, String mysqlUrl, String mysqlUser, String mysqlPassword, String groupId, String artifactId) {
        this.model.put("packageName", packageName);
        this.model.put("applicationName", applicationName);
        this.model.put("mysqlUrl", mysqlUrl);
        this.model.put("mysqlUser", mysqlUser);
        this.model.put("mysqlPassword", mysqlPassword);
        this.model.put("groupId", groupId);
        this.model.put("artifactId", artifactId);
        this.packageName = packageName;
        this.applicationName = applicationName;
        this.mysqlUrl = mysqlUrl;
        this.mysqlUser = mysqlUser;
        this.mysqlPassword = mysqlPassword;
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public TemplateConfig setModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public TemplateConfig setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public TemplateConfig setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getMysqlUrl() {
        return mysqlUrl;
    }

    public TemplateConfig setMysqlUrl(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
        return this;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public TemplateConfig setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
        return this;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public TemplateConfig setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public TemplateConfig setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public TemplateConfig setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

}
