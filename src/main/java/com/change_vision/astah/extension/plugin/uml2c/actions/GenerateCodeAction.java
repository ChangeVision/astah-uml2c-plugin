package com.change_vision.astah.extension.plugin.uml2c.actions;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.velocity.exception.ResourceNotFoundException;

import com.change_vision.astah.extension.plugin.uml2c.Messages;
import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.astah.extension.plugin.uml2c.cmodule.CModuleFactory;
import com.change_vision.astah.extension.plugin.uml2c.codegenerator.CodeGenerator;
import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

public abstract class GenerateCodeAction implements IPluginActionDelegate {
    public Object run(IWindow window) throws UnExpectedException {
        try {
            AstahAPI api = AstahAPI.getAstahAPI();
            ProjectAccessor projectAccessor = api.getProjectAccessor();
            @SuppressWarnings("unused")
            IModel iCurrentProject = projectAccessor.getProject();

            IElement[] iElements = getSelectedElements(api);

            // Check the selected elements
            if ((iElements.length != 1) || !(iElements[0] instanceof IClass)) {
                JOptionPane.showMessageDialog(window.getParent(),
                        Messages.getMessage("message.select_class"), 
                        Messages.getMessage("title.select_class"),
                        JOptionPane.WARNING_MESSAGE);
                return null;
            }
            IClass iClass = (IClass) iElements[0];

            AbstractCModule cModule = CModuleFactory.getCModule(iClass);
            System.out.printf("Module is %s.\n", cModule.getClass().getSimpleName());
            System.out.printf("path is %s.\n", projectAccessor.getProjectPath());

            String outputDirPath = getOutputDirPath(window, projectAccessor);
            if (outputDirPath == null) return null; //canceled

            generateCode(cModule, outputDirPath);

            JOptionPane.showMessageDialog(window.getParent(), 
                    Messages.getMessage("message.finish_generating"), 
                    Messages.getMessage("title.finish_generating"),
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(window.getParent(), 
                    Messages.getMessage("message.project_not_found"), 
                    Messages.getMessage("title.project_not_found"),
                    JOptionPane.WARNING_MESSAGE);
        } catch (ResourceNotFoundException e) {
            JOptionPane.showMessageDialog(window.getParent(), 
                    Messages.getMessage("message.not_found_template", CodeGenerator.getAstahConfigPath(), e.getLocalizedMessage()),
                    Messages.getMessage("title.not_found_template"),
                    JOptionPane.WARNING_MESSAGE);
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(window.getParent(),
                    Messages.getMessage("message.unexpected_exception", e.getLocalizedMessage(), e.getStackTrace()),
                    Messages.getMessage("title.unexpected_exception"),
                    JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    private String getOutputDirPath(IWindow window, ProjectAccessor projectAccessor)
            throws ProjectNotFoundException {
        String outputDirPath = new File(projectAccessor.getProjectPath()).getParent();
        if (outputDirPath == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle(Messages.getMessage("title.choose_output_dir"));
            int selected = fileChooser.showSaveDialog(window.getParent());
            if (selected != JFileChooser.CANCEL_OPTION) {
                outputDirPath = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                return null; //canceled
            }
        }
        return outputDirPath;
    }

    private IElement[] getSelectedElements(AstahAPI api) throws InvalidUsingException {
        IViewManager iViewManager = api.getViewManager();
        IDiagramViewManager iDiagramViewManager = iViewManager.getDiagramViewManager();
        return iDiagramViewManager.getSelectedElements();
    }

    protected abstract void generateCode(AbstractCModule cModule, String outputDirPath) throws IOException;
}