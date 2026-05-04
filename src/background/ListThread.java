package background;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ui.MainPanel;

public class ListThread implements Runnable {
    private MainPanel mainPanel;
    private Socket socket;
    private BufferedReader in;

    ListThread(String address, int port, MainPanel input) {
        this.mainPanel = input;
        try {
            this.socket = new Socket(address, port);
            this.socket.setSoTimeout(2000);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {System.out.println("Create socket from PASV failed.");}
    }

    public void run() {
        try {
            this.mainPanel.clearDirectory();
            this.mainPanel.clearDirectoryCombo();
            String reply = this.readReply();
            while (reply != null) {
                this.mainPanel.updateDirectory(reply);
                reply = this.readReply();
            }
        } catch (Exception e) {System.out.println("Reading list fails.");}
    }

    private String readReply() {
        try {
            String reply = this.in.readLine();
            if (reply != null) {
                if (reply.charAt(0) == 'd') {
                    String[] parts = reply.trim().split("\\s+");
                    String directoryName = parts[parts.length - 1];
                    this.mainPanel.updateDirectoryCombo(directoryName);
                }
            }
            return reply;
        } catch (Exception e) {
            System.out.println("Reading fails.");
            return null;
        }
    }
}