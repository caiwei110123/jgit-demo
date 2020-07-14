package bakup; /**
 * @Classname
 * @Description TODO
 * @Date 2020/7/13
 * @Created by cw
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    public static void main(String[] strings)
    {
//        System.out.println(execCommand("cmd.exe /c cd D:\\\\workspace\\\\jgittest & git clone git@git.code.tencent.com:caiwei110123/test-demo.git"));
        main1("cmd.exe /c cd D:\\workspace\\\\jgittest & git clone git@git.code.tencent.com:caiwei110123/test-demo.git > D://log.txt ");
//        main1("cmd.exe /c echo fwfwefwefw ");

    }
    public static void main1(String cmd) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String execCommand(String command)
    {
        String line = "";
        StringBuilder sb = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        Runtime.getRuntime().exec(command).getInputStream()));)
        {
            while ((line = bufferedReader.readLine()) != null)
                sb.append(line + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid command.";
        }

        return sb.toString();
    }
}