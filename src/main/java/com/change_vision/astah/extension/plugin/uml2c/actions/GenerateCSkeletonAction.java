package com.change_vision.astah.extension.plugin.uml2c.actions;

import java.io.IOException;

import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.astah.extension.plugin.uml2c.codegenerator.CSkeletonGenerator;

public class GenerateCSkeletonAction extends GenerateCodeAction {
    @Override
    protected void generateCode(AbstractCModule cModule, String outputDirPath) throws IOException {
        CSkeletonGenerator cSkeletonGenerator = new CSkeletonGenerator(cModule);
        cSkeletonGenerator.outputHeader(outputDirPath);
        cSkeletonGenerator.outputSource(outputDirPath);

        if (!cModule.getClass().getSimpleName().equals("SingleInstanceCModule")) {
            cSkeletonGenerator.outputPrivateHeader(outputDirPath);
        }
    }
}
