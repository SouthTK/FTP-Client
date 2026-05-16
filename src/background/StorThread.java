package background;

import java.net.Socket;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.File;

import ui.MainPanel;

public class StorThread extends DataThread {
    private String path;
    private Socket socket;
    private OutputStream out;

    StorThread(String pasvResponse, String path) throws Exception {
        super(pasvResponse);
        this.path = path;
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
            this.out.close();
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Download fails.");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.out.close();
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close or already been closed");
        }
    }
}