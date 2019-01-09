package com.gezhiwei.projectstart.service;

import com.gezhiwei.projectstart.common.DirConfig;
import com.gezhiwei.projectstart.common.TemplateConfig;

import java.io.File;
import java.io.IOException;

/**
 * @InterfaceName: ICreateProjectService
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/9 13:20
 * @modified By:
 */
public interface ICreateProjectService {
    File initDir() throws IOException;
    File createProjectDir(File dir ,TemplateConfig templateConfig, DirConfig dirConfig) throws IOException;

}
