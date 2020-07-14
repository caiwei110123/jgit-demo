package bakup;

import java.io.*;

/**
 * @Classname
 * @Description TODO
 * @Date 2020/7/13
 * @Created by cw
 */


public class CMDTest {

    public static void main(String[] args) {
        String cmdStr = "mspaint";// 打开画板
        cmdStr = "cd D:\\workspace\\jgittest & git clone git@git.code.tencent.com:caiwei110123/test-demo.git";// 重命名，DOS中不可
//        cmdStr = "ren 3.png 4.png";
//        cmdStr = "ipconfig";
//		executeCMD(cmdStr);/
        System.out.println(executeLocalCmd(cmdStr, null));
    }

    /**
     * 直接System.out
     * 测试OK
     * @param cmdStr  CMD命令字符
     */
    public static void executeCMD(String cmdStr) {

        Runtime run = Runtime.getRuntime();
        try {
//			Process process = run.exec("cmd.exe /k start " + cmdStr);
            Process process = run.exec("cmd.exe /c " + cmdStr);
            InputStream in = process.getInputStream();
            while (in.read() != -1) {
                System.out.println(in.read());
            }
            in.close();
            process.waitFor();
        } catch (Exception e) {
          e.printStackTrace();
        }

    }

    /**
     * 将日志输出到文件
     * 待测试
     * @param cmmands    命令数组adb logcat -v time > d:/adb.log { "adb",
     *                   "logcat","-v","time"}
     * @param logToFile  保存的文件
     * @param dirTodoCMD 在此目录下执行
     * @return
     */
    public static String executeCMDFile(String[] cmmands, String logToFile, String dirTodoCMD) {

        try {
            ProcessBuilder builder = new ProcessBuilder(cmmands);
            if (dirTodoCMD != null)
                builder.directory(new File(dirTodoCMD));
            builder.redirectErrorStream(true);
            builder.redirectOutput(new File(logToFile));
            Process process = builder.start();
            process.waitFor();
            // 得到命令执行后的结果
            InputStream is = process.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "GBK"));
            String line = null;
            StringBuffer sbBuffer = new StringBuffer();
            while ((line = buffer.readLine()) != null) {
                sbBuffer.append(line);
            }

            is.close();
            return sbBuffer.toString();
        } catch (Exception e) {
         e.printStackTrace();
        }
        return null;
    }

    /**
     * Windows执行本地命令行
     * 测试ok
     * @param cmd
     * @param workpath  在此目录下执行
     * @return
     */
    public static String executeLocalCmd(String cmd, File workpath) {
        try {
            String[] cmdA = { "cmd.exe", "/c", cmd };
            Process process = null;
            if(workpath==null){
                process = Runtime.getRuntime().exec(cmdA);
            }else{
                process = Runtime.getRuntime().exec(cmdA, null, workpath);
            }
//             LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream(),"GBK"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }


}