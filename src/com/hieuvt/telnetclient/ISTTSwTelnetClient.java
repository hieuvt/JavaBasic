package com.hieuvt.telnetclient;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by hieuvt on 07/10/2015.
 */
public class ISTTSwTelnetClient {

    private TelnetClient telnetClient = new TelnetClient();
    private InputStream input;
    private PrintStream output;

    public ISTTSwTelnetClient() {

    }

    private boolean connect(String swAddr, int port, String passwd) {

        try {
            if (telnetClient != null && telnetClient.isConnected()) {
                telnetClient.disconnect();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }

            telnetClient.setConnectTimeout(5000);
            telnetClient.connect(swAddr, port);

            input = telnetClient.getInputStream();
            output = new PrintStream(telnetClient.getOutputStream());

            write(passwd);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            int numRead = 0;

            if (input.available() <= 5) {//reader always returns more 5 chars
                return null;
            }
            char ch = (char) input.read();

            while (true) {
                // System.out.print(ch);
                numRead++;
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }

                if (input.available() == 0) {
                    break;
                }
                ch = (char) input.read();

                if (numRead > 2000) {
                    break; // can not read the pattern
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String value) {
        try {
            output.println(value);
            output.flush();
            // System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            telnetClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String command) {
        write(command);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify the changing rate in miliseconds !");
            System.exit(0);
        }

        int routeChangeRate = new Integer(args[0]);
        ISTTSwTelnetClient isttSwTelnetClient = new ISTTSwTelnetClient();
        String command11 = "ip route-static 172.16.16.0 255.255.255.0 192.168.100.1 preference 11";
        String command9 = "ip route-static 172.16.16.0 255.255.255.0 192.168.100.1 preference 9";
        if (isttSwTelnetClient.connect("172.16.18.2", 23, "abcd45678")) {
            System.out.println("Connect Success");
            isttSwTelnetClient.sendCommand("system-view");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {
                isttSwTelnetClient.sendCommand(command11);
                System.out.println("route changed to preference 11");
                try {
                    Thread.sleep(routeChangeRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isttSwTelnetClient.sendCommand(command9);
                System.out.println("route changed to preference 9");
                try {
                    Thread.sleep(routeChangeRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        isttSwTelnetClient.disconnect();
    }
}

