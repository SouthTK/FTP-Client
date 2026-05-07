package background;

import java.net.Socket;

import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ui.MainPanel;

public class ProcessThread implements Runnable {
    private MainPanel mainPanel;
    private String username;
    private String password;

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public ProcessThread(MainPanel input) {
        this.mainPanel = input;
    }

    public void run() {
        while(1 == 1) {
            try {
                Thread.sleep(5000);
                System.out.println("Current user:" + this.username);
            } catch (Exception e) {System.out.println("Process thread fails.");}
        }
    }

    // backend method
    public void setUser(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.print("Current user: " + user);
    }

    public void print(String input) {
        this.mainPanel.updateOutput("[System] " + input);
    }

    // button method
    public void connect(String serverAddress) {
        if (this.socket == null) {
            this.print("Trying to connect to " + serverAddress + ".");
            try {
                this.socket = new Socket(serverAddress, 21);
                this.socket.setSoTimeout(2000);
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);
                this.readReply(true);

                this.out.println("USER " + this.username);
                String reply = this.readReply(true);
                if (reply.charAt(0) == '3') {
                    this.out.println("PASS " + this.password);
                    reply = this.readReply(true);
                }

                if (reply.charAt(0) == '1') {
                    this.print("An error has occur.");
                    this.disconnect();
                } else if (reply.charAt(0) == '2') {
                    this.refresh();
                } else if (reply.charAt(0) == '3') {
                    this.print("An error has occur.");
                    this.disconnect();
                } else if (reply.charAt(0) == '4' || reply.charAt(0) == '5') {
                    this.disconnect();
                    throw new Exception();
                }            
            } catch (Exception e) {
                this.print("Failed to connect.");
                this.disconnect();
                e.printStackTrace();
            };   
        } else {this.print("Already connected to a server, disconnect first.");}
    }

    public void disconnect() {
        if (this.socket == null) {return;}
        else {
            this.print("Disconnecting.");
            try {
                try {
                    this.out.println("QUIT");
                    this.readReply(true);
                } catch (Exception e) {this.print("Failed to quit.");}
                this.socket.close();
                this.socket = null;
                this.in = null;
                this.out = null;
                this.mainPanel.clearDirectory();
            } catch (Exception e) {
                this.print("Failed to disconnect.");
                e.printStackTrace();
            };
        }
    }

    public void refresh() {
        if (this.socket == null || !this.pwd()) {return;}

        String reply = this.pasv();
        if (reply == null) {return;}
        else {
            reply = reply.replaceAll("[^0-9,]", ""); 
            String[] parts = reply.split(",");

            String address = String.join(".", parts[0], parts[1], parts[2], parts[3]);
            int port = 256 * Integer.parseInt(parts[4]) + Integer.parseInt(parts[5]);
            Thread thread = new Thread(new ListThread(address, port, mainPanel));

            if (this.list()) {
                thread.start();
            } 
        }
    }

    public void changeDirectory(String input) {
        if (this.socket == null || !this.cwd(input)) {return;}
        else {
            this.refresh();
        }
    }

    // command method
    private boolean pwd() {
        if (this.socket == null) {return false;}
        else {
            try {
                this.out.println("PWD");
                String reply = this.readReply(true);

                if (reply.charAt(0) == '1' || reply.charAt(0) == '3') {
                    this.mainPanel.updateCurrentDirectory("An error has occur.");
                    throw new Exception();
                } else if (reply.charAt(0) == '4' || reply.charAt(0) == '5') {
                    this.mainPanel.updateCurrentDirectory("An failure has occur.");
                    throw new Exception();
                } else {
                    String[] parts = reply.split("\"");
                    this.mainPanel.updateCurrentDirectory(parts[1]);
                    return true;
                }
            } catch (Exception e) {
                this.print("PWD fails.");
                return false;
            }
        }
    }

    private boolean cwd(String input) {
        if (this.socket == null) {return false;}
        else {
            try {
                this.out.println("CWD " + input);
                String reply = this.readReply(true);

                if (reply.charAt(0) == '1' || reply.charAt(0) == '3') {
                    this.mainPanel.updateCurrentDirectory("An error has occur.");
                    throw new Exception();
                } else if (reply.charAt(0) == '4' || reply.charAt(0) == '5') {
                    this.mainPanel.updateCurrentDirectory("An failure has occur.");
                    throw new Exception();
                } else {
                    return true;
                }
            } catch (Exception e) {
                this.print("CWD fails.");
                return false;
            }
        }
    }

    private String pasv() {
        if (this.socket == null) {return null;}
        else {
            try {
                this.out.println("PASV");
                String reply = this.readReply(true);

                if (reply.charAt(0) == '1' || reply.charAt(0) == '3') {
                    this.mainPanel.updateCurrentDirectory("An error has occur.");
                    throw new Exception();
                } else if (reply.charAt(0) == '4' || reply.charAt(0) == '5') {
                    this.mainPanel.updateCurrentDirectory("An failure has occur.");
                    throw new Exception();
                } else {
                    return reply.substring(3);
                }
            } catch (Exception e) {
                this.print("CWD fails.");
                return null;
                }
        }
    }

    private boolean list() {
        if (this.socket == null) {return false;}
        else {
            try {
                this.out.println("LIST");
                String reply = this.readReply(true);

                while (reply.charAt(0) == '1') {
                    reply = this.readReply(true);
                }

                if (reply.charAt(0) == '1' || reply.charAt(0) == '3') {
                    this.mainPanel.updateCurrentDirectory("An error has occur.");
                    throw new Exception();
                } else if (reply.charAt(0) == '4' || reply.charAt(0) == '5') {
                    this.mainPanel.updateCurrentDirectory("An failure has occur.");
                    throw new Exception();
                } else {
                    return true;
                }
            } catch (Exception e) {
                this.print("LIST fails.");
                return false;
            }
        }
    }

    // private method
    private String readReply (boolean print) {
        String reply = null;
        try {
            reply = this.in.readLine();
        } catch (Exception e) {
            this.print("Error while reading reply.");
            return null;
        }
        if (print) {this.print(reply);}
        if (reply.charAt(3) == '-') {return readReply(print);}
        else {return reply;}
    }
    
    public void test() {
        if (this.socket == null) {return;}
        else {
            this.out.println("CWD gnu");
            try {
                while (true) {
                    this.print(this.in.readLine());
                }
            } catch (Exception e) {}
        }
    }
}