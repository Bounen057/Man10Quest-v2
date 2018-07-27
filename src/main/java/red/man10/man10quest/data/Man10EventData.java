package red.man10.man10quest.data;

public class Man10EventData {
    private int id;
    private String icon;
    private String shown_type;
    private String event_name;
    private String event_type;
    private String event_description;
    private long reward_balance;
    private String reward_item;
    private String reward_commands;

    public Man10EventData(int id,String icon,String shown_type,String event_name,String event_type,String event_description,long reward_balance,String reward_item,String reward_commands){
        this.id = id;
        this.icon = icon;
        this.shown_type = shown_type;
        this.event_name = event_name;
        this.event_type = event_type;
        this.event_description = event_description;
        this.reward_balance = reward_balance;
        this.reward_item = reward_item;
        this.reward_commands = reward_commands;
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

    public long getReward_balance(){
        return reward_balance;
    }

    public String getReward_item() {
        return reward_item;
    }

    public String getReward_commands(){
        return reward_commands;
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
