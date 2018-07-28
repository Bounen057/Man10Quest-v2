package red.man10.man10quest;

import org.bukkit.plugin.java.JavaPlugin;
import red.man10.man10quest.api.QuestAPI;
import red.man10.man10vaultapiplus.Man10VaultAPI;

public final class Man10Quest extends JavaPlugin {

    public MySQLManager mysql;
    public Man10VaultAPI vault;
    public String prefix = "§d§l[§e§lM§6§lQuest§d§l]§r";

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig(); // config setUp
        mysql = new MySQLManager(this,"MQuest"); //MySQL setUp
        vault = new Man10VaultAPI("MQuest"); // Vault setUp
        QuestAPI.loadEnable(this); //Quest API setUp
        getCommand("mquest").setExecutor(new QuestCommand(this)); // Quest Command setUp
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        QuestAPI.saveEventAll();
        QuestAPI.savePlayerAll();
    }
}
