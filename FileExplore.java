import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.jcraft.jsch.*;

public class FileExplore {


    public static void main(String[] args)  {

        if(args.length != 5){
            System.out.println("Incorrect argument input:- exiting");
            System.exit(0);
        }

        String username=args[0];
        String password=args[1];
        String directory=args[2];
        String host=args[3];
        int port=Integer.valueOf(args[4]);

        
        Session session = null;
        ChannelSftp channel = null;
        System.out.println("Agent:- Attempting to connect");

        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("Agent:- Connection to remote host <" + username +"> establish");

            System.out.println("Agent:- Establishing SSH File Transfer Protocol channel");
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            System.out.println("Agent:- Established sftp channel");

            Stack<String> stack = new Stack<String>();
            stack.push(directory);
            String full_dir;
            System.out.println("Agent:- File data transferring through channel");

            System.out.println("Root directory:- " + directory);
            while(!stack.isEmpty()){
                //System.out.println("POPPING  "+ stack.peek());
                full_dir = stack.peek();
                directory = stack.pop();
                channel.cd(directory);
                List<ChannelSftp.LsEntry> content = channel.ls(directory);
                for(var i : content){
                    if(i.getAttrs().isDir() && !i.getFilename().equals(".") && !i.getFilename().equals("..")){
                        //System.out.println("PUSHING  " + directory + i.getFilename());
                        stack.push(directory+i.getFilename()+"/");
                        System.out.println(stack.peek());
                    } else if(!i.getFilename().equals(".") && !i.getFilename().equals("..")) { //if(i.getAttrs().isReg())
                        System.out.println(full_dir + i.getFilename());
                    }
                }
            }
            System.out.println("Agent:- File data transferred through channel");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                System.out.println("Agent:- closing session");
                session.disconnect();
            }
            if (channel != null) {
                System.out.println("Agent:- closing channel");
                channel.disconnect();
            }
        }
    }
}

