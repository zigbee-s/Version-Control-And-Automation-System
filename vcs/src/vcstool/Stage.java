package vcstool;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Public class Stage
 * Keeps a record of files added and remvoved since last commit
 * 
 * @author xiaofei
 */

public class Stage implements Serializable {
    private Commit latestCommit;
    private ArrayList<String> filesNewStaged;
    private ArrayList<String> filesMarkedForRemoval;
    /**
     * key: filename, value: directory
     * null value means the file has not been added to commits
     * or the file has been modified since last commit
     */
    private Map<String, String> stagedFiles;

    public Stage(Commit latestCommit) {
        this.latestCommit = latestCommit;
        filesNewStaged = new ArrayList<>();
        filesMarkedForRemoval = new ArrayList<>();
        stagedFiles = new HashMap<>();
        if (latestCommit.getAddedFiles() != null) {
            stagedFiles.putAll(latestCommit.getAddedFiles());
        }
    }

    public Stage() {
        filesNewStaged = new ArrayList<>();
        filesMarkedForRemoval = new ArrayList<>();
        stagedFiles = new HashMap<>();
    }

    public Commit getLatestCommit() {
        return this.latestCommit;
    }

    // public ArrayList<String> getFilesNewStaged() {
    // return filesNewStaged;
    // }

    // public ArrayList<String> getFilesMarkedForRemoval() {
    // return filesMarkedForRemoval;
    // }

    public Map<String, String> getStagedFiles() {
        return this.stagedFiles;
    }

    public void add(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("File does not exist");
            return;
        }
        if (unChangedFromLastCommit(fileName)) {
            System.out.println("File has not been modified since the last commit");
        }
        stagedFiles.put(fileName, null);
        filesNewStaged.add(fileName);
    }

    // public void remove(String fileName) {
    // if (stagedFiles.containsKey(fileName)) {
    // stagedFiles.remove(fileName);
    // filesMarkedForRemoval.add(fileName);
    // } else {
    // System.out.println("No reason to remove the file.");
    // }
    // }

    private boolean unChangedFromLastCommit(String fileName) {
        File file = new File(fileName);
        if (latestCommit.contains(fileName)) {
            File backupFile = latestCommit.getFile(fileName);
            if (backupFile.lastModified() >= file.lastModified()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Staged Files ===\n");
        for (String file : filesNewStaged) {
            sb.append(file + "\n");
        }
        sb.append("\n");
        sb.append("=== Files Marked for Removal ===\n");
        for (String file : filesMarkedForRemoval) {
            sb.append(file + "\n");
        }
        return sb.toString();
    }
}
