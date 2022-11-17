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

    // public void setHead(Commit head) {
    // this.head = head;
    // }

    public Stage getCurrentStage() {
        return currentStage;
    }

    // public void setCurrentStage(Stage stage) {
    // this.currentStage = stage;
    // }

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

    // public void removeFile(String fileName) {
    // currentStage.remove(fileName);
    // }

    // /** merge this branch with another branch. */
    // public void merge(Branch other) {
    // Commit otherHead = other.getHead();
    // Commit split = splitPoint(this, other);
    // for (String fileName : otherHead.getAddedFiles().keySet()) {
    // if ((!head.contains(fileName)) || (!head.modified(split, fileName))) {
    // head.copyFile(otherHead, fileName, fileName);
    // } else {
    // String copyName = fileName + ".conflict";
    // head.copyFile(otherHead, fileName, copyName);
    // }
    // }
    // }

    /**
     * helper function
     * find the merge point of branch and current branch.
     */
    // private static Commit splitPoint(Branch b1, Branch b2) {
    // int b1Len = b1.length();
    // int b2Len = b2.length();
    // if (b2Len > b1Len) {
    // return splitPoint(b2, b1);
    // }
    // Commit c1 = b1.getHead();
    // Commit c2 = b2.getHead();
    // for (int i = 0; i < b1Len - b2Len; i++) {
    // c1 = c1.getPreviousCommit();
    // }
    // while (!c1.equals(c2)) {
    // c1 = c1.getPreviousCommit();
    // c2 = c2.getPreviousCommit();
    // }
    // return c1;
    // }

    /**
     * helper function
     * returns the number of commits in branch.
     */
    // private int length() {
    // int len = 0;
    // Commit cur = head;
    // while (cur != null) {
    // len++;
    // cur = cur.getPreviousCommit();
    // }
    // return len;
    // }
}
