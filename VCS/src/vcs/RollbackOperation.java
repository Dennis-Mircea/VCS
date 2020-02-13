/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;

import filesystem.Directory;
import filesystem.File;
import utils.ErrorCodeManager;
import utils.OperationType;

public class RollbackOperation extends VcsOperation {

    public RollbackOperation(final OperationType type,
                        final ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(final Vcs vcs) {
        // seteaza HEAD-ul pe ultimul commit din branch-ul curent
        vcs.setHead(vcs.getCurrentBranch().getLastCommit());

        // sterge toate fisiere si directoarele din directorul curent
        for (File file : vcs.getStage().getAllFiles()) {
            vcs.getActiveSnapshot().getCurrentDir().remove(file.getName());
        }
        for (Directory dir : vcs.getStage().getAllDirectories()) {
            vcs.getActiveSnapshot().getCurrentDir().remove(dir.getName());
        }

        // restaureaza toate fisierele si directoarele din ultimul commit
        for (File file : vcs.getHead().getStage().getAllFiles()) {
            vcs.getActiveSnapshot().getCurrentDir().addEntity(file);
        }
        for (Directory dir : vcs.getHead().getStage().getAllDirectories()) {
            vcs.getActiveSnapshot().getCurrentDir().addEntity(dir);
        }
        // reseteaza stage-ul
        vcs.getStage().resetStage();
        return ErrorCodeManager.OK;
    }
}
