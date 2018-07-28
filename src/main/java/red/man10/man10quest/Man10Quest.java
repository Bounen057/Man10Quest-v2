package red.man10.man10quest;

import org.bukkit.plugin.java.JavaPlugin;
import red.man10.man10quest.api.QuestAPI;

public final class Man10Quest extends JavaPlugin {

    public MySQLManager mysql;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig(); // config setUp
        mysql = new MySQLManager(this,"MQuest"); //MySQL setUp
        QuestAPI.loadEnable(this); //Quest API setUp
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        QuestAPI.saveEventAll();
        QuestAPI.savePlayerAll();
    }
}
