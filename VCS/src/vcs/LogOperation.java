/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;
import utils.ErrorCodeManager;
import utils.OperationType;

public class LogOperation extends VcsOperation {

    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(final Vcs vcs) {
        // afiseaza toate commit-uri de pe branch-ul curent
        for (Commit commit : vcs.getCurrentBranch().getCommits()) {
            vcs.getOutputWriter().write("Commit id: "
                            + commit.getId() + "\n");
            vcs.getOutputWriter().write("Message: "
                            + commit.getMessage() + "\n");
            if (commit != vcs.getCurrentBranch().getCommits().
                        get(vcs.getCurrentBranch().getCommits().size() - 1)) {
                vcs.getOutputWriter().write("\n");
            }
        }
        return ErrorCodeManager.OK;
    }

}
