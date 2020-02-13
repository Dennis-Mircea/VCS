/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package vcs;

import java.util.ArrayList;
import filesystem.File;
import filesystem.Directory;

public final class Staging {
    private String content; // continutul(mesajul) Stage-ului
    // toate fiind din directorul curent;
    private ArrayList<File> allFiles;   // toate fisierele care au fost create
    private ArrayList<Directory> allDirectories; // same like allFiles

    public Staging() {
        content = "";
        allFiles = new ArrayList<File>();
        allDirectories = new ArrayList<Directory>();
    }

    // functie care copiaza continutul unui Stage in alt Stage
    public void copy(final Staging newStage) {
        this.content = newStage.content;
        for (int i = 0; i < newStage.getAllFiles().size(); i++) {
            this.allFiles.add(i, newStage.getAllFiles().get(i));
        }
        for (int i = 0; i < newStage.getAllDirectories().size(); i++) {
            this.allDirectories.add(i, newStage.getAllDirectories().get(i));
        }
    }

    // functii cu care clasa Stage interactioneaza cu celelalte clase
    public String getContent() {
        return this.content;
    }
    public void setContent(final String newContent) {
        this.content += newContent;
    }
    public void resetStage() {
        this.content = "";
        allFiles.clear();
        allDirectories.clear();
    }
    public void resetContent() {
        this.content = "";
    }
    public ArrayList<File> getAllFiles() {
        return this.allFiles;
    }
    public ArrayList<Directory> getAllDirectories() {
        return this.allDirectories;
    }
    public void addFile(final File newFile) {
        this.allFiles.add(newFile);
    }
    public void addDirectory(final Directory newDir) {
        this.allDirectories.add(newDir);
    }
}
