package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Server;
import view.ServerView;

/**
 *
 * @author _Adrián_Prendas_
 */
public class Controller implements ActionListener{
    
    private ServerView serverView;
    private Server server;
    
    public Controller(){
        this.serverView = new ServerView();
        this.serverView.getBtnStart().setActionCommand("start");
        this.serverView.getBtnStart().addActionListener(this);
        this.serverView.getBtnStop().setActionCommand("stop");
        this.serverView.getBtnStop().addActionListener(this);
        this.serverView.getBtnSend().setActionCommand("send");
        this.serverView.getBtnSend().addActionListener(this);
        this.serverView.getBtnSelect().setActionCommand("select");
        this.serverView.getBtnSelect().addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "start":start();break;
            case "stop":stop();break;
            case "send":send();break;
            case "select":break;
        }
    }
    
    public void start(){
        this.server = Server.startServer(
                this.serverView.getGameArea(),
                this.serverView.getActionsArea(),
                this.serverView.getPlayersArea(),
                this.serverView.getChatArea()
                );
    }
    
    public void stop(){
        this.server.stopServer();
    }
    
    public void send(){
        String message = this.serverView.getTxtMessage().getText();
        this.server.tellEveryone(message);
    }
    

}
