//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.smiledlScanpressuretext;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShellUtils {
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    public ShellUtils() {
    }

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : (String[])commands.toArray(new String[0]), isRoot, true);
    }

    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : (String[])commands.toArray(new String[0]), isRoot, isNeedResultMsg);
    }

    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands != null && commands.length != 0) {
            Process process = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = null;
            StringBuilder errorMsg = null;
            DataOutputStream os = null;

            try {
                process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
                os = new DataOutputStream(process.getOutputStream());
                String[] var10 = commands;
                int var11 = commands.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String command = var10[var12];
                    if (command != null) {
                        os.write(command.getBytes());
                        os.writeBytes("\n");
                        os.flush();
                    }
                }

                os.writeBytes("exit\n");
                os.flush();
                result = process.waitFor();
                if (isNeedResultMsg) {
                    successMsg = new StringBuilder();
                    errorMsg = new StringBuilder();
                    successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    String s;
                    while((s = successResult.readLine()) != null) {
                        successMsg.append(s);
                    }

                    while((s = errorResult.readLine()) != null) {
                        errorMsg.append(s);
                    }
                }
            } catch (IOException var24) {
                var24.printStackTrace();
            } catch (Exception var25) {
                var25.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }

                    if (successResult != null) {
                        successResult.close();
                    }

                    if (errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException var23) {
                    var23.printStackTrace();
                }

                if (process != null) {
                    process.destroy();
                }

            }

            return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null : errorMsg.toString());
        } else {
            return new CommandResult(result, (String)null, (String)null);
        }
    }

    public static class CommandResult {
        public int result;
        public String successMsg;
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
