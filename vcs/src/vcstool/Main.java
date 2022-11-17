package vcstool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Main {

    private static final String GITLET_DIR = ".vcs/";

    private static CommitTree init() {
        File f = new File(GITLET_DIR);
        if (f.exists()) {
            System.out.println("A gitlet version control system already exists in the current directory");
            return null;
        }
        f.mkdirs();
        return CommitTree.commitTreeInit();
    }

    private static CommitTree tryLoadTree() {
        CommitTree commitTree = null;
        File commitTreeFile = new File(GITLET_DIR + "gitletVCS.ser");
        if (commitTreeFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(commitTreeFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                commitTree = (CommitTree) objectIn.readObject();
            } catch (IOException e) {
                String msg = "IOException while loading commitTree.";
                System.out.println(msg);
            } catch (ClassNotFoundException e) {
                String msg = "ClassNotFoundException while loading commitTree.";
                System.out.println(msg);
            }
        }
        return commitTree;
    }

    public static void saveTree(CommitTree commitTree) {
        if (commitTree == null) {
            return;
        }
        try {
            File commitTreeFile = new File(GITLET_DIR + "gitletVCS.ser");
            FileOutputStream fileOut = new FileOutputStream(commitTreeFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(commitTree);
        } catch (IOException e) {
            String msg = "IOException while saving commitTree.";
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        String token = "";
        if (args.length > 1) {
            token = args[1];
        }
        String token2 = "";
        if (args.length > 2) {
            token2 = args[2];
        }

        CommitTree commitTree = tryLoadTree();
        String command = args[0];
        switch (command) {
            case "init":
                commitTree = init();
                break;
            case "add":
                commitTree.add(token);
                break;
            case "commit":
                commitTree.commit(token);
                break;
            // case "remove":
            // commitTree.remove(token);
            // break;
            // case "log":
            // commitTree.printlog();
            // break;
            // // case "global-log":
            // commitTree.printlogGlobal();
            // break;
            // case "find":
            // commitTree.find(token);
            // break;
            case "status":
                System.out.println(commitTree);
                // break;
                // case "checkout":
                // if (args.length == 2) {
                // commitTree.checkout(token);
                // } else {
                // int commitID = Integer.parseInt(token);
                // commitTree.checkout(commitID, token2);
                // }
                // break;
                // case "branch":
                // commitTree.addNewBranch(token);
                // break;
                // case "rm-branch":
                // commitTree.removeBranch(token);
                // break;
                // case "reset":
                // int id = Integer.parseInt(token);
                // commitTree.reset(id);
                // break;
                // case "merge":
                // commitTree.merge(token);
            default:
                System.out.println("No command is found");
                break;

        }
        System.out.println(commitTree);
        saveTree(commitTree);
    }
}
