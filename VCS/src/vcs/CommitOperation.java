/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public class CommitOperation extends VcsOperation {

    public CommitOperation(final OperationType type,
            final ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public final int execute(final Vcs vcs) {
        // daca stage-ul este gol intoarce eroarea aferenta
        if (vcs.getContentStage().equals("")) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        // retin in message mesajul commit-ului
        String message = "";
        for (int i = 2; i < operationArgs.size() - 1; i++) {
            message += operationArgs.get(i);
            message += " ";
        }
        message += operationArgs.get(operationArgs.size() - 1);

        // adaugam commit-ul pe branch-ul curent cu mesajul si stage-ul curent
        Commit newCommit = new Commit(message, vcs.getStage());
        vcs.addCommit(newCommit);
        // resetam continutul Stage-ului
        vcs.getStage().resetContent();

        return ErrorCodeManager.OK;
    }
}
