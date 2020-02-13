/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;
import filesystem.Directory;

public class Branch {
    private String name; // numele branch-ului
    private Directory root;
    private Directory currentDir;
    private ArrayList<Commit> allCommits;   // toate commit-urile din branch

    public Branch() {
        name = new String("master");
        root = new Directory("root", null);
        currentDir = root;
        allCommits = new ArrayList<Commit>();
        Commit firstCommit = new Commit();
        allCommits.add(firstCommit);
    }

    public Branch(final String name, final  Directory newDir) {
        this.name = new String(name);
        this.currentDir = newDir;
        allCommits = new ArrayList<Commit>();
    }

    // functii cu care clasa Branch interationeaza cu celelalte clase
    public final String getName() {
        return this.name;
    }
    public final void addCommit(final Commit newCommit) {
        this.allCommits.add(newCommit);
    }
    public final ArrayList<Commit> getCommits() {
        return this.allCommits;
    }
    public final Commit getLastCommit() {
        return this.allCommits.get(this.allCommits.size() - 1);
    }
}
