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

    public void connect(String serverAddress) {
        if (this.socket == null) {
            this.print("Trying to connect to " + serverAddress + ".");
            try {
                this.socket = new Socket(serverAddress, 21);
                this.socket.setSoTimeout(2000);
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);

                this.readReply(true);// is there anything need to handle here?
                this.out.println("USER " + this.username);

                String reply = this.readReply(true);
                if (reply.charAt(0) == '3') {
                    this.out.println("PASS " + this.password);
                    reply = this.readReply(true);
                }

                if (reply.charAt(0) == '1') {
                    this.print("An error has occur.");
                    this.disconnect();
                } else if (reply.charAt(0) == '3') {
                    this.disconnect();
                    throw new Exception();
                } else if (reply.charAt(0) == '4' || reply.charAt(0) == '5') {
                    this.disconnect();
                    throw new Exception();
                }            
            } catch (Exception e) {
                this.print("Connect fails");
                this.disconnect();
                e.printStackTrace();
            };   
        } else {this.print("Already connected to a server, disconnect first.");}
    }

    public void disconnect() {
        if (this.socket != null) {
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
            } catch (Exception e) {
                this.print("Failed to disconnect.");
                e.printStackTrace();
            };
        }
    }

    public void setUser(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.print("Current user: " + user);
    }

    public void print(String input) {
        this.mainPanel.updateOutput("[System] " + input);
    }

    public void refresh() {
        if (this.socket == null) {return;}
        String reply = this.pasv();
        if (reply == null) {return;}
        else {
            reply = reply.replaceAll("[^0-9,]", ""); 
            String[] parts = reply.split(",");

            String address = String.join(".", parts[0], parts[1], parts[2], parts[3]);
            int x = Integer.parseInt(parts[4]);
            int y = Integer.parseInt(parts[5]);
            int port = 256 * x + y;
            
            Thread thread = new Thread(new ListThread(address, port, mainPanel));
            this.list(); 
            thread.start();
            this.readReply(true);
            return;
        }
    }

    private void pwd() {
        if (this.socket == null) {return;}
        else {
            try {
                this.out.println("PWD");
                this.print(this.in.readLine());
                
            } catch (Exception e) {}
        }
    }

    private String pasv() {
        if (this.socket == null) {return null;}
        else {
            try {
                this.out.println("PASV");
                String response = this.in.readLine();
                this.print(response);
                if (response.substring(0, 3).equals("227")) {return response.substring(3);}
                else {return null;}
            } catch (Exception e) {return null;}
        }
    }

    private void list() {
        try {
            this.out.println("LIST");
            String response = this.in.readLine();
            this.print(response);
        } catch (Exception e) {}
    }

    private String readReply (boolean print) {
        String reply = null;
        try {
            reply = this.in.readLine();
        } catch (Exception e) {}
        if (print) {this.print(reply);}
        if (reply.charAt(3) == '-') {return readReply(print);}
        else {return reply;}
    }
    
    public void test() {
        // if (this.socket == null) {return;}
        // else {
        //     this.out.println("PASV");
        //     try {
        //         while (true) {
        //             this.print(this.in.readLine());
        //         }
        //     } catch (Exception e) {}
        // }
    }
}