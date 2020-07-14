package bakup; /**
 * @Classname
 * @Description TODO
 * @Date 2020/7/13
 * @Created by cw
 */
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * bakup.TestController 测试
 *
 * @author
 * @date 2020/3/31
 */
@RestController
@Slf4j
public class TestController {

    public static void main(String[] args) {
    new TestController().cloneBranch();
    }
    @RequestMapping("test")
    public String sayHello() {
        cloneBranch();
        //checkOutBranch();
        //createNewBranch();
        //delectBranch();
//        mergeBranch();
        return "Hello Spring Boot";
    }

    void cloneBranch() {
        String remotePath = "git@git.code.tencent.com:caiwei110123/test-demo.git";
        //权限验证
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("PRIVATE-TOKEN", "dG8LHTpcPFa42XNQzje0");
        try {
            List<String> branchs = new ArrayList<String>();
            branchs.add("master");
//            branchs.add("refs/heads/test");
            //====连接dev_test_dev01分支
            CloneCommand cloneCommand = Git.cloneRepository();
            Git git = cloneCommand.setURI(remotePath)//要从中克隆的uri
                    .setDirectory(new File("D:\\workspace\\jgittest\\test"))//克隆到的目录
                    .setBranchesToClone(branchs)//.setBranch("refs/heads/test")
                    .setCredentialsProvider(usernamePasswordCredentialsProvider)
                    .setBranch("master")
                    .call();
            List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();//ALL -a 所有分支  REMOTE -r 远程分支
            System.out.println(list);
            List<Ref> list1 = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();//ALL -a 所有分支  REMOTE -r 远程分支
            System.out.println(list1);
            List<Ref> gitList = git.branchList().call();//本地分支
            for (Ref ref : gitList) {
                System.out.println("分支：" + ref + "" + ref.getName() + "" + ref.getObjectId().getName());
            }

            System.out.println("==================over==========================");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void checkOutBranch() {
        //-   refs/heads/test   refs/remotes/origin/dev_test_dev02   refs/remotes/origin/test
        try {
            Git git = Git.open(new File("E:/repo"));
            List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();//ALL -a 所有分支  REMOTE -r 远程分支
            System.out.println(list);
            //切换远端分支
//            Ref ref= git.checkout().setCreateBranch(true)//创建新分支
//            .setName("dev_test_dev02")
//            .setStartPoint("origin/dev_test_dev02")//对应于起点选项
//            .call();
//            System.out.println(ref);
            //切换本地分支
            Ref ref = git.checkout().setCreateBranch(false).setName("test").call();
            System.out.println(ref);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void createNewBranch() {

        try {
            Git git = Git.open(new File("E:/repo"));
            List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();//ALL -a 所有分支  REMOTE -r 远程分支
            System.out.println(list);
            //切换本地分支dev_test_dev02
            Ref ref = git.checkout().setCreateBranch(false).setName("dev_test_dev02").call();
            System.out.println(ref);
            //切换本地分支test
            ref = git.checkout().setCreateBranch(false).setName("test").call();
            System.out.println(ref);
            //新建
//            ref = git.checkout().setCreateBranch(true).setName("dev_test_dev01").call();
//            System.out.println(ref);
            //01 本地分支以存在 切换本地分支
            ref = git.checkout().setCreateBranch(false).setName("dev_test_dev01").call();
            System.out.println(ref);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void delectBranch() {
        //权限验证
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("**", "***");
        try {
            Git git = Git.open(new File("E:/repo"));
            List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();//ALL -a 所有分支  REMOTE -r 远程分支
            //删除远程分支
//            RefSpec refSpec1 = new RefSpec().setSource(null).setDestination(R_HEADS+"dev_test_dev02");
//            if (null == refSpec1) {
//                System.out.println("远程分支不存在"+"dev_test_dev02");
//            }
//            git.push().setCredentialsProvider(usernamePasswordCredentialsProvider).setRefSpecs(refSpec1).setRemote("origin").call();
//            System.out.println("删除分支成功");
//            System.out.println("--------------");

            //删除远程分支
//            RefSpec refSpec2 = new RefSpec().setSource(null).setDestination(R_HEADS+"dev_test_dev01");//设置目标
//            if (null == refSpec2) {
//                System.out.println("远程分支不存在"+"dev_test_dev01");
//            }
//            git.push().setCredentialsProvider(usernamePasswordCredentialsProvider).setRefSpecs(refSpec2).setRemote("origin").call();
//            System.out.println("删除分支成功");
//            System.out.println("--------------");
            //切换本地分支test
            Ref ref = git.checkout().setCreateBranch(false).setName("test").call();
            System.out.println(ref);
            //删除本地分支
            git.branchDelete().setBranchNames("refs/heads/dev_test_dev01").call();
            System.out.println("删除分支成功");
            System.out.println("--------------");

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    void mergeBranch() {
        //权限验证
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("**", "**");
        try {
            Git git = Git.open(new File("E:/repo"));
            List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();//ALL -a 所有分支  REMOTE -r 远程分支

            //新建 03 分支（03==test）
//            Ref ref = git.checkout().setCreateBranch(true).setName("dev_test_dev03").call();
//            System.out.println(ref);
//
//            //切换到本地02
//            ref = git.checkout().setCreateBranch(false).setName("dev_test_dev02").call();
//            System.out.println(ref);
//
//            //新建 04 分支（04==02）
//            Ref ref4 = git.checkout().setCreateBranch(true).setName("dev_test_dev04").call();
//            System.out.println(ref4);
//
//            //切换到03
//            ref = git.checkout().setCreateBranch(false).setName("dev_test_dev03").call();
//            System.out.println(ref);
//
//            //03 合并 04
//            git.merge().include(ref4).call();

            //推送到远程
            Ref ref = git.checkout().setCreateBranch(false).setName("dev_test_dev04").call();
            System.out.println(ref);
            //git.pull().setCredentialsProvider(usernamePasswordCredentialsProvider).call();

            git.push().add(ref).setCredentialsProvider(usernamePasswordCredentialsProvider).call();

            List<Ref> list2 = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();//ALL -a 所有分支  REMOTE -r 远程分支
            System.out.println("---------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String initSynchronizedObject(String param1, String param2) {
        return param1 + param2;
    }


}