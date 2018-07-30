package red.man10.man10quest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import red.man10.man10quest.api.QuestAPI;
import red.man10.man10quest.api.QuestCore;
import red.man10.man10quest.data.Man10EventData;

public class QuestCommand implements CommandExecutor {
    Man10Quest plugin;
    boolean power = true;
    public QuestCommand(Man10Quest plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if(args.length == 3){
                if(args[0].equalsIgnoreCase("open")){
                    int quest_id;
                    try {
                        quest_id = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        plugin.getLogger().info("questIDは数字で");
                        return true;
                    }
                    if(Bukkit.getPlayer(args[2])==null){
                        plugin.getLogger().info("プレイヤーがオフライン");
                        return true;
                    }
                    Man10EventData data = QuestAPI.getQuestfromId(quest_id);
                    if(data == null){
                        plugin.getLogger().info("そのIDのクエストは存在しない");
                        return true;
                    }
                    int id = QuestAPI.createPlayerQuest(quest_id,data.getEvent_name(),data.getEvent_type(),Bukkit.getPlayer(args[2]).getName(),Bukkit.getPlayer(args[2]).getUniqueId().toString(),"Empty",false);
                    if(id == -1){
                        plugin.getLogger().info("オープン失敗");
                        return true;
                    }
                    plugin.getLogger().info("成功。§eIDは"+id);
                    return true;
                }
            }else if(args.length == 8) {
                if (args[0].equalsIgnoreCase("create")) {
                    long bal;
                    try {
                        bal = Long.parseLong(args[7]);
                    } catch (NumberFormatException e) {
                        plugin.getLogger().info("reward_bal は数字");
                        return true;
                    }
                    int id = QuestAPI.createQuest(null, args[3], args[1], args[2], args[4], args[5], args[6], bal,null,null);
                    if(id == -1){
                        plugin.getLogger().info("作成失敗 クエスト名重複？");
                        return true;
                    }
                    plugin.getLogger().info("成功。IDは"+id);
                    return true;
                }
            }
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("mquest.use")) {
            return true;
        }
        if(args.length == 0){
            if (p.hasPermission("mquest.op")) {
                p.sendMessage(plugin.prefix+"§e/mquest on/off : コマンドをオン・オフ");
                p.sendMessage(plugin.prefix+"§e/mquest open [クエストID] [プレイヤー名] : クエストを開放");
                p.sendMessage(plugin.prefix+"§e/mquest create [quest_name] [quest_type] [shown_type] [event_description] [end_message] [broadcast_type] [reward_bal] : クエスト作成");
            }
            p.sendMessage(plugin.prefix+"§e/mquest list : 実行可能ジョブを確認");
            return true;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("on")){
                if (!p.hasPermission("mquest.op")) {
                    p.sendMessage(plugin.prefix+"§c実行不能コマンド");
                    return true;
                }
                power = true;
                p.sendMessage(plugin.prefix+"§emquestコマンドをon");
                return true;
            }else if(args[0].equalsIgnoreCase("off")) {
                if (!p.hasPermission("mquest.op")) {
                    p.sendMessage(plugin.prefix+"§c実行不能コマンド");
                    return true;
                }
                power = false;
                p.sendMessage(plugin.prefix+"§emquestコマンドをoff");
                return true;
            }else if(args[0].equalsIgnoreCase("list")) {
                QuestCore.listView(p.getUniqueId());
                return true;
            }
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("open")){
                if (!p.hasPermission("mquest.op")) {
                    p.sendMessage(plugin.prefix+"§c実行不能コマンド");
                    return true;
                }
                int quest_id;
                try {
                    quest_id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    p.sendMessage(plugin.prefix + "§cid は数字");
                    return true;
                }
                if(Bukkit.getPlayer(args[2])==null){
                    p.sendMessage(plugin.prefix + "§cプレイヤーがオフライン");
                    return true;
                }
                Man10EventData data = QuestAPI.getQuestfromId(quest_id);
                if(data == null){
                    p.sendMessage(plugin.prefix + "§cそのIDのクエストは存在しない");
                    return true;
                }
                int id = QuestAPI.createPlayerQuest(quest_id,data.getEvent_name(),data.getEvent_type(),Bukkit.getPlayer(args[2]).getName(),Bukkit.getPlayer(args[2]).getUniqueId().toString(),"Empty",false);
                if(id == -1){
                    p.sendMessage(plugin.prefix+"§cオープン失敗");
                    return true;
                }
                p.sendMessage(plugin.prefix+ "§a成功。§eIDは"+id);
                return true;
            }
        }else if(args.length == 8) {
            if (args[0].equalsIgnoreCase("create")) {
                if (!p.hasPermission("mquest.op")) {
                    p.sendMessage(plugin.prefix+"§c実行不能コマンド");
                    return true;
                }
                long bal;
                try {
                    bal = Long.parseLong(args[7]);
                } catch (NumberFormatException e) {
                    p.sendMessage(plugin.prefix + "§creward_bal は数字");
                    return true;
                }
                int id = QuestAPI.createQuest(null, args[3], args[1], args[2], args[4], args[5], args[6], bal,null,null);
                if(id == -1){
                    p.sendMessage(plugin.prefix+"§c作成失敗 クエスト名重複？");
                    return true;
                }
                p.sendMessage(plugin.prefix+ "§a成功。§eIDは"+id);
                return true;
            }
        }
        if (p.hasPermission("mquest.op")) {
            p.sendMessage(plugin.prefix+"§e/mquest on/off : コマンドをオン・オフ");
            p.sendMessage(plugin.prefix+"§e/mquest open [クエストID] [プレイヤー名] : クエストを開放");
            p.sendMessage(plugin.prefix+"§e/mquest create [quest_name] [quest_type] [shown_type] [event_description] [end_message] [broadcast_type] [reward_bal] : クエスト作成");
        }
        p.sendMessage(plugin.prefix+"§e/mquest list : 実行可能ジョブを確認");
        return true;
    }
}
