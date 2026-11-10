import java.io.File;
import java.nio.file.Paths;

public class Test{
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    File keyFile = Paths.get(Consts.security_keys_path, Consts.security_publicKey_name).toFile();
   /* String parent = keyFile.toString().substring(0, keyFile.toString().lastIndexOf("/"));
    System.out.println(parent);*/
    System.out.println(getFileParentDir(keyFile));
    }

    public static String getFileParentDir(File file){
        int fileNameLength = file.getName().length();
        int parentLength = file.toString().length() - fileNameLength;
        return file.toString().substring(0, parentLength);
    }
}
