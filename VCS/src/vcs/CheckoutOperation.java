/*
 *  Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;
import filesystem.Directory;
import filesystem.File;
import utils.ErrorCodeManager;
import utils.OperationType;

public class CheckoutOperation extends VcsOperation {
    private static final int THREE = 3;
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }
    @Override
    public int execute(Vcs vcs) {
        // daca stage-ul este gol
        if (vcs.getContentStage().length() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }
        boolean exists = false;
        // daca operatia este de tipul vcs checkout -c commitId
        if (operationArgs.size() == THREE) {
            for (int i = 0; i < vcs.getCommits().size(); i++) {
                if (Integer.parseInt(operationArgs.get(2)) == vcs.getCommits().get(i).getId()) {
                    exists = true;
                    // daca exita commit-ul, sterge commit-urile ulterioare lui
                    for (int j = i + 1; j < vcs.getCommits().size(); j++) {
                        vcs.getCommits().remove(j);
                    }
                    break;
                }
            }
        }
        // daca commitId nu exista intoarce eroare aferenta
        if (!exists && operationArgs.size() == THREE) {
            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }
        if (operationArgs.size() == THREE) {
            Commit commit = vcs.getCommits().get(vcs.getCommits().size() - 1);
            if (Integer.parseInt(operationArgs.get(2)) == commit.getId()) {
                exists = true;
                // seteaza HEAD-ul
                vcs.setHead(commit);
                for (File file : vcs.getStage().getAllFiles()) {
                    vcs.getActiveSnapshot().getCurrentDir().remove(file.getName());
                }
                for (Directory dir : vcs.getStage().getAllDirectories()) {
                    vcs.getActiveSnapshot().getCurrentDir().remove(dir.getName());
                }
                for (File file : vcs.getHead().getStage().getAllFiles()) {
                    vcs.getActiveSnapshot().getCurrentDir().addEntity(file);
                }
                for (Directory dir : vcs.getHead().getStage().getAllDirectories()) {
                    vcs.getActiveSnapshot().getCurrentDir().addEntity(dir);
                }
                // goleste Stage-ul
                vcs.getStage().resetStage();
                return ErrorCodeManager.OK;
            }
        }
        // daca comandata este de tipul vcs checkout branchName
        // verifica daca branch-ul exista
        for (Branch branch : vcs.getAllBranches()) {
            if (operationArgs.get(1).equals(branch.getName())) {
                exists = true;
                // daca exista, schimba branch-ul
                vcs.setCurrentBranch(branch);
            }
        }
        // daca branch-ul exista intoarce eroarea aferenta
        if (!exists) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        return ErrorCodeManager.OK;
    }
}
