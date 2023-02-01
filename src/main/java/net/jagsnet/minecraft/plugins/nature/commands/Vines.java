package net.jagsnet.minecraft.plugins.nature.commands;

import net.jagsnet.minecraft.plugins.nature.Nature;
import net.jagsnet.minecraft.plugins.nature.otherStuff.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static net.jagsnet.minecraft.plugins.nature.otherStuff.Utils.*;
import static org.bukkit.Bukkit.getServer;

public class Vines implements CommandExecutor {
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
            Utils.sendMessage(p, "/vines <type> <direction> <size> <quantity> <clingyness> <thickness> <standardBlocks> <rootBlocks>");
            Utils.sendMessage(p, "/vines directions");
            Utils.sendMessage(p, "EXAMPLES - - - - - - -");
            Utils.sendMessage(p, "/vines vine westdown 30 1 3 2 90%oak_leaves,10%melon oak_wood");
            Utils.sendMessage(p, "/vines patch down 30 3 3 3 50%oak_leaves,50%stone oak_wood");
            return true;
        }

        if (args[0].equalsIgnoreCase("directions")) {
            Utils.sendMessage(p, "Goodluck looking through them all...");
            Utils.sendMessage(p, "northdown, eastdown, southdown, westdown, northup, eastup, southup, westup, wallwestup, wallwestdown, wallwestleft, wallwestright, walleastup, walleastdown, walleastleft, walleastright, wallnorthup, wallnorthdown, wallnorthleft, wallnorthright, wallsouthup, wallsouthdown, wallsouthleft, wallsouthright");
            return true;
        }

        int size = Integer.parseInt(args[2]);
        int count = Integer.parseInt(args[3]);
        int clingyness = Integer.parseInt(args[4]);
        int thiccness = Integer.parseInt(args[5]);

        if (size > 200 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max vine size is 200");
            return true;
        }

        if (count > 7 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max count is 7");
            return true;
        }

        if (clingyness > 6 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max clingyness is 6");
            return true;
        }

        if (thiccness > 6 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max thickness is 6");
            return true;
        }

        String[] standardBlocks = args[6].toLowerCase().split(",");
        String[] otherBlocks = args[7].toLowerCase().split(",");

        Long time = System.currentTimeMillis();

        switch (args[0].toLowerCase()) {
            case "patch":
                switch (args[1].toLowerCase()) {
                    case "down":
                        foreach(count, 1, p, size, "northdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "eastdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "southdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "westdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    case "up":
                        foreach(count, 1, p, size, "northup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "eastup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "southup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "westup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    case "east":
                        foreach(count, 1, p, size, "walleastup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "walleastdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "walleastleft", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "walleastleft", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    case "west":
                        foreach(count, 1, p, size, "wallwestup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallwestdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallwestleft", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallwesteast", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    case "north":
                        foreach(count, 1, p, size, "wallnorthup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallnorthdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallnorthleft", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallnortheast", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    case "south":
                        foreach(count, 1, p, size, "wallsouthup", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallsouthdown", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallsouthleft", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        foreach(count, 1, p, size, "wallsouthright", clingyness, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    default:
                        break;
                }
                break;
            case "vine":
                vine(p.getLocation(), args[1].toLowerCase(), size, p, clingyness, standardBlocks, otherBlocks, thiccness, time);
                break;
            default:
                Utils.sendMessage(p, "You didn't type a valid /vines commands");
                Utils.sendMessage(p, "/vines <type> <pattern> <size> <quantity> <clingyness> <standardBlocks> <specialBlocks> <otherBlocks>");
                Utils.sendMessage(p, "/vines vine westdown 30 1 3 oak_leaves,stone melon oak_wood");
                break;
        }

        return true;
    }


    static void foreach(int count, int delay, Player p, int size, String pattern, int strength, String[] standardBlocks, String[] otherBlocks, int thiccness, Long time) {
        int d = 0;
        while (count > 0) {
            d = d + delay;
            spawn(d, p.getLocation(), p, size, pattern, strength, standardBlocks, otherBlocks, thiccness, time);
            count--;
        }
    }

    static void spawn(int delay, Location loc, Player p, int size, String pattern, int strength, String[] standardBlocks, String[] otherBlocks, int thiccness, Long time) {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Nature.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location center = loc;
                center.setX(center.getX() + ThreadLocalRandom.current().nextInt(-5, 5));
                center.setZ(center.getZ() + ThreadLocalRandom.current().nextInt(-5, 5));
                center.setY(center.getY() + 10);
                double floor = center.getY() - 20;
                while (center.getY() > floor) {
                    if (pasteable(loc, 0, 0, 0)) {
                        center.add(0, 1, 0);
                        vine(center, pattern, size, p, strength, standardBlocks, otherBlocks, thiccness, time);
                        break;
                    }
                    center.add(0, -1, 0);
                }
            }
        }, delay);
    }

    static void vine(Location loc, String pattern, int size, Player p, int strength, String[] standardBlocks, String[] otherBlocks, int thiccness, Long time) {
        switch (pattern) {
            case "westdown":
                genVine(loc, size, 0, -1, 1, -1, 1, -1, "down", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "northdown":
                genVine(loc, size, 1, -1, 1, -1, 0, -1, "down", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "eastdown":
                genVine(loc, size, 1, -0, 1, -1, 1, -1, "down", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "southdown":
                genVine(loc, size, 1, -1, 1, -1, 1, 0, "down", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "westup":
                genVine(loc, size, 0, -1, 1, -1, 1, -1, "up", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "northup":
                genVine(loc, size, 1, -1, 1, -1, 0, -1, "up", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "eastup":
                genVine(loc, size, 1, -0, 1, -1, 1, -1, "up", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "southup":
                genVine(loc, size, 1, -1, 1, -1, 1, 0, "up", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallwestup":
                genVine(loc, size, 1, -1, 1, 0, 1, -1, "west", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallwestdown":
                genVine(loc, size, 1, -1, 0, -1, 1, -1, "west", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallwestleft":
                genVine(loc, size, 1, -1, 1, -1, 1, 0, "west", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallwestright":
                genVine(loc, size, 1, -1, 1, -1, 0, -1, "west", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "walleastup":
                genVine(loc, size, 1, -1, 1, 0, 1, -1, "east", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "walleastdown":
                genVine(loc, size, 1, -1, 0, -1, 1, -1, "east", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "walleastleft":
                genVine(loc, size, 1, -1, 1, -1, 0, -1, "east", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "walleastright":
                genVine(loc, size, 1, -1, 1, -1, 1, 0, "east", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallnorthup":
                genVine(loc, size, 1, -1, 1, 0, 1, -1, "north", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallnorthdown":
                genVine(loc, size, 1, -1, 0, -1, 1, -1, "north", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallnorthleft":
                genVine(loc, size, 0, -1, 1, -1, 1, -1, "north", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallnorthright":
                genVine(loc, size, 1, 0, 1, -1, 1, -1, "north", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallsouthup":
                genVine(loc, size, 1, -1, 1, 0, 1, -1, "south", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallsouthdown":
                genVine(loc, size, 1, -1, 0, -1, 1, -1, "south", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallsouthleft":
                genVine(loc, size, 1, 0, 1, -1, 1, -1, "south", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            case "wallsouthright":
                genVine(loc, size, 0, -1, 1, -1, 1, -1, "south", strength, standardBlocks, otherBlocks, thiccness, p, time);
                break;
            default:
                break;
        }
    }

    static void genVine(Location loc, int size, int maxx, int minx, int maxy, int miny, int maxz, int minz, String cling, int strength, String[] standardBlocks, String[] otherBlocks, int thiccness, Player p, Long time) {
        for (int i = size; i > 0; i--) {
            
            setBlock(loc, 0, 0, 0, standardBlocks, false, p, time);


            if (thiccness > 1) {
                for (int thiccsize = thiccness; thiccsize > 1; thiccsize--) {
                    ArrayList<Location> thicc = new ArrayList<>();
                    for (int x = 1; x >= -1; x--) {
                        for (int y = 1; y >= -1; y--) {
                            for (int z = 1; z >= -1; z--) {
                                if (pasteable(loc, x, y, z)) {
                                    thicc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                }
                            }
                        }
                    }
                    Random generator = new Random();
                    int freeLocLoc = generator.nextInt(thicc.size());
                    Location thiccLoc = thicc.get(freeLocLoc);
                    setBlock(thiccLoc, 0, 0, 0, standardBlocks, false, p, time);
                }
            }

            int chance = ThreadLocalRandom.current().nextInt(0, 10);
            if (chance == 8) {
                setBlock(loc, 0, -1, 0, otherBlocks, true, p, time);
            }

            ArrayList<Location> freeLoc = new ArrayList<>();

            for (int x = maxx; x >= minx; x--) {
                for (int y = maxy; y >= miny; y--) {
                    for (int z = maxz; z >= minz; z--) {
                        if (pasteable(loc, x, y, z)) {
                            switch (cling) {
                                case "down":
                                    if (y <= 0) {
                                        for (int j = strength; j > 0; j--) {
                                            freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                        }
                                    } else {
                                        freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                    }
                                    break;
                                case "up":
                                    if (y >= 0) {
                                        for (int j = strength; j > 0; j--) {
                                            freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                        }
                                    } else {
                                        freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                    }
                                    break;
                                case "west":
                                    if (x <= 0) {
                                        for (int j = strength; j > 0; j--) {
                                            freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                        }
                                    } else {
                                        freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                    }
                                    break;
                                case "north":
                                    if (z <= 0) {
                                        for (int j = strength; j > 0; j--) {
                                            freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                        }
                                    } else {
                                        freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                    }
                                    break;
                                case "east":
                                    if (x >= 0) {
                                        for (int j = strength; j > 0; j--) {
                                            freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                        }
                                    } else {
                                        freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                    }
                                    break;
                                case "south":
                                    if (z >= 0) {
                                        for (int j = strength; j > 0; j--) {
                                            freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                        }
                                    } else {
                                        freeLoc.add(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z));
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }

            if (freeLoc.size() > 0) {
                Random generator = new Random();
                int freeLocLoc = generator.nextInt(freeLoc.size());
                loc = freeLoc.get(freeLocLoc);
            }

        }
    }
}
