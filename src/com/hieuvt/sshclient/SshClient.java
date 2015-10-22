package com.hieuvt.sshclient;

import com.jcraft.jsch.*;

import java.io.InputStream;

public class SshClient {

    private String host;
    private String user;
    private String passwd;
    private int port;
    private final static String KNOWNHOSTS_FILE = "known_hosts.txt";

    public SshClient(String host, String user, String passwd) {
        setHost(host);
        setUser(user);
        setPasswd(passwd);
        setPort(22);
    }

    public SshClient(String host, String user, String passwd, int port) {
        setHost(host);
        setUser(user);
        setPasswd(passwd);
        setPort(port);
    }

    public boolean executeCommand (String command) {
        try{
            JSch jsch=new JSch();
            jsch.setKnownHosts(KNOWNHOSTS_FILE);

            Session session=jsch.getSession(user, host, 22);
            session.setPassword(getPasswd());

            session.connect();

//            while (true) {
                Channel channel=session.openChannel("exec");
                ((ChannelExec)channel).setCommand(command);

                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);
                InputStream in=channel.getInputStream();

                channel.connect();

                byte[] tmp=new byte[1024];
                while(true){
                    while(in.available()>0){
                        int i=in.read(tmp, 0, 1024);
                        if(i<0)break;
                        System.out.print(new String(tmp, 0, i));
                    }
                    if(channel.isClosed()){
                        if(in.available()>0) continue;
                        System.out.println("exit-status: "+channel.getExitStatus());
                        break;
                    }
                    try{Thread.sleep(1000);}catch(Exception ee){}
                }


                channel.disconnect();
//            }

            session.disconnect();
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public static void main(String[] arg){

        String host = "172.16.16.19";
        String user = "sysadmin";
        String password = "abcd@1234";
        String command = "set|grep SSH";

        SshClient sshClient = new SshClient(host,user,password);
        sshClient.executeCommand(command);

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
