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
