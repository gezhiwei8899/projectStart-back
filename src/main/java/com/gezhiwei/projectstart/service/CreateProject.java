package com.gezhiwei.projectstart.service;

import com.gezhiwei.projectstart.common.DirConfig;
import com.gezhiwei.projectstart.common.TemplateConfig;
import com.gezhiwei.projectstart.util.TemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @ClassName: CreateProject
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/9 12:34
 * @modified By:
 */
@Service
public class CreateProject implements ICreateProjectService {

    @Autowired
    private TemplateRenderer templateRenderer;

    @Override
    public File initDir() throws IOException {
        File rootDir = File.createTempFile("tmp", "", getTemporaryDirectory());
        rootDir.delete();
        rootDir.mkdirs();
        return rootDir;
    }

    public File createProjectDir(File rootDir, TemplateConfig templateConfig, DirConfig dirConfig) {


        /** start to create file dir **/
        File project = createIfNotExsit(rootDir, dirConfig.getProjectName());

        File java = createIfNotExsit(project, "src/main/java");

        File basePackage = createIfNotExsit(java, dirConfig.getPackageName().replaceAll("\\.", "/"));

        File common = createIfNotExsit(basePackage, "common");

        File config = createIfNotExsit(basePackage, "config");

        File dao = createIfNotExsit(basePackage, "dao");

        File service = createIfNotExsit(basePackage, "service");

        File entity = createIfNotExsit(dao, "entity");

        File mapper = createIfNotExsit(dao, "mapper");

        File resources = createIfNotExsit(project, "src/main/resources");

        File mybatis = createIfNotExsit(resources, "mybatis");

        File cmapper = createIfNotExsit(mybatis, "cmapper");

        File mapperR = createIfNotExsit(mybatis, "mapper");
        /** end to create file dir **/

        //从外到内
        writeText(new File(project, "pom.xml"), templateRenderer.buildPom(templateConfig));
        writeText(new File(project, ".gitignore"), templateRenderer.buildGitIgnore(templateConfig));
        writeText(new File(basePackage, templateConfig.getApplicationName() + ".java"), templateRenderer.buildApplication(templateConfig));
        writeText(new File(config, "DataSourceAutoConfiguration.java"), templateRenderer.buildDataSourceAutoConfiguration(templateConfig));

        writeText(new File(resources, "logback.xml"), templateRenderer.buildLogBack(templateConfig));
        writeText(new File(resources, "application.properties"), templateRenderer.buildApppProperties(templateConfig));
        writeText(new File(resources, "generatorConfig.xml"), templateRenderer.buildGenerateConfig(templateConfig));

        return project;

    }

    protected File createIfNotExsit(File basePackage, String common) {
        File f = new File(basePackage, common);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    private File getTemporaryDirectory() {
        File initializr;
        // 当前文件系统类
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            initializr = new File("c://", "initializr");
            if (!initializr.exists()) {
                initializr.mkdirs();
            }
        } else {
            initializr = new File("/data", "initializr");
            if (!initializr.exists()) {
                initializr.mkdirs();
            }
        }
        return initializr;
    }

    private void writeText(File target, String body) {
        try (OutputStream stream = new FileOutputStream(target)) {
            StreamUtils.copy(body, Charset.forName("UTF-8"), stream);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot write file " + target, ex);
        }
    }
}
