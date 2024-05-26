package com.example.gametest;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Controller{
    public ImageView imgPlay;
    public ImageView imgLeaderboards;
    public ImageView imgExit;
    public ImageView imgLogout;
    public Pane pnExit;
    public ImageView imgYes;
    public ImageView imgNo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pnExit.setVisible(false);
        playMusic("/com/example/gametest/Music/A_Town_further_than_the_Universe-yuhei_komatsu.mp3");
    }
    public void onPlayClick(){
        toLoadingScreen("levels.fxml");
    }
    public void onLeaderboardsClick(){ toLoadingScreen("leaderboards.fxml");}
    public void onExitClick(){pnExit.setVisible(true);}
    public void onLogoutClick() { toLoadingScreen("login.fxml"); }
    @FXML
    void onPlayerHoverIn(){hoverIn(imgPlay);}
    @FXML
    void onPlayerHoverOut(){hoverOut(imgPlay);}
    @FXML
    void onLeaderboardsHoverIn(){hoverIn(imgLeaderboards);}
    @FXML
    void onLeaderboardsHoverOut(){hoverOut(imgLeaderboards);}
    @FXML
    void onLogoutHoverIn(){hoverIn(imgLogout);}
    @FXML
    void onLogoutHoverOut(){hoverOut(imgLogout);}
    @FXML
    void onExitHoverIn(){hoverIn(imgExit);}
    @FXML
    void onExitHoverOut(){hoverOut(imgExit);}
    @FXML
    void closeGame(){Platform.exit();}
    @FXML
    void closeExitMenu(){pnExit.setVisible(false);}
    @FXML
    void onYesHoverIn(){hoverIn(imgYes);}
    @FXML
    void onYesHoverOut(){hoverOut(imgYes);}
    @FXML
    void onNoHoverIn(){hoverIn(imgNo);}
    @FXML
    void onNoHoverOut(){hoverOut(imgNo);}

}
