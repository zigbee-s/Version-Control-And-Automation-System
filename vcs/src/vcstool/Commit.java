package vcstool;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.io.IOException;
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
        
        commitTime = new Date();
        if (currentStage != null) {
            addedFiles = currentStage.getStagedFiles();
            previousCommit = currentStage.getLatestCommit();
            id = previousCommit.id+1;
        }
        else{
            id = NumCommit + 1;
        }
        commit_Dir = Prefix_Dir + id + "/";
        saveFiles();
    }

    public Commit(Stage currentStage, String msg) {
        this(currentStage);
        commitMsg = msg;
    }

    public int getId() {
        return id;
    }

    public String getCommitTime() {
        return commitTime.toString();
    }

    public Commit getPreviousCommit(){
        return previousCommit;
    }

    public Map<String, String> getAddedFiles() {
        return addedFiles;
    }

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

    private void saveFiles() {
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
