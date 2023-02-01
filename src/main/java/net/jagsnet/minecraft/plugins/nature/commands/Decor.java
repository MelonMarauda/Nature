package net.jagsnet.minecraft.plugins.nature.commands;

import net.jagsnet.minecraft.plugins.nature.otherStuff.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

import static net.jagsnet.minecraft.plugins.nature.otherStuff.Utils.sendMessage;

public class Decor implements CommandExecutor {
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
            Utils.sendMessage(p, "/trees <pattern> <variati");
            Utils.sendMessage(p, "EXAMPLES - - - - - - -");
            return true;
        }

        if (args[0].equalsIgnoreCase("variations")) {
            Utils.sendMessage(p, "Pointy");
        }

        int size;
        int stems;

        if (args[2].contains("-")) {
            size = ThreadLocalRandom.current().nextInt(Integer.parseInt(args[2].split("-")[0]), Integer.parseInt(args[2].split("-")[1])+1);
        } else {size = Integer.parseInt(args[2]);}

        if (args[3].contains("-")) {
            stems = ThreadLocalRandom.current().nextInt(Integer.parseInt(args[3].split("-")[0]), Integer.parseInt(args[3].split("-")[1])+1);
        } else {stems = Integer.parseInt(args[3]);}

        if (size > 10 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max size is 10");
            return true;
        }

        if (stems > 7 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max stems is 7");
            return true;
        }

        String[] standardBlocks = args[4].toLowerCase().split(",");
        String[] specialBlocks = args[5].toLowerCase().split(",");
        String[] otherBlocks = args[6].toLowerCase().split(",");
        String[] tip = args[7].toLowerCase().split(",");

        int thiccness = 1;
        int branchdensity = 1;

        boolean straight = Boolean.parseBoolean(args[8]);

        Long time = System.currentTimeMillis();

        switch (args[1].toLowerCase()) {
            case "deadflat":

            default:
                sendMessage(p, "Not a valid decor type");
                break;
        }



        return true;
    }
}
