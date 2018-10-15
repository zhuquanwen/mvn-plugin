package com.zqw;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/10/13 14:16
 * @since jdk1.8
 */
@Mojo(name = "count",defaultPhase = LifecyclePhase.PACKAGE)
public class CountMojo extends AbstractMojo {
    @Parameter(property = "msg")
    private String msg;
    @Parameter(property = "options")
    private String[] options;
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println(msg);
        if (options != null) Arrays.stream(options).forEach(System.out::println);
        System.out.println("共有Java文件数目:" + javaCount());
    }
    public int javaCount(){
        String path = System.getProperty("user.dir");
        File file = new File(path);
        int[] count = new int[]{0};
        getFileJavaCount(file, count);

        return count[0];
    }
    private void getFileJavaCount(File file, final int[] count){

        if(file.exists()){
            File[] files = file.listFiles(new FileFilter() {
                public boolean accept(File file1) {
                    if(file1.isDirectory()) {
                        getFileJavaCount(file1, count);
                        return false;
                    }
                    return file1.getName().endsWith(".java");
                }
            });
            if(files != null) count[0] += files.length;
        }

    }
}
