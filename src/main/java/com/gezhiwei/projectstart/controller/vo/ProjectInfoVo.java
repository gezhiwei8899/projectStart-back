package com.gezhiwei.projectstart.controller.vo;

/**
 * @ClassName: ProjectInfoVo
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/8 11:24
 * @modified By:
 */
public class ProjectInfoVo {
    private String groupId;

    private String artifactId;

    private String mysqlUrl="";

    private String mysqlUser="";

    private String mysqlPassword = "";

    public String getGroupId() {
        return groupId;
    }

    public ProjectInfoVo setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public ProjectInfoVo setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getMysqlUrl() {
        return mysqlUrl;
    }

    public ProjectInfoVo setMysqlUrl(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
        return this;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public ProjectInfoVo setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
        return this;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public ProjectInfoVo setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
        return this;
    }
}
