package vcstool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Main {

    private static final String VCS_DIR = ".vcs/";

    private static CommitTree init() {
        File f = new File(VCS_DIR);
        if (f.exists()) {
            System.out.println("A vcs version control system already exists in the current directory");
            return null;
        }
        f.mkdirs();
        return CommitTree.commitTreeInit();
    }

    private static CommitTree tryLoadTree() {
        CommitTree commitTree = null;
        File commitTreeFile = new File(VCS_DIR + "VCS.ser");
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
            File commitTreeFile = new File(VCS_DIR + "VCS.ser");
            FileOutputStream fileOut = new FileOutputStream(commitTreeFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(commitTree);
        } catch (IOException e) {
            String msg = "IOException while saving commitTree.";
            System.out.println(msg);
        }
    }

    public static void main(String[] args) throws IOException {
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
            case "status":
                System.out.println(commitTree);
                break;
            case "deploy":
                System.out.println(Deploy.creatFile());
                break;
            case "push":
                System.out.println(aws.pushToBucket(token));
                break;
            default:
                System.out.println("No command is found");
                break;

        }
        System.out.println(commitTree);
        saveTree(commitTree);
    }
}
