package bakup;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @Classname
 * @Description TODO
 * @Date 2020/7/13
 * @Created by cw
 */
public class tttt {
    public static String cloneRepository(String url,String localPath)
    {
        try{
            System.out.println("开始下载......");

            CloneCommand cc = Git.cloneRepository().setURI(url);
            cc.setDirectory(new File(localPath)).call();

            System.out.println("下载完成......");

            return "success";
        }catch(Exception e)
        {
            e.printStackTrace();
            return "error";
        }
    }
    public static void main(String[] args)
    {
        String localPath = "D:\\workspace\\jgittest\\sso";
        String url = "https://github.com/caiwei110123/sso.git";

        cloneRepository(url,localPath);
    }
//    public static void main(String[] args) throws GitAPIException {
//        Git git = Git.cloneRepository().setURI("https://github.com/caiwei110123/sso.git")
//                .setCredentialsProvider(provide())
//                .call();
//        git.checkout()
//                .setCreateBranch(true)
//                .setName("test1")
//                .call();
//        File file = git.getRepository().getDirectory();
//        String projectPath = file.getParent();
//        System.out.println(projectPath);
//
//    }
//
//    private static UsernamePasswordCredentialsProvider provide() {
//        return new UsernamePasswordCredentialsProvider("caiwei110123", "cai000-wei[]");
//    }
}
//https://blog.csdn.net/Amy126/article/details/85335834?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase


//https://zzzmh.cn/single?id=86