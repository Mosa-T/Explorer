import java.io.ByteArrayOutputStream;
import java.io.File;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;

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
    public static void main(String[] args)  {

        String username="pi";
        String password="123";
        //String host="192.162.1.110"; //or 255
        //String host="192.168.1.0";
        String host="192.168.1.110";
        int port=22;
        String command="ls ";
        
        Session session = null;
        ChannelExec channel = null;
        System.out.println("TRYING");
        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            
            System.out.println("CONNECTED_1");
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            System.out.println("CONNECTED_2");
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            System.out.println("Slept");
            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        
        
        
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




