package vcstool;

import java.io.File;
import java.io.Serializable;

public class Branch implements Serializable {
    private final String branchName;
    private Commit head;
    private Stage currentStage;

    public Branch(String name, Commit head) {
        this.branchName = name;
        this.head = head;
        this.currentStage = new Stage(head);
    }

    public String getBranchName() {
        return branchName;
    }

    public Commit getHead() {
        return head;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void commitNoMsg() {
        this.head = new Commit(currentStage);
        currentStage = new Stage(head);
    }

    public void commitMsg(String msg) {
        this.head = new Commit(currentStage, msg);
        currentStage = new Stage(head);
    }

    public void stageFile(String fileName) {
        currentStage.add(fileName);
    }
}
