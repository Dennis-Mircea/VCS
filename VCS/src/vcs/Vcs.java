/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;
import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;

public final class Vcs implements Visitor {
    private Branch currentBranch;   // branch-ul curent unde este tinutsi HEAD
    private ArrayList<Branch> allBranches;  // toate branch-urile
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private Staging currentStage;   // Stage-ul activ
    private Commit head;    // pointerul HEAD

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(final OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);

        //TODO other initialisations
        this.currentBranch = new Branch();
        this.allBranches = new ArrayList<Branch>();
        this.allBranches.add(currentBranch);
        this.currentStage = new Staging();
        this.head = this.currentBranch.getLastCommit();
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(final FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(final VcsOperation vcsOperation) {
        //TODO
        return vcsOperation.execute(this);
    }

    //TODO methods through which vcs operations interact with this
    public Branch getCurrentBranch() {
        return currentBranch;
    }
    public OutputWriter getOutputWriter() {
        return this.outputWriter;
    }
    public FileSystemSnapshot getActiveSnapshot() {
        return this.activeSnapshot;
    }
    public ArrayList<Branch> getAllBranches() {
        return this.allBranches;
    }
    public void addBranch(final Branch branch) {
        this.allBranches.add(branch);
    }
    public String getContentStage() {
        return this.currentStage.getContent();
    }
    public Staging getStage() {
        return this.currentStage;
    }
    public void addContentStage(final String newContent) {
        this.currentStage.setContent(newContent);
    }
    public Commit getHead() {
        return this.head;
    }
    public void setHead() {
        this.head = this.currentBranch.getLastCommit();
    }
    public void setHead(final Commit newHead) {
        this.head = newHead;
    }
    public void addCommit(final Commit newCommit) {
        this.currentBranch.addCommit(newCommit);
    }
    public ArrayList<Commit> getCommits() {
        return this.currentBranch.getCommits();
    }
    public void setCurrentBranch(final Branch branch) {
        this.currentBranch = branch;
    }
    public void resetCommits() {
        for (int i = 1; i < this.currentBranch.getCommits().size() - 1; i++) {
            this.currentBranch.getCommits().remove(i);
        }
    }
    public ArrayList<Commit> getFirstandLastCommit() {
        ArrayList<Commit> commit = new ArrayList<Commit>();
        commit.add(this.currentBranch.getCommits().get(0));
        commit.add(this.currentBranch.getLastCommit());
        return commit;
    }
}
