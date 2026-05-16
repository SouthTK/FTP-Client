package background;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ui.MainPanel;

public class ListThread extends DataThread {
    private BufferedReader in;
    private MainPanel mainPanel;

    ListThread(String pasvResponse, MainPanel input) throws Exception {
        super(pasvResponse);
        this.mainPanel = input;
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
            socket.close();
        } catch (Exception e) {System.out.println("Reading list fails.");}
    }

    public void close() {
        try {
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close or already been closed");
        }
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