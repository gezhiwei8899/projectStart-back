package com.gezhiwei.projectstart.controller;

import com.alibaba.fastjson.JSONObject;
import com.gezhiwei.projectstart.common.DirConfig;
import com.gezhiwei.projectstart.common.TemplateConfig;
import com.gezhiwei.projectstart.controller.vo.ProjectInfoVo;
import com.gezhiwei.projectstart.service.ICreateProjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.ZipFileSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MainController
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/8 11:17
 * @modified By:
 */
@RestController
public class MainController {

    private transient Map<String, List<File>> temporaryFiles = new LinkedHashMap<>();

    @Autowired
    private ICreateProjectService iCreateProjectService;

    //创建一个项目直接打到 github上
    @RequestMapping("/start/project")
    public ResponseEntity<byte[]> startNewProject(String params) throws IOException {

        ProjectInfoVo projectInfoVo = JSONObject.parseObject(params, ProjectInfoVo.class);

        File dir = iCreateProjectService.initDir();

        String packageName = projectInfoVo.getGroupId() + "." + projectInfoVo.getArtifactId().replaceAll("-", "");
        String applicationName = StringUtils.capitalize(projectInfoVo.getArtifactId().replaceAll("-", ""));
        String mysqlUrl = projectInfoVo.getMysqlUrl();
        String mysqlUser = projectInfoVo.getMysqlUser();
        String mysqlPassword = projectInfoVo.getMysqlPassword();
        String groupId = projectInfoVo.getGroupId();
        String artifactId = projectInfoVo.getArtifactId();

        String projectName = projectInfoVo.getArtifactId();


        TemplateConfig templateConfig = new TemplateConfig(packageName, applicationName, mysqlUrl, mysqlUser, mysqlPassword, groupId, artifactId);

        DirConfig dirConfig = new DirConfig(projectName, packageName);

        File projectDir = iCreateProjectService.createProjectDir(dir, templateConfig, dirConfig);

        File download = new File(dir, projectDir.getName() + ".zip");
        addTempFile(dir.getName(), download);

        Zip zip = new Zip();

        zip.setProject(new Project());
        zip.setDefaultexcludes(false);

        ZipFileSet set = new ZipFileSet();

        set.setDir(projectDir);
        set.setIncludes("**,");
        set.setDefaultexcludes(false);

        zip.addFileset(set);
        zip.setDestFile(download.getCanonicalFile());
        zip.execute();
        return upload(download, dir, generateFileName(artifactId, "zip"), "application/zip");
    }

    private void addTempFile(String group, File file) {
        this.temporaryFiles.computeIfAbsent(group, (key) -> new ArrayList<>()).add(file);
    }

    private static String generateFileName(String artifactId, String extension) {
        String tmp = artifactId.replaceAll(" ", "-");
        try {
            return URLEncoder.encode(tmp, "UTF-8") + "." + extension;
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode URL", ex);
        }
    }

    private ResponseEntity<byte[]> upload(File download, File dir, String fileName, String contentType) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(download)) {
            byte[] bytes = StreamUtils.copyToByteArray(fileInputStream);

            ResponseEntity<byte[]> result = createResponseEntity(bytes, contentType, fileName);
            cleanTempFiles(dir);
//            Files.delete(Paths.get(dir.getPath()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseEntity<byte[]> createResponseEntity(byte[] content, String contentType, String fileName) {
        String contentDispositionValue = "attachment; filename=\"" + fileName + "\"";
        return ResponseEntity
                .ok()
                .header("Content-Type", contentType)
                .header("Content-Disposition", contentDispositionValue)
                .body(content);
    }

    /**
     * Clean all the temporary files that are related to this root directory.
     *
     * @param dir the directory to clean
     * @see #createDistributionFile
     */
    public void cleanTempFiles(File dir) {
        List<File> tempFiles = this.temporaryFiles.remove(dir.getName());
        if (!tempFiles.isEmpty()) {
            tempFiles.forEach((File file) -> {
                if (file.isDirectory()) {
                    FileSystemUtils.deleteRecursively(file);
                } else if (file.exists()) {
                    file.delete();
                }
            });
        }
    }
}
