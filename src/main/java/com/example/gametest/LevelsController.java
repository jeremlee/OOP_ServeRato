package com.example.gametest;

import com.example.utils.LevelDescriptionMap;
import com.example.utils.LevelProfitMap;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LevelsController extends Controller {
    public static Integer LEVEL;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;
    public ImageView img5;
    public Pane pnDesc;
    public ImageView imgClosePane;
    public ImageView imgLvl;
    public Label lblLvl;
    public Label lblTargetProfit;
    public Label lblDesc;
    public ImageView imgPlay;
    private HashMap<Integer,Integer> lvlTargetProfit;
    private HashMap<Integer,String> lvlDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LEVEL = 0;
        lvlTargetProfit = LevelProfitMap.getMap();
        lvlDescription = LevelDescriptionMap.getMap();
        lblDesc.setWrapText(true);
        pnDesc.setVisible(false);
    }
    public void onLvl1Click(){
        LEVEL = 1;
        lblLvl.setText(LEVEL.toString());
        lblDesc.setText(lvlDescription.get(LEVEL));
        lblTargetProfit.setText(lvlTargetProfit.get(LEVEL).toString());
        pnDesc.setVisible(true);
    }
    public void on1HoverIn(){hoverIn(img1);}
    public void on1HoverOut(){hoverOut(img1);}
    public void onLvl2Click(){
        LEVEL = 2;
        lblLvl.setText(LEVEL.toString());
        lblDesc.setText(lvlDescription.get(LEVEL));
        lblTargetProfit.setText(lvlTargetProfit.get(LEVEL).toString());
        pnDesc.setVisible(true);
    }
    public void on2HoverIn(){hoverIn(img2);}
    public void on2HoverOut(){hoverOut(img2);}
    public void onLvl3Click(){
        LEVEL = 3;
        lblLvl.setText(LEVEL.toString());
        lblDesc.setText(lvlDescription.get(LEVEL));
        lblTargetProfit.setText(lvlTargetProfit.get(LEVEL).toString());
        pnDesc.setVisible(true);
    }
    public void on3HoverIn(){hoverIn(img3);}
    public void on3HoverOut(){hoverOut(img3);}
    public void onLvl4Click(){
        LEVEL = 4;
        lblLvl.setText(LEVEL.toString());
        lblDesc.setText(lvlDescription.get(LEVEL));
        lblTargetProfit.setText(lvlTargetProfit.get(LEVEL).toString());
        pnDesc.setVisible(true);
    }
    public void on4HoverIn(){hoverIn(img4);}
    public void on4HoverOut(){hoverOut(img4);}
    public void onLvl5Click(){
        LEVEL = 5;
        lblLvl.setText(LEVEL.toString());
        lblDesc.setText(lvlDescription.get(LEVEL));
        lblTargetProfit.setText(lvlTargetProfit.get(LEVEL).toString());
        pnDesc.setVisible(true);
    }
    public void on5HoverIn(){hoverIn(img5);}
    public void on5HoverOut(){hoverOut(img5);}
    public void onDescClose(){pnDesc.setVisible(false);}
    public void onDescHoverIn(){hoverIn(imgClosePane);}
    public void onDescHoverOut(){hoverOut(imgClosePane);}
    public void onPlayClick(){toLoadingScreen("game.fxml");}
    public void onPlayHoverIn(){hoverIn(imgPlay);}
    public void onPlayHoverOut(){ hoverOut(imgPlay); }
}
