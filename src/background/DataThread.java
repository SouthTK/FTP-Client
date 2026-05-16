package background;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ui.MainPanel;

public abstract class DataThread implements Runnable {
    protected Socket socket;

    DataThread(String pasvResponse) throws Exception { //we have no ways to know it works or not noob
        String[] parts = pasvResponse.replaceAll("[^0-9,]", "").split(",");

        String address = String.join(".", parts[0], parts[1], parts[2], parts[3]);
        int port = 256 * Integer.parseInt(parts[4]) + Integer.parseInt(parts[5]);

        this.socket = new Socket(address, port);
    }

    public void close() {
        try {
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close or already been closed");
        }
    }
}