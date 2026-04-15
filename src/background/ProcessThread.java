package background;

import ui.MainPanel;

public class ProcessThread implements Runnable {
    private MainPanel mainPanel;
    private String username;
    private String password;

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

    public void setUser(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.mainPanel.updateOutput("Logged in as " + user);
    }
}