import java.io.File;
import java.io.IOException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
/**
 * java 操作 git
 * 实现java自动化操作git 简单功能实现
 * <p>
 * 基于Jgit
 * <dependency>
 * <groupId>org.eclipse.jgit</groupId>
 * <artifactId>org.eclipse.jgit</artifactId>
 * <version>${jgit.version}</version>
 * </dependency>
 * <p>
 * 代码参考
 * https://www.jqhtml.com/26305.html
 */
public class JGitUtil {
    public static void main(String[] args) {
        try {
//            String uri = "https://github.com/caiwei110123/sso.git";
            String uri = "https://git.code.tencent.com/caiwei110123/test-demo.git";
            //  cai000-wei[]
            UsernamePasswordCredentialsProvider provider =getCredentialsProvider("caiwei110123","cai000wei");
            File gitDir = new File("D:\\workspace\\jgittest\\sso");
           // doInit(gitDir);

          // doClone(gitDir,uri,provider,"master");
            String branchName = "xxxxx";
            doCreate(gitDir,branchName);
             doCheckOutAndPushBranch(gitDir,branchName,provider,uri);
            System.out.println("========== =======ok=========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void doPushBranch(File gitDir, String branchName) throws GitAPIException, IOException {
        Git git = Git.open(gitDir);

    }
    public static void doCheckOutAndPushBranch(File gitDir, String branchName,CredentialsProvider provider,String uri) throws Exception {
        Git git = Git.open(gitDir);
        CheckoutCommand checkOut = git.checkout().setName(branchName);
        checkOut.call();

        // add remote repo:
        RemoteAddCommand remoteAddCommand = git.remoteAdd();
        remoteAddCommand.setName(branchName);
        remoteAddCommand.setUri(new URIish(uri));
        // you can add more settings here if needed
        remoteAddCommand.call();

        // push to remote:
        PushCommand pushCommand = git.push();
        pushCommand.setCredentialsProvider(provider);
        // you can add more settings here if needed
        pushCommand.call();

   //     git.push().add(rf).setCredentialsProvider(provider).call();

    }
    public static UsernamePasswordCredentialsProvider getCredentialsProvider(String gituser, String gitpasswd) {
        UsernamePasswordCredentialsProvider credentialsProvider = null;
        if (StringUtils.isNotEmpty(gituser) && StringUtils.isNotEmpty(gitpasswd)) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(gituser, gitpasswd);
        }
        return credentialsProvider;
    }
    public static void doInit(File gitDir) throws GitAPIException {
        if (!gitDir.exists()) {
            gitDir.mkdirs();
        }
        Git git = Git.init().setDirectory(gitDir).setBare(false).call();
    }
    public static void doClone(File gitDir, String gituri, CredentialsProvider credentialsProvider, String branch)
            throws GitAPIException {
        Git.cloneRepository().setURI(gituri)
                .setCredentialsProvider(credentialsProvider)
                .setDirectory(gitDir).setCloneAllBranches(false)
                .setBranch(branch).call().close();
    }
    public static PullResult doPull(File gitDir, String url, CredentialsProvider credentialsProvider) throws IOException,
            GitAPIException {
        Git git = Git.open(gitDir);
        return git.pull().setCredentialsProvider(credentialsProvider).call();
    }
    public static void doAdd(File gitDir, String... files)
            throws GitAPIException, IOException {
        try (Git git = Git.open(gitDir)) {
            AddCommand add = git.add();
            if (ArrayUtils.isEmpty(files)) {
                add.addFilepattern(".");
            } else {
                for (String file : files) {
                    add.addFilepattern(file);
                }
            }
            add.call();
        }
    }
    public static void doCreate(File gitDir, String branchName) throws GitAPIException, IOException {
        Git git = Git.open(gitDir);
        CreateBranchCommand create = git.branchCreate().setName(branchName);
        create.call();
    }
    public static void doCommit(File gitDir, String message) throws GitAPIException, IOException {
        Git git = Git.open(gitDir);
        CommitCommand commit = git.commit().setAll(false).setMessage(message);
        commit.call();
    }
    public static Iterable<PushResult> doPush(File gitDir, CredentialsProvider credentialsProvider) throws IOException, GitAPIException {
        Git git = Git.open(gitDir);
        PushCommand push = git.push().setCredentialsProvider(credentialsProvider);
        return push.call();
    }
}