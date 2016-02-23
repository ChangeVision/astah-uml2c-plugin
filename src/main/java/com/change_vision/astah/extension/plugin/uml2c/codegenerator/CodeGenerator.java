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

import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.jude.api.inf.AstahAPI;

class CodeGenerator {
    private static final String TEMPLATE_ENCODING = "Windows-31J"; //"UTF-8", "Windows-31J"
    private static final String OUTPUT_ENCODING = "Windows-31J"; //"UTF-8", "Windows-31J"

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

        Template template = Velocity.getTemplate(templatePath, TEMPLATE_ENCODING);

        File file = new File(codePath);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), OUTPUT_ENCODING)));
        template.merge(context, pw);
        pw.close();
    }

    public String getCModuleName() {
        return cModule.getName();
    }

    public String getTemplateSearchDirPath() {
        return getAstahConfigPath() + ",.";
    }
    
    private String getAstahConfigPath() {
        String edition;
        try {
            edition = AstahAPI.getAstahAPI().getProjectAccessor().getAstahEdition();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        String userHomeDir = System.getProperty("user.home");
        return userHomeDir + "/.astah/" + edition;
    }

    public String getTemplateDirPath() {
        return templateDirPath;
    }

    private AbstractCModule cModule;
    private static final String templateDirPath = "plugins/uml2c/";
}
