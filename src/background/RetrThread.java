package background;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RetrThread implements Runnable {
    private Socket socket;
    private BufferedReader in;

    RetrThread(String address, int port) throws Exception {
        this.socket = new Socket(address, port);
        //this.socket.setSoTimeout(2000);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void run() {
        // try {
        //     String reply = this.in.readLine();
        //     while (reply != null) {
        //         System.out.println(reply);
        //         reply = this.in.readLine();
        //     }
        // } catch (Exception e) {System.out.println("reading fails.");}
    }
}