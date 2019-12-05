package com.xiaodou.docplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.xiaodou.docplugin.log.LogHelper;
import com.xiaodou.docplugin.service.IJavadocService;
import com.xiaodou.docplugin.service.impl.JavadocService;
import com.xiaodou.docplugin.service.impl.dc.DcJavadocService;
import com.xiaodou.docplugin.util.ExceptionHelper;

/**
 * @author bin.song
 * @goal javadoc
 * @phase javadoc for com.xiaodou
 */
public class JavadocMojo extends AbstractMojo {


    /**
     * @parameter expression="${project.version}"
     */
    private String projectVersion;

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    /**
     * @parameter expression="${basedir}"
     */
    private String pjBaseDirectory;

    public String getPjBaseDirectory() {
        return pjBaseDirectory;
    }

    public void setPjBaseDirectory(String projectBaseDirectory) {
        this.pjBaseDirectory = projectBaseDirectory;
    }

    /**
     * @parameter expression="${project.name}"
     */
    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @parameter
     */
    private boolean isDc;

    public void execute() throws MojoExecutionException, MojoFailureException {
        // TODO Auto-generated method stub
        System.out.println("start to make javadoc for " + this.jobName + ", dir = " + this.pjBaseDirectory);
        System.out.println("isDc: " + isDc);
        try {
            IJavadocService service = new JavadocService();
            if (isDc) service = new DcJavadocService();
            service.execute(this.pjBaseDirectory, this.jobName);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("err to make javadoc for " + this.jobName);
            LogHelper.Log("JavadocMojo-->execute: excepMsg-->" + e.getMessage() + ":stackInfo-->" + ExceptionHelper.getStackTrace(e));
        }
        System.out.println("end to make javadoc for " + this.jobName);
    }

}
