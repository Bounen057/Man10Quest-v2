package red.man10.man10quest.api;

import red.man10.man10quest.Man10Quest;
import red.man10.man10quest.data.Man10EventData;
import red.man10.man10quest.data.Man10QuestData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class QuestAPI {

    static Man10Quest plugin; //メインクラス変数
    static HashMap<Integer,Man10EventData> eventDatas; //イベントデータ(メモリ)の記録HashMap
    static HashMap<UUID,HashMap<Integer,Man10QuestData>> playerDatas; //プレイヤーデーター(メモリ)の操作HashMap

    public static void loadEnable(Man10Quest plugin){
        QuestAPI.plugin = plugin;
        eventDatas = new HashMap<>();
        playerDatas = new HashMap<>();
        startUpLoad(); //start Up Load
    }

    public static void startUpLoad(){
        String tableCreateSql = "create table if not exists man10quest_event(" +
                "id int auto_increment not null primary key," +
                "icon text,"+
                "shown_type varchar(32),"+
                "event_name varchar(128),"+
                "event_type varchar(64),"+
                "event_description text,"+
                "reward_balance bigint,"+
                "reward_item text,"+
                "reward_commands text,"+
                "time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;";
        plugin.mysql.execute(tableCreateSql); // created man10quest_event table
        //ここから先にロードするコードを書く
        String sql = "SELECT * FROM man10quest_event;";
        ResultSet rs = plugin.mysql.query(sql); // get man10quest_event data
        if (rs != null) {
            eventDatas = EventDatafromResultSet(rs); // set man10quest_event data
        }
    }

    public static HashMap<Integer,Man10EventData> EventDatafromResultSet(ResultSet rs){
        HashMap<Integer,Man10EventData> eventDatas = new HashMap<>();
        try {
            while (rs.next()){
                int id = rs.getInt("id");
                String icon = rs.getString("icon");
                String shown_type = rs.getString("shown_type");
                String event_name = rs.getString("event_name");
                String event_type = rs.getString("event_type");
                String event_description = rs.getString("event_description");
                long reward_balance = rs.getLong("reward_balance");
                String reward_item = rs.getString("reward_items");
                String reward_commands = rs.getString("reward_commands");
                Date time = rs.getDate("time");
                Man10EventData eventData = new Man10EventData(id,icon,shown_type,event_name,event_type,event_description,reward_balance,reward_item,reward_commands,time);
                eventDatas.put(id,eventData);
            }
            rs.close();
        } catch (NullPointerException | SQLException e1) {
            e1.printStackTrace();
            return eventDatas;
        }
        return eventDatas;
    }

    public static void addEvent(Man10EventData eventData){
        String sql = "INSERT INTO man10quest_event (icon,shown_type,event_name,event_type,event_description,reward_balance,reward_items,reward_commands) VALUES (" +
                "'"+eventData.getIcon()+"'," +
                "'"+eventData.getShown_type()+"'," +
                "'"+eventData.getEvent_name()+"'," +
                "'"+eventData.getEvent_type()+"'," +
                "'"+eventData.getEvent_description()+"'," +
                eventData.getReward_balance()+"," +
                "'"+eventData.getReward_item()+"'," +
                "'"+eventData.getReward_commands()+"');";
        plugin.mysql.execute(sql);
        int id = eventDatas.size() + 1;
        eventDatas.put(id,eventData);
    }


}
