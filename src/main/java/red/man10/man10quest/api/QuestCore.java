package red.man10.man10quest.api;

import org.bukkit.Bukkit;
import red.man10.man10quest.data.Man10EventData;
import red.man10.man10quest.data.Man10QuestData;

import java.util.UUID;

public class QuestCore {

    public static boolean dataIsHide(Man10EventData data){
        return data.getShown_type().equalsIgnoreCase("hide");
    }

    public static boolean dataIsFree(Man10EventData data){
        return data.getShown_type().equalsIgnoreCase("free");
    }

    public static boolean dataIsnoOpenHide(Man10EventData data){
        return data.getShown_type().equalsIgnoreCase("hide") || data.getShown_type().equalsIgnoreCase("no_open_hide");
    }

    public static boolean dataIsOpenHide(Man10EventData data){
        return data.getShown_type().equalsIgnoreCase("hide") || data.getShown_type().equalsIgnoreCase("open_hide");
    }

    public static void listView(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(QuestAPI.plugin, () -> {
            if (!QuestAPI.playerDatas.containsKey(uuid)) {
                Bukkit.getPlayer(uuid).sendMessage("§cあなたが待機中のクエストは存在しない");
                return;
            }
            for (Man10QuestData data : QuestAPI.playerDatas.get(uuid).values()) {
                Man10EventData event = QuestAPI.getQuestfromId(data.getId());
                if (event == null) {
                    continue;
                }
                if (dataIsnoOpenHide(event)) {
                    continue;
                }
                Bukkit.getPlayer(uuid).sendMessage(QuestAPI.plugin.prefix + data.getEvent_id() + ": §e" + event.getEvent_name() + " §6" + event.getEvent_description());
            }
        });
    }

}
