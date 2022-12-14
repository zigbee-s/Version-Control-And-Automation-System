package vcstool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OS {
    static public void runCommand(String command) throws IOException {
        Process p = Runtime.getRuntime().exec(command);

        String s;
        System.out.println(p.getOutputStream());
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
    }

}