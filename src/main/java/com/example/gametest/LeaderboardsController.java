package com.example.gametest;

import com.example.gametest.utils.GameTableData;
import com.example.gametest.utils.MySQLConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class LeaderboardsController extends Controller{
    public TableView<GameTableData> tblLeaderboard;
    public ChoiceBox<String> cbLvl;
    private final String[] lvls = {"All levels", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};
    public TableColumn<GameTableData,String> colName;
    public TableColumn<GameTableData,Integer> colScore;
    public TableColumn<GameTableData,Integer> colLvl;
    public TableColumn<GameTableData, Date> colDate;
    public ImageView imgBack;
    private ObservableList<GameTableData> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playMusic("/com/example/gametest/Music/A_Town_further_than_the_Universe-yuhei_komatsu.mp3");
        list = FXCollections.observableArrayList();
        cbLvl.getItems().addAll(lvls);
        cbLvl.setOnAction(event -> loadData(cbLvl.getSelectionModel().getSelectedIndex()));
        loadColumns();
        loadData(0);
    }
    private void loadColumns(){
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colScore.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
        colLvl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLvl()).asObject());
        colDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTime()));
    }
    private void loadData(int level)  {
        list.clear();
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement();
            PreparedStatement statement1 = c.prepareStatement("SELECT u.username AS name, g.score AS score, g.level AS lvl, g.time_played AS time " +
                    "FROM tblgame AS g " +
                    "INNER JOIN tbluser AS u ON g.uid = u.uid " +
                    "WHERE g.level = ? ORDER BY score DESC " +
                    "LIMIT 10")){
            //start of try
            ResultSet rs;
            if (level == 0) {
                statement.executeQuery("SELECT u.username AS name, g.score AS score, g.level AS lvl, g.time_played AS time " +
                        "FROM tblgame AS g " +
                        "INNER JOIN tbluser AS u ON g.uid = u.uid " +
                        "ORDER BY score DESC " +
                        "LIMIT 10");
                rs = statement.getResultSet();
            } else {
                statement1.setInt(1, level);
                statement1.executeQuery();
                rs = statement1.getResultSet();
            }
            while(rs.next()){
                list.add(new GameTableData(rs.getString("name"),rs.getInt("score"),
                        rs.getInt("lvl"),rs.getDate("time")));
            }
            tblLeaderboard.setItems(list);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void back(){toLoadingScreen("main_menu.fxml");}
    public void onBackHoverIn(){hoverIn(imgBack);}
    public void onBackHoverOut(){hoverOut(imgBack);}
}
