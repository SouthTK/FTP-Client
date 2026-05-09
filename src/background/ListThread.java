package background;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ui.MainPanel;

public class ListThread implements Runnable {
    private MainPanel mainPanel;
    private Socket socket;
    private BufferedReader in;

    ListThread(String address, int port, MainPanel input) throws Exception {
        this.mainPanel = input;
        this.socket = new Socket(address, port);
        //this.socket.setSoTimeout(2000);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void run() {
        try {
            this.mainPanel.clearDirectory(false);            
            String reply = this.readReply();
            while (reply != null) {
                this.mainPanel.updateDirectory(reply);
                reply = this.readReply();
            }
        } catch (Exception e) {System.out.println("Reading list fails.");}// never reach?
    }

    private String readReply() {
        try {
            String reply = this.in.readLine();
            return reply;
        } catch (Exception e) {
            System.out.println("Reading fails.");
            return null;
        }
    }
}