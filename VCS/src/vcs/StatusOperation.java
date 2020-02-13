/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public class StatusOperation extends VcsOperation {
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }
    @Override
    public int execute(Vcs vcs) {
        // afiseaza conform formei din enunt
        vcs.getOutputWriter().write("On branch: " + vcs.getCurrentBranch().getName() + "\n");
        vcs.getOutputWriter().write("Staged changes:\n");
        vcs.getOutputWriter().write(vcs.getContentStage());

        return ErrorCodeManager.OK;
    }
}
