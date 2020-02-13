/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import utils.IDGenerator;

public final class Commit {
    private int id; // id-ul commit-ului
    private String message; // mesajul commit-ului
    private Staging stage;  // stage-ul retinut de commit

    // constructor pentru first commit
    public Commit() {
        this.id = IDGenerator.generateCommitID();
        this.message = "First commit";
        this.stage = new Staging();
    }
    public Commit(final String message, final Staging stage) {
        this.id = IDGenerator.generateCommitID();
        this.message = message;
        this.stage = new Staging();
        this.stage.copy(stage);
    }

    // functii cu care clasa Commit interactioneaza cu celelalte clase
    public int getId() {
        return this.id;
    }
    public String getMessage() {
        return this.message;
    }
    public Staging getStage() {
        return this.stage;
    }
    public void resetStage() {
        this.stage.getAllFiles().clear();
        this.stage.getAllDirectories().clear();;
    }
}
