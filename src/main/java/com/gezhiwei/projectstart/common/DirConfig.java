package com.gezhiwei.projectstart.common;

/**
 * @ClassName: DirConfig
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/9 12:14
 * @modified By:
 */
public class DirConfig {

    private String projectName;

    private String packageName;

    public DirConfig(String projectName, String packageName) {
        this.packageName = packageName;
        this.projectName = projectName;
    }


    public String getProjectName() {
        return projectName;
    }

    public DirConfig setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public DirConfig setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }
}
