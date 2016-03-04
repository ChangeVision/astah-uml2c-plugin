package com.change_vision.astah.extension.plugin.uml2c.codegenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.extension.plugin.uml2c.astahutil.AstahUtil;
import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.jude.api.inf.AstahAPI;

public class CodeGenerator {
    private static final String TEMPLATE_ENCODING = "UTF-8"; //"UTF-8", "Windows-31J"
    private static final String OUTPUT_ENCODING = "UTF-8"; //"UTF-8", "Windows-31J"
    private static final String TEMPLATE_DIR_PATH = "plugins/uml2c/";
    private static Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    private AbstractCModule cModule;

    public CodeGenerator(AbstractCModule cModule) {
        this.cModule = cModule;
        Velocity.setProperty("file.resource.loader.path", getTemplateSearchDirPath());
        //Velocity.setProperty("runtime.log.error.stacktrace", "true");
        //Velocity.setProperty("runtime.log.warn.stacktrace", "true");
        //Velocity.setProperty("runtime.log.info.stacktrace", "true");
        Velocity.init();
    }

    public void outputCode(String codePath, String templatePath) throws IOException {
        VelocityContext context = new VelocityContext();
        context.put("cModule", cModule);

        Template template = null;
        try {
            template = Velocity.getTemplate(templatePath, TEMPLATE_ENCODING);
        } catch (ResourceNotFoundException e) {
            throw e;
        }

        File file = new File(codePath);
        logger.info("Generating {} ({})", file.getName(), templatePath);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), OUTPUT_ENCODING)));
        template.merge(context, pw);
        pw.close();
    }

    public String getCModuleName() {
        return cModule.getName();
    }

    public String getTemplateSearchDirPath() {
        return AstahUtil.getFullPathOfConfigDir() + ",.";
    }

    public String getTemplateDirPath() {
        return TEMPLATE_DIR_PATH;
    }
}
