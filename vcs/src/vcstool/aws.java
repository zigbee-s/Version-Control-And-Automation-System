package vcstool;

import java.io.IOException;

public class aws {
    static public String pushToBucket(String bucketName) throws IOException {
        String command = String.format("aws s3 cp .vcs s3://%s --recursive", bucketName);
        OS.runCommand(command);
        return "Pushed to aws";
    }
}