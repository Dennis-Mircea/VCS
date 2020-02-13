/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;
import utils.ErrorCodeManager;
import utils.OperationType;

public class CreateBranchOperation extends VcsOperation {

    public CreateBranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        boolean exists = false;
        // verifica daca branch-ul exista deja
        for (Branch branch : vcs.getAllBranches()) {
            if (operationArgs.get(1).equals(branch.getName())
                    || operationArgs.get(1).equals("master")) {
                exists = true;
                break;
            }
        }
        if (exists) {
            // daca branch-ul exista intoarce eroarea aferenta
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        } else {
            // creaza un nou branch cu numele specificat
            Branch newBranch = new Branch(operationArgs.get(1),
                            vcs.getActiveSnapshot().getCurrentDir());
            // adauga branch-ul in lista de branch-uri exista
            vcs.addBranch(newBranch);
        }
        return ErrorCodeManager.OK;
    }
}
