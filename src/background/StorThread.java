package background;

import java.net.Socket;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.File;

public class StorThread implements Runnable {
    private String path;
    private Socket socket;
    private OutputStream out;

    StorThread(String address, int port, String path) throws Exception {
        this.path = path;
        this.socket = new Socket(address, port);
        this.out = this.socket.getOutputStream();
    }

    public void run() {
        try {
            int count;
            System.out.println("went well 2");
            byte data[] = new byte[1024]; 
            File uploadFile = new File(path);
            FileInputStream fileIn = new FileInputStream(uploadFile); 
            
            while ((count = fileIn.read(data)) != -1) {
              out.write(data, 0, count);   
              System.out.println("writing " + count + " bytes");
            }

            out.flush();
            fileIn.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Download fails.");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close or already been closed");
        }
    }
}