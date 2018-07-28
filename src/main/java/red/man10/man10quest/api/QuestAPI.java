package red.man10.man10quest.api;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import red.man10.man10quest.Man10Quest;
import red.man10.man10quest.data.Man10EventData;
import red.man10.man10quest.data.Man10QuestData;
import red.man10.man10vaultapiplus.enums.TransactionCategory;
import red.man10.man10vaultapiplus.enums.TransactionType;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class QuestAPI {

    static class shown_Type {
        static String hide = "hide";
        static String no_open_hide = "no_open_hide";
        static String open_hide = "open_hide";
        static String free = "free";
    }

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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String tableCreateSql = "create table if not exists man10quest_event(" +
                    "id int auto_increment not null primary key," +
                    "icon text," +
                    "shown_type varchar(32)," +
                    "event_name varchar(128)," +
                    "event_type varchar(64)," +
                    "event_description text," +
                    "end_message text," +
                    "broadcast_type varchar(32)," +
                    "reward_balance bigint," +
                    "reward_item text," +
                    "reward_commands text," +
                    "time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;";
            plugin.mysql.execute(tableCreateSql); // created man10quest_event table
            String tableCreateSql2 = "create table if not exists man10quest_player_data(" +
                    "id int auto_increment not null primary key," +
                    "event_id int," +
                    "event_name varchar(128)," +
                    "event_type varchar(64)," +
                    "name varchar(32)," +
                    "uuid varchar(64)," +
                    "data text," +
                    "finished boolean," +
                    "start_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                    "finished_time DATETIME" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;";
            plugin.mysql.execute(tableCreateSql2); // created man10quest_player_data table
            //ここから先にロードするコードを書く
            String sql = "SELECT * FROM man10quest_event;";
            ResultSet rs = plugin.mysql.query(sql); // get man10quest_event data
            if (rs != null) {
                eventDatas = EventDatafromResultSet(rs); // set man10quest_event data
            }
            playerDatas = loadPlayerData();
        });
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
                String end_message = rs.getString("end_message");
                String broadcast_type = rs.getString("broadcast_type");
                long reward_balance = rs.getLong("reward_balance");
                String reward_item = rs.getString("reward_items");
                String reward_commands = rs.getString("reward_commands");
                Date time = rs.getDate("time");
                Man10EventData eventData = new Man10EventData(id,icon,shown_type,event_name,event_type,event_description,end_message,broadcast_type,reward_balance,reward_item,reward_commands,time);
                eventDatas.put(id,eventData);
            }
            rs.close();
        } catch (NullPointerException | SQLException e1) {
            e1.printStackTrace();
            plugin.mysql.close();
            return eventDatas;
        }
        plugin.mysql.close();
        return eventDatas;
    }

    public static HashMap<UUID,HashMap<Integer,Man10QuestData>> loadPlayerData(){
        String sql = "SELECT * FROM man10quest_player_data;";
        ResultSet rs = plugin.mysql.query(sql);
        HashMap<UUID,HashMap<Integer,Man10QuestData>> playerDatas = new HashMap<>();
        try {
            while (rs.next()){
                String suuid = rs.getString("uuid");
                UUID uuid = UUID.fromString(suuid);
                if(!QuestAPI.playerDatas.containsKey(uuid)){
                    QuestAPI.playerDatas.put(uuid,new HashMap<>());
                }
                HashMap<Integer,Man10QuestData> questDatas = QuestAPI.playerDatas.get(uuid);
                int id = rs.getInt("id");
                int event_id = rs.getInt("event_id");
                String event_name = rs.getString("event_name");
                String event_type = rs.getString("event_type");
                String name = rs.getString("name");
                String data = rs.getString("data");
                boolean finished = rs.getBoolean("finished");
                Date start_time = rs.getDate("start_time");
                Date finished_time = rs.getDate("finished_time");
                Man10QuestData questData = new Man10QuestData(id,event_id,event_name,event_type,name,uuid.toString(),data,finished,start_time,finished_time);
                questDatas.put(id,questData);
                playerDatas.put(uuid,questDatas);
            }
            rs.close();
        } catch (NullPointerException | SQLException e1) {
            e1.printStackTrace();
            plugin.mysql.close();
            return playerDatas;
        }
        plugin.mysql.close();
        return playerDatas;
    }

    public static void addEvent(Man10EventData eventData){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String sql = "INSERT INTO man10quest_event (icon,shown_type,event_name,event_type,event_description,end_message,broadcast_type,reward_balance,reward_items,reward_commands) VALUES (" +
                    "'" + eventData.getIcon() + "'," +
                    "'" + eventData.getShown_type() + "'," +
                    "'" + eventData.getEvent_name() + "'," +
                    "'" + eventData.getEvent_type() + "'," +
                    "'" + eventData.getEvent_description() + "'," +
                    "'" + eventData.getEnd_message() + "'," +
                    "'" + eventData.getBroadcast_type() + "'," +
                    eventData.getReward_balance() + "," +
                    "'" + eventData.getReward_item() + "'," +
                    "'" + eventData.getReward_commands() + "');";
            plugin.mysql.execute(sql);
            int id = eventDatas.size() + 1;
            eventDatas.put(id, eventData);
        });
    }

    public static void editEvent(Man10EventData eventData){
        if(!eventDatas.containsKey(eventData.getId())){
            return;
        }
        eventDatas.put(eventData.getId(),eventData);
        saveEvent(eventData);
    }

    public static void saveEvent(Man10EventData eventData){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String sql = "UPDATE man10quest_event SET " +
                    "icon = '" + eventData.getIcon() + "'," +
                    "shown_type = '" + eventData.getShown_type() + "'," +
                    "event_name = '" + eventData.getEvent_name() + "'," +
                    "event_type = '" + eventData.getEvent_type() + "'," +
                    "event_description = '" + eventData.getEvent_description() + "'," +
                    "end_message = '" + eventData.getEnd_message() + "'," +
                    "broadcast_type = '" + eventData.getBroadcast_type() + "'," +
                    "reward_balance = " + eventData.getReward_balance() + "," +
                    "reward_items = '" + eventData.getReward_item() + "'," +
                    "reward_commands = '" + eventData.getReward_commands() + "' WHERE id = " + eventData.getId() + ";";
            plugin.mysql.execute(sql);
        });
    }

    public static void saveEventAll(){
        for(Man10EventData eventData:eventDatas.values()){
            saveEvent(eventData);
        }
    }

    public static void addPlayerQuest(Man10QuestData man10QuestData){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String sql = "INSERT INTO man10quest_player_data (event_id,event_name,event_type,name,uuid,data,finished,finished_time) VALUES (" +
                    man10QuestData.getEvent_id() + "," +
                    "'" + man10QuestData.getEvent_name() + "'," +
                    "'" + man10QuestData.getEvent_type() + "'," +
                    "'" + man10QuestData.getName() + "'," +
                    "'" + man10QuestData.getUuid() + "'," +
                    "'" + man10QuestData.getData() + "'," +
                    "" + man10QuestData.isFinished() + "," +
                    null + ");";
            plugin.mysql.execute(sql);
            HashMap<Integer, Man10QuestData> data = playerDatas.get(UUID.fromString(man10QuestData.getUuid()));
            data.put(playerDatas.size() + 1, man10QuestData);
            playerDatas.put(UUID.fromString(man10QuestData.getUuid()), data);
        });
    }

    public static void savePlayerQuest(Man10QuestData man10QuestData){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String sql = "UPDATE man10quest_player_data SET " +
                    "event_id = " + man10QuestData.getEvent_id() + "," +
                    "event_name = '" + man10QuestData.getEvent_name() + "'," +
                    "event_type = '" + man10QuestData.getEvent_type() + "'," +
                    "name = '" + man10QuestData.getName() + "'," +
                    "uuid = '" + man10QuestData.getUuid() + "'," +
                    "data = '" + man10QuestData.getData() + "'," +
                    "finished = " + man10QuestData.isFinished() + "," +
                    "finished_time = " + man10QuestData.getFinished_time() + " WHERE id = " + man10QuestData.getId() + ";";
            plugin.mysql.execute(sql);
        });
    }

    public static void successQuest(Man10QuestData man10QuestData){
        man10QuestData.setFinished(true);
        editPlayerQuest(man10QuestData);
        Man10EventData event = eventDatas.get(man10QuestData.getEvent_id());
        if(event.getBroadcast_type().equalsIgnoreCase("broadcast")){
            Bukkit.broadcastMessage(plugin.prefix+event.getEnd_message().replace("[user_name]",man10QuestData.getName()));
        }else if(event.getBroadcast_type().equalsIgnoreCase("title")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(event.getEnd_message(), null, 10, 10, 100);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0F, 1.0F);
            }
        }else{
           if(Bukkit.getPlayer(man10QuestData.getUuid())!=null){
               Bukkit.getPlayer(man10QuestData.getUuid()).sendMessage(plugin.prefix+event.getEnd_message().replace("[user_name]",man10QuestData.getName()));
           }
        }
        String[] commands = event.getReward_commands().split(" || ");
        for(String str:commands){
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),str.replace("[user_name]",man10QuestData.getName()));
        }
        plugin.vault.givePlayerMoney(UUID.fromString(man10QuestData.getUuid()),event.getReward_balance(),TransactionType.WIN,"quest reward id: "+event.getId());
    }

    public static void editPlayerQuest(Man10QuestData man10QuestData){
        if(!QuestAPI.playerDatas.containsKey(UUID.fromString(man10QuestData.getUuid()))){
            return;
        }
        HashMap<Integer,Man10QuestData> data = playerDatas.get(UUID.fromString(man10QuestData.getUuid()));
        data.put(man10QuestData.getId(),man10QuestData);
        QuestAPI.playerDatas.put(UUID.fromString(man10QuestData.getUuid()),data);
    }

    public static void savePlayerAll(){
        for(HashMap<Integer,Man10QuestData> data:QuestAPI.playerDatas.values()){
            for(Man10QuestData qdata : data.values()) {
                savePlayerQuest(qdata);
            }
        }
    }

    public static HashMap<Integer,Man10QuestData> getPlayerData(UUID uuid){
        if(uuid==null){
            return new HashMap<>();
        }
        return playerDatas.get(uuid);
    }


}
