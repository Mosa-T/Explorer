import java.io.File;

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
        //String dirpath = "D:/study/JavaWorkspace/JobTests";
        String dirpath = "D:/study/logic";
        File f_dir = new File(dirpath);

        if (f_dir.exists() && f_dir.isDirectory()) {
            File list[] = f_dir.listFiles();
            System.out.println("Main directory : " + f_dir);
            explore(list);
        }
    }
}
