package com.gezhiwei.projectstart.somethingtest;

import com.gezhiwei.projectstart.util.TemplateRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: TestFile
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/9 9:53
 * @modified By:
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * 1.模板填充数据 、 一次性dir创建数据 、 要被填充子文件的package或者dir数据 、
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFile {

    @Autowired
    private TemplateRenderer templateRenderer;

    private transient Map<String, List<File>> temporaryFiles = new LinkedHashMap<>();

    @Test
    public void test() {
        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("groupId", "com.gezhiwei");
        model.put("artifactId", "test-asdb");
        model.put("basePackage", doGenerateBasePackage(model));


        try {
            File rootDir = File.createTempFile("tmp", "", getTemporaryDirectory());
            System.out.println(rootDir.getName());

            rootDir.delete();
            rootDir.mkdirs();

            File dir = new File(rootDir, model.get("artifactId").toString());
            dir.mkdirs();

            File java = new File(dir, "src/main/java");
            java.mkdirs();

            File src = new File(java, model.get("basePackage").toString().replaceAll("\\.", "/"));
            src.mkdirs();
            doGeneratePackages(src);

            File resources = new File(dir, "src/main/resources");
            resources.mkdirs();
            doGenerateBaseResourcesDir(resources);

            String pom = new String(doGenerateMavenPom(model));
            writeText(new File(dir, "pom.xml"), pom);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doGenerateBaseResourcesDir(File resources) {

    }

    private Map<String, File> doGeneratePackages(File src) {
        Map<String, File> fileMap = new ConcurrentHashMap<>();

        File dir;
        dir = new File(src, "common");
        dir.mkdirs();
        fileMap.put("common", dir);

        dir = new File(src, "config");
        dir.mkdirs();
        fileMap.put("config", dir);

        dir = new File(src, "service");
        dir.mkdirs();
        fileMap.put("service", dir);

        File dao;
        dao = new File(src, "dao");
        dao.mkdirs();
        fileMap.put("dao", dao);

        dir = new File(dao, "entity");
        dir.mkdirs();
        fileMap.put("entity", dir);

        dir = new File(dao, "mappper");
        dir.mkdirs();
        fileMap.put("mappper", dir);

        return fileMap;
    }

    private String doGenerateBasePackage(Map<String, Object> model) {
        return model.get("groupId") + "." + model.get("artifactId").toString().replaceAll("-", ".");
    }

    private File getTemporaryDirectory() {
        File initializr = new File("c://", "initializr");
        if (!initializr.exists()) {
            initializr.mkdirs();
        }
        return initializr;
    }

    private byte[] doGenerateMavenPom(Map<String, Object> model) {
        return this.templateRenderer.process("starter-pom.xml", model).getBytes();
    }

    private void writeText(File target, String body) {
        try (OutputStream stream = new FileOutputStream(target)) {
            StreamUtils.copy(body, Charset.forName("UTF-8"), stream);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot write file " + target, ex);
        }
    }

}
