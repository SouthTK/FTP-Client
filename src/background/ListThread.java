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
            String reply = this.in.readLine();
            while (reply != null) {
                System.out.println(reply);
                this.mainPanel.updateDirectory(reply);
                reply = this.in.readLine();
            }
        } catch (Exception e) {System.out.println("reading fails.");}
    }
}