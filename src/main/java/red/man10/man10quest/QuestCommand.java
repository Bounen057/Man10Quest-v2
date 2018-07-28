package red.man10.man10quest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import red.man10.man10quest.api.QuestCore;

public class QuestCommand implements CommandExecutor {
    Man10Quest plugin;
    boolean power = true;
    public QuestCommand(Man10Quest plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("mquest.use")) {
            return true;
        }
        if(args.length == 0){
            if (p.hasPermission("mquest.op")) {
                p.sendMessage("§e/mquest on/off : コマンドをオン・オフ");
            }
            p.sendMessage("§e/mquest list : 実行可能ジョブを確認");
            return true;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("on")){
                if (!p.hasPermission("mquest.op")) {
                    p.sendMessage("§c実行不能コマンド");
                    return true;
                }
                power = true;
                p.sendMessage("§emquestコマンドをon");
                return true;
            }else if(args[0].equalsIgnoreCase("off")) {
                if (!p.hasPermission("mquest.op")) {
                    p.sendMessage("§c実行不能コマンド");
                    return true;
                }
                power = false;
                p.sendMessage("§emquestコマンドをoff");
                return true;
            }else if(args[0].equalsIgnoreCase("list")) {
                QuestCore.listView(p.getUniqueId());
                return true;
            }
        }
        if (p.hasPermission("mquest.op")) {
            p.sendMessage("§e/mquest on/off : コマンドをオン・オフ");
        }
        p.sendMessage("§e/mquest list : 実行可能ジョブを確認");
        return true;
    }
}
