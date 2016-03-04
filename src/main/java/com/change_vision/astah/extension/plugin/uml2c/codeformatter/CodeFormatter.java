package com.change_vision.astah.extension.plugin.uml2c.codeformatter;

import java.io.File;
import java.io.IOException;

import java.lang.InterruptedException;
import java.lang.ProcessBuilder;
import java.lang.Process;

import javax.management.RuntimeErrorException;

import com.change_vision.astah.extension.plugin.uml2c.astahutil.AstahUtil;

public class CodeFormatter {

    public static void format(String codePath) throws IOException, InterruptedException {
        if (!hasUncrustify()) {
            return;
        }

        ProcessBuilder pb = new ProcessBuilder(
                                    getUncrustifyExePath(),
                                    "-c", getUncrustifyCfgPath(),
                                    "-f", codePath,
                                    "-o", codePath);

        Process process = pb.start();
        int procRet = process.waitFor();
        if (procRet != 0) {
            throw new RuntimeErrorException(new Error("Uncrustify fails: " + String.valueOf(procRet)));
        }

        // Cleanup temporary files.
        new File(codePath + ".unc-backup.md5~").delete();
        new File(codePath + ".unc-backup~").delete();
    }

    private static boolean hasUncrustify() {
        String astahConfigPath = AstahUtil.getFullPathOfConfigDir();

        File exeFile = new File(getUncrustifyExePath());
        if (!exeFile.exists()) {
            return false;
        }

        File cfgFile = new File(getUncrustifyCfgPath());
        if (!cfgFile.exists()) {
            return false;
        }

        return true;
    }

    private static String getUncrustifyExePath() {
        return AstahUtil.getFullPathOfConfigDir() + "/plugins/uml2c/uncrustify/uncrustify.exe";
    };

    private static String getUncrustifyCfgPath() {
        return AstahUtil.getFullPathOfConfigDir() + "/plugins/uml2c/uncrustify/custom.cfg";
    };
}
