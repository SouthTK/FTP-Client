package background;

import java.net.Socket;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;

public class RetrThread extends DataThread {
    private String path;
    private InputStream in;

    RetrThread(String pasvResponse, String path) throws Exception {
        super(pasvResponse);
        this.path = path;
        this.in = this.socket.getInputStream();
    }

    public void run() {
        try {
            int count;
            byte data[] = new byte[1024]; 
            File downloadFile = new File(path);
            FileOutputStream fileOut = new FileOutputStream(downloadFile);
            
            while ((count = in.read(data)) != -1) {
              fileOut.write(data, 0, count);   
            }
            
            fileOut.flush();
            fileOut.close();
            this.in.close();
            this.socket.close();
        } catch (Exception e) {System.out.println("Download fails.");}
    }

    public void close() {
        try {
            this.in.close();
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close or already been closed");
        }
    }
}