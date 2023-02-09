package net.jagsnet.minecraft.plugins.nature.commands;

import net.jagsnet.minecraft.plugins.nature.otherStuff.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.jagsnet.minecraft.plugins.nature.otherStuff.Utils.*;

public class NatureMain implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Needs to be run by a player.");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("nature.use")) {
            sendMessage(p, "You do not have permission to use this command. If you believe you do require permission please contact your nearest admin.");
            return true;
        }

        if (args.length < 1) {
            sendMessage(p, "/nature undo");
            sendMessage(p, "/nature version");
            return true;
        }

        if (args[0].equalsIgnoreCase("version")) {
            sendMessage(p, "Nature Version 1.0");
            return true;
        }

        if (args[0].equalsIgnoreCase("undo")) {
            undo(p);
            return true;
        }

        if (args[0].equalsIgnoreCase("undos")) {
            sendMessage(p, String.valueOf(undos.size()));
            sendMessage(p, String.valueOf(undos.get(p.getUniqueId()).size()));
            return true;
        }

        return true;
    }
}
