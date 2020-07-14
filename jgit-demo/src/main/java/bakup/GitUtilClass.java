package bakup;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.util.List;

/**
 * @Classname
 * @Description TODO
 * @Date 2020/7/13
 * @Created by cw
 */
public class GitUtilClass {
    public static String localRepoPath = "D:\\workspace\\jgittest";
    public static String localRepoGitConfig = "D:\\workspace\\jgittest\\.git";
    public static String remoteRepoURI = "git@git.code.tencent.com:caiwei110123/test-demo.git";
    public static String localCodeDir = "D:/platplat";

    public static void main(String[] args) {
        GitUtilClass.newBranch("TEST-111");
    }
    /**
     * 新建一个分支并同步到远程仓库
     * @param branchName
     */
    public static String newBranch(String branchName){
        String newBranchIndex = "refs/heads/"+branchName;
        String gitPathURI = "";
        Git git = null;
        //Git.open();
        try {

            //检查新建的分支是否已经存在，如果存在则将已存在的分支强制删除并新建一个分支
            List<Ref> refs = git.branchList().call();
            for (Ref ref : refs) {
                if (ref.getName().equals(newBranchIndex)) {
                    System.out.println("Removing branch before");
                    git.branchDelete().setBranchNames(branchName).setForce(true)
                            .call();
                    break;
                }
            }
            //新建分支
            Ref ref = git.branchCreate().setName(branchName).call();
            //推送到远程
            git.push().add(ref).call();
            gitPathURI = remoteRepoURI + " " + "feature/" + branchName;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gitPathURI;
    }

    public static void commitFiles() throws Exception{
        String filePath = "";
        Git git = Git.open( new File(localRepoGitConfig) );
        //创建用户文件的过程
        File myfile = new File(filePath);
        myfile.createNewFile();
        git.add().addFilepattern("pets").call();
        //提交
        git.commit().setMessage("Added pets").call();
        //推送到远程
        git.push().call();
    }

    public static boolean pullBranchToLocal(String cloneURL){
        boolean resultFlag = false;
        String[] splitURL = cloneURL.split(" ");
        String branchName = splitURL[1];
        String fileDir = localCodeDir+"/"+branchName;
        //检查目标文件夹是否存在
        File file = new File(fileDir);
        if(file.exists()){
            deleteFolder(file);
        }
        Git git;
        try {
            git = Git.open( new File(localRepoGitConfig) );
            git.cloneRepository().setURI(cloneURL).setDirectory(file).call();
            resultFlag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultFlag;
    }

    public static void deleteFolder(File file){
        if(file.isFile() || file.list().length==0){
            file.delete();
        }else{
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                deleteFolder(files[i]);
                files[i].delete();
            }
        }
    }

    public static void setupRepo() throws Exception{
        //建立与远程仓库的联系，仅需要执行一次
        Git git = Git.cloneRepository().setURI(remoteRepoURI).setDirectory(new File(localRepoPath)).call();
    }

}