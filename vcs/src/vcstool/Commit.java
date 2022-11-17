package vcstool;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

public class Commit implements Serializable {

    private static final String Prefix_Dir = ".vcs/commits/";
    private static int NumCommit = 0; // initialized to 0
    private int id;
    private String commitMsg;
    private Date commitTime;
    private String commit_Dir; // unique directory to store the backup files
    private Map<String, String> addedFiles; // map staged files to its directory
    private Commit previousCommit;

    public Commit(Stage currentStage) {
        id = NumCommit + 1;
        commitTime = new Date();
        commit_Dir = Prefix_Dir + commitTime.hashCode() + "/";
        if (currentStage != null) {
            addedFiles = currentStage.getStagedFiles();
            previousCommit = currentStage.getLatestCommit();
        }
        NumCommit = NumCommit + 1;
        saveFiles();
    }

    public Commit(Stage currentStage, String msg) {
        this(currentStage);
        commitMsg = msg;
    }

    // public static int getNumCommit() {
    // return NumCommit;
    // }

    public int getId() {
        return id;
    }

    // public String getCommitMsg() {
    // return commitMsg;
    // }

    public String getCommitTime() {
        return commitTime.toString();
    }

    // //public String getCommit_Dir() {
    // return commit_Dir;
    // }

    public Map<String, String> getAddedFiles() {
        return addedFiles;
    }

    // //public Commit getPreviousCommit() {
    // return previousCommit;
    // }

    public boolean contains(String fileName) {
        if (addedFiles == null) {
            return false;
        }
        return addedFiles.containsKey(fileName);
    }

    public File getFile(String fileName) {
        String path = addedFiles.get(fileName) + fileName;
        File backup = new File(path);
        return backup;
    }

    // //public void restoreFile(String fileName) {
    // File backupFile = getFile(fileName);
    // File file = new File(fileName);
    // try {
    // Files.copy(backupFile.toPath(), file.toPath(), REPLACE_EXISTING,
    // COPY_ATTRIBUTES);
    // } catch (IOException e) {
    // String msg = "IOException when restoring file";
    // System.out.println(msg);
    // }
    // }

    // public void restoreAllFiles() {
    // Collection<String> fileNames = addedFiles.keySet();
    // for (String fileName : fileNames) {
    // restoreFile(fileName);
    // }
    // }

    // public void copyFile(Commit other, String fileName, String fileNameCopy) {
    // File originFile = other.getFile(fileName);
    // String copyPath = commit_Dir + fileNameCopy;
    // File copyFile = new File(copyPath);
    // try {
    // Files.copy(originFile.toPath(), copyFile.toPath(), REPLACE_EXISTING,
    // COPY_ATTRIBUTES);
    // } catch (IOException e) {
    // String msg = "IOException when copying file";
    // System.out.println(msg);
    // }
    // this.addedFiles.put(fileNameCopy, commit_Dir);
    // }

    // public boolean modified(Commit other, String fileName) {
    // File originFile = other.getFile(fileName);
    // File thisFile = this.getFile(fileName);
    // return thisFile.lastModified() > originFile.lastModified();
    // }

    private void saveFiles() {
        // make directory
        File dir = new File(commit_Dir);
        dir.mkdirs();
        if (addedFiles == null) {
            return;
        }
        for (String fileName : addedFiles.keySet()) {
            String fileToSave;
            if (addedFiles.get(fileName) == null) {
                fileToSave = fileName;
            } else {
                fileToSave = addedFiles.get(fileName) + fileName;
            }
            File file = new File(fileToSave);
            String path = commit_Dir + fileName;
            File backupFile = new File(path);
            try {
                Files.copy(file.toPath(), backupFile.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
            } catch (IOException e) {
                String msg = "IOException when saving files";
                System.out.println(msg);
            }
            addedFiles.put(fileName, commit_Dir);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("====\n");
        sb.append("Commit " + this.id + ".");
        sb.append("\n");
        sb.append(this.commitTime.toString());
        sb.append("\n");
        sb.append(this.commitMsg);
        return sb.toString();
    }
}
