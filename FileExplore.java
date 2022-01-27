import java.io.File;
import com.jcraft.jsch;
public class FileExplore {

    //Look through the files/folders, if it's a file, print it, if it's a directory, print it and recurse through it
    //And do the same for its files/folders.
    static void explore(File[] list)
    {
        for (File i : list) {
            if (i.isFile())
                System.out.println("FILE:  - "+ i + "\\" + i.getName());

            else if (i.isDirectory()) {
                System.out.println("FOLDER:- "+ i );
                explore(i.listFiles());
            }
        }
    }
    //Pick a directory to explore, if it exists and is a valid directory, we will explore it.
    public static void main(String[] args)
    {
        if(args.length != 2){
            System.out.println("Incorrect argument input:- exiting");
            System.exit(0);
        }
        File f_dir = new File(args[1]);
        System.out.println("Remote hostname:- "+args[0]);
        System.out.println("Directory request:- " + args[1]);
        if (f_dir.exists() && f_dir.isDirectory()) {
            File list[] = f_dir.listFiles();
            explore(list);
        } else{
            System.out.println("Error in:- Directory request");
        }
    }
}
