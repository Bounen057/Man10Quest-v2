package red.man10.man10quest.data;

import java.util.Date;

public class Man10QuestData {
    private final int id;
    private int event_id;
    private String event_name;
    private String event_type;
    private String name;
    private String uuid;
    private String data;
    private boolean finished;
    private Date start_time;
    private Date finished_time;

    public Man10QuestData(int id,int event_id,String event_name,String event_type,String name,String uuid,String data,boolean finished,Date start_time,Date finished_time){
        this.id = id;
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_type = event_type;
        this.name = name;
        this.uuid = uuid;
        this.data = data;
        this.finished = finished;
        this.start_time = start_time;
        this.finished_time = finished_time;
    }

    public int getId() {
        return id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_type() {
        return event_type;
    }

    public String getName() {
        return name;
    }

    public String getUuid(){
        return uuid;
    }

    public String getData() {
        return data;
    }

    public boolean isFinished() {
        return finished;
    }

    public Date getStart_time(){
        return start_time;
    }

    public Date getFinished_time() {
        return finished_time;
    }

    //ここまで、get関数
    //ここから、set関数

    public void setEvent_id(int id) {
        this.event_id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setFinished_time(Date finished_time) {
        this.finished_time = finished_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
}
