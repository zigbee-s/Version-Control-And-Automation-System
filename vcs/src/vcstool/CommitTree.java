package vcstool;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class CommitTree implements Serializable {
    private static final String WarningMsg = "Warning: The command you entered may alter the files in your working directory./n"
            +
            "Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)";
    private Map<String, Branch> branchMap; // map branch name to branch
    private Branch currentBranch; // stores a reference to the current Branch
    private Map<Integer, Commit> allCommits;
    private Map<String, ArrayList<Integer>> commitMsgToID;

    private CommitTree() {
        branchMap = new HashMap<>();
        allCommits = new HashMap<>();
        commitMsgToID = new HashMap<>();
    }

    public Map<String, Branch> getBranchMap() {
        return branchMap;
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }

    public static CommitTree commitTreeInit() {
        CommitTree ct = new CommitTree();
        String msg = "initial commit";
        String bname = "master";
        Commit initCommit = new Commit(null, msg);
        Branch initBranch = new Branch(bname, initCommit);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(initCommit.getId());
        ct.currentBranch = initBranch;
        ct.branchMap.put(bname, initBranch);
        ct.allCommits.put(initCommit.getId(), initCommit);
        ct.commitMsgToID.put(msg, ids);
        return ct;
    }

    public void add(String fileName) {
        currentBranch.stageFile(fileName);
    }

    public void commit(String msg) {
        if (msg.length() > 0) {
            currentBranch.commitMsg(msg);
        } else {
            currentBranch.commitNoMsg();
        }
        Commit cur = currentBranch.getHead();
        allCommits.put(cur.getId(), cur);
        ArrayList<Integer> temp = commitMsgToID.get(msg);
        if (temp == null) {
            temp = new ArrayList<>();
        }
        temp.add(cur.getId());
        commitMsgToID.put(msg, temp);
    }

    // public void remove(String fileName) {
    // currentBranch.removeFile(fileName);
    // }

    // public void printlog() {
    // Commit currentCommit = currentBranch.getHead();
    // while (currentCommit != null) {
    // System.out.println(currentCommit.toString());
    // System.out.println();
    // currentCommit = currentCommit.getPreviousCommit();
    // }
    // }

    // public void printlogGlobal() {
    // for (int key : allCommits.keySet()) {
    // // Commit c = allCommits.get(key);
    // System.out.println(c.toString());
    // System.out.println();
    // }
    // }

    // public void find(String msgTofind) {
    // if (!commitMsgToID.containsKey(msgTofind)) {
    // System.out.println("Found no commit with that message.");
    // return;
    // }
    // for (int id : commitMsgToID.get(msgTofind)) {
    // System.out.println(id);
    // }
    // }

    // public void checkout(String name) {
    // if ((!branchMap.containsKey(name)) &&
    // (!currentBranch.getHead().contains(name))) {
    // System.out.println("File does not exist in the most recent commit, or no such
    // branch exists.");
    // } else {
    // System.out.println(WarningMsg);
    // Scanner in = new Scanner(System.in);
    // String answer = in.nextLine();
    // if (!answer.equals("yes")) {
    // return;
    // }
    // if (branchMap.containsKey(name)) {
    // currentBranch = branchMap.get(name);
    // Commit c = currentBranch.getHead();
    // c.restoreAllFiles();
    // return;
    // }
    // if (currentBranch.getHead().contains(name)) {
    // currentBranch.getHead().restoreFile(name);
    // return;
    // }
    // }
    // }

    // public void checkout(int id, String name) {
    // if (!allCommits.containsKey(id)) {
    // System.out.println("No commit with that id exists.");
    // return;
    // }
    // Commit commit = allCommits.get(id);
    // if (!commit.contains(name)) {
    // System.out.println("File does not exist in that commit.");
    // return;
    // }
    // commit.restoreFile(name);
    // }

    // public void addNewBranch(String branchName) {
    // if (branchMap.containsKey(branchName)) {
    // System.out.println("A branch with that name already exists.");
    // return;
    // }
    // Commit head = currentBranch.getHead();
    // Branch branch = new Branch(branchName, head);
    // branchMap.put(branchName, branch);
    // }

    // public void removeBranch(String branchName) {
    // if (currentBranch.getBranchName().equals(branchName)) {
    // System.out.println("Cannot remove the current branch.");
    // return;
    // }
    // if (!branchMap.containsKey(branchName)) {
    // System.out.println("A branch with that name does not exist.");
    // return;
    // }
    // branchMap.remove(branchName);
    // }

    // public void reset(int id) {
    // if (!allCommits.containsKey(id)) {
    // System.out.println("No commit with that id exists.");
    // return;
    // }
    // System.out.println(WarningMsg);
    // Scanner in = new Scanner(System.in);
    // String answer = in.nextLine();
    // if (!answer.equals("yes")) {
    // return;
    // }
    // Commit commit = allCommits.get(id);
    // commit.restoreAllFiles();
    // currentBranch.setHead(commit);
    // currentBranch.setCurrentStage(null);
    // }

    // public void merge(String branchName) {
    // if (!branchMap.containsKey(branchName)) {
    // System.out.println(" A branch with that name does not exist.");
    // return;
    // }
    // if (branchName.equals(currentBranch.getBranchName())) {
    // System.out.println("Cannot merge a branch with itself.");
    // return;
    // }
    // System.out.println(WarningMsg);
    // Scanner in = new Scanner(System.in);
    // String answer = in.nextLine();
    // if (!answer.equals("yes")) {
    // return;
    // }
    // Branch other = branchMap.get(branchName);
    // currentBranch.merge(other);
    // }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Branches ===\n");
        sb.append("*" + currentBranch.getBranchName() + "\n");
        for (String branchName : branchMap.keySet()) {
            sb.append(branchName);
            sb.append("\n");
        }
        sb.append("\n");
        sb.append(currentBranch.getCurrentStage().toString());
        return sb.toString();
    }
}
