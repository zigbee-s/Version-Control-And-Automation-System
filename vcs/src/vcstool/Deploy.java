package vcstool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Deploy {
    static public String creatFile() {
        try {
            File myObj = new File("version.txt");
            String str = "";
            if (myObj.createNewFile()) {
                str = "1";
            } else {
                BufferedReader br = new BufferedReader(new FileReader("version.txt"));
                String st;
                while ((st = br.readLine()) != null) {
                    int version = Integer.parseInt(st);
                    str = Integer.toString(version + 1);
                }
            }
            String command = String.format("docker build --tag image:v%s .", str);
            System.out.println(command);
            OS.runCommand(command);

            BufferedWriter writer = new BufferedWriter(new FileWriter("version.txt"));
            writer.write(str);
            writer.close();
            return "image Created with version: v" + str;
        } catch (IOException e) {
            return "Error";
        }
    }
}
