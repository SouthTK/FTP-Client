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
                Thread.sleep(1000);
                System.out.println("Current user:" + this.username);
            } catch (Exception e) {System.out.println("Process thread fails.");}
        }
    }

    public void connect(String serverAddress) {
        if (this.socket == null) {
            this.print("[System] Trying to connect to " + serverAddress);
            try {
                this.socket = new Socket(serverAddress, 21);
                this.socket.setSoTimeout(2000);
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);

                this.print(this.in.readLine());
                this.out.println("USER " + this.username);

                try {
                    while (true) {
                        this.print(this.in.readLine());
                    }
                } catch (Exception e) {}
                
            } catch (Exception e) {
                this.print("[System] Connect fails");
                e.printStackTrace();
            };   
        } else {
            this.print("[System] Already connected to a server, disconnect first.");
        }
    }

    public void disconnect() {
        if (this.socket != null) {
            this.print("[System] Disconnecting.");
            try {
                this.socket.close();
                this.socket = null;
                this.in = null;
                this.out = null;
            } catch (Exception e) {
                this.print("[System] Disconnect fails");
                e.printStackTrace();
            };
        }
    }

    public void pwd() {
        if (this.socket == null) {return;}
        else {
            this.out.println("PWD");
            try {
                while (true) {
                    this.print(this.in.readLine());
                }
            } catch (Exception e) {}
        }
    }

    public void setUser(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.mainPanel.updateOutput("[System] Logged in as " + user);
    }

    public void print(String input) {
        this.mainPanel.updateOutput(input);
    }
}