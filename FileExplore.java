import java.util.Vector;
import com.jcraft.jsch.*;

public class FileExplore {

    public static void main(String[] args)  {

        if(args.length != 5){
            System.out.println("Incorrect argument input:- exiting");
            System.exit(0);
        }

        String username="pi";
        String password="123";
        String host="192.168.1.110";
        int port=22;
        String directory="/home/pi/";
        
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

            channel.cd(directory);
            Vector filelist = channel.ls(directory);
            System.out.println("Agent:- File data transferred through channel");

            for(int i=0; i<filelist.size();i++){
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
                System.out.println(entry.getFilename());
                //System.out.println(filelist.get(i).toString());
            }
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
