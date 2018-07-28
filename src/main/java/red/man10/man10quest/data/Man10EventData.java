package red.man10.man10quest.data;

import java.util.Date;

public class Man10EventData {
    private final int id;
    private String icon;
    private String shown_type;
    private String event_name;
    private String event_type;
    private String event_description;
    private String end_message;
    private String broadcast_type;
    private long reward_balance;
    private String reward_item;
    private String reward_commands;
    private Date time;

    public Man10EventData(int id,String icon,String shown_type,String event_name,String event_type,
                          String event_description,String end_message,String broadcast_type,long reward_balance,String reward_item,
                          String reward_commands, Date time){
        this.id = id;
        this.icon = icon;
        this.shown_type = shown_type;
        this.event_name = event_name;
        this.event_type = event_type;
        this.event_description = event_description;
        this.end_message = end_message;
        this.broadcast_type = broadcast_type;
        this.reward_balance = reward_balance;
        this.reward_item = reward_item;
        this.reward_commands = reward_commands;
        this.time = time;
    }

    public int getId(){
        return id;
    }

    public String getIcon(){
        return icon;
    }

    public String getShown_type(){
        return shown_type;
    }

    public String getEvent_name(){
        return event_name;
    }

    public String getEvent_type(){
        return event_type;
    }

    public String getEvent_description(){
        return event_description;
    }

    public String getBroadcast_type() {
        return broadcast_type;
    }

    public String getEnd_message() {
        return end_message;
    }

    public long getReward_balance(){
        return reward_balance;
    }

    public String getReward_item() {
        return reward_item;
    }

    public String getReward_commands(){
        return reward_commands;
    }

    public Date getTime(){
        return time;
    }

    //ここまで、get関数
    //ここから、set関数
    public void setIcon(String icon){
        this.icon = icon;
    }

    public void setShown_type(String shown_type){
        this.shown_type = shown_type;
    }

    public void setEvent_name(String event_name){
        this.event_name = event_name;
    }

    public void setEvent_type(String event_type){
        this.event_type = event_type;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public void setBroadcast_type(String broadcast_type) {
        this.broadcast_type = broadcast_type;
    }

    public void setEnd_message(String end_message) {
        this.end_message = end_message;
    }

    public void setReward_balance(long reward_balance) {
        this.reward_balance = reward_balance;
    }

    public void setReward_commands(String reward_commands) {
        this.reward_commands = reward_commands;
    }

    public void setReward_item(String reward_item) {
        this.reward_item = reward_item;
    }
}
