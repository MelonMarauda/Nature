package net.jagsnet.minecraft.plugins.nature.otherStuff;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static void sendMessage(Player player, String msg){
        String bold = ChatColor.BOLD + "";
        player.sendMessage(ChatColor.GREEN + bold + "NATURE" + ChatColor.DARK_GRAY + bold + " > " + ChatColor.WHITE + msg);
    }

    public static void setCyl(Location loc, int radius, String[] blocks, boolean inner, boolean nubs, boolean ignoreBlocks, Player p, Long time){
        for (int i = radius; i > 0; i--) {
            setCircle(loc, i, blocks, inner, nubs, ignoreBlocks, p, time);
        }
    }

    public static void setCircle(Location loc, int radius, String[] blocks, boolean inner, boolean nubs, boolean ignoreBlocks, Player p, Long time){
        HashSet<Location> circleLocations = CircleGenerator.generateCircle(loc, radius, CircleGenerator.Plane.XZ, inner, nubs);
        for (Location l : circleLocations) {
            setBlock(l, 0, 0, 0, blocks, ignoreBlocks, p, time);
        }
    }

    public static List<Location> setCurve(int segs, Location p0, Location p1, Location p2, String[] blocks, boolean ignoreBlocks, Player p, Long time) {
        List<Location> locs = CurveGen.bezierCurve(segs, p0, p1, p2);
        for (Location l : locs) {
            Location loc = l.clone();
            loc.setX(Math.floor(l.getX()));
            loc.setY(Math.floor(l.getY()));
            loc.setZ(Math.floor(l.getZ()));
            setBlock(loc, 0, 0, 0, blocks, ignoreBlocks, p, time);
        }
        return locs;
    }

    public static void setBlock(Location inputLoc, int x, int y, int z, String[] blocks, boolean ignoreBlocks, Player p, Long time){
        Location loc = new Location(inputLoc.getWorld(), inputLoc.getX() + x, inputLoc.getY() + y, inputLoc.getZ() + z);
        Material m = loc.getBlock().getType();
        if (ignoreBlocks || pasteable(loc, x, y, z)) {
            saveBlock(loc, time, p, m);
            if (blocks.length == 1) {
                loc.getBlock().setType(Material.getMaterial(blocks[0].toUpperCase()));
                return;
            }
            int chance = 0;
            int rand = ThreadLocalRandom.current().nextInt(1, 101);
            for (String s : blocks) {
                String[] b = s.split("%");
                chance += Integer.parseInt(b[0]);
                if (chance >= rand) {
                    loc.getBlock().setType(Material.getMaterial(b[1].toUpperCase()));
                    return;
                }
            }
        }
    }

    public static void saveBlock(Location loc, Long time, Player p, Material m) {
        UUID u = p.getUniqueId();
        if (undos.containsKey(u)) {
            if (undos.get(u).containsKey(time)) {
                if (undos.get(u).get(time).containsKey(loc)) {return;}
                undos.get(u).get(time).put(loc, m);
                return;
            }
            if (undos.get(u).size() >= 10) {
                Long key = 9668417520000L;
                for (int i = undos.get(u).size() - 1; i >= 0; i--) {
                    if ((Long) undos.get(u).keySet().toArray()[i] < key) {
                        key = (Long) undos.get(u).keySet().toArray()[i];
                    }
                }
                undos.get(u).remove(key);
            }
            undos.get(u).put(time, new HashMap<Location, Material>());
            undos.get(u).get(time).put(loc, m);
            return;
        }
        undos.put(u, new HashMap<Long, HashMap<Location, Material>>());
        undos.get(u).put(time, new HashMap<Location, Material>());
        undos.get(u).get(time).put(loc, m);
    }

    public static void undo(Player p){
        UUID u = p.getUniqueId();
        if (undos.containsKey(u)) {
            Long key = 0L;
            for (int i = undos.get(u).size() - 1; i >= 0; i--) {
                if ((Long) undos.get(u).keySet().toArray()[i] > key) {
                    key = (Long) undos.get(u).keySet().toArray()[i];
                }
            }
            if (key > 0) {
                HashMap<Location, Material> locs = undos.get(u).get(key);
                Set<Location> locationSet = locs.keySet();
                for (Location loc : locationSet) {
                    Material m;
                    if (locs.get(loc) == null) {
                        m = Material.AIR;
                    } else {m = locs.get(loc);}
                    loc.getBlock().setType(m);
                }
                undos.get(u).remove(key);
            }
        }
    }

    public static Block getBlock(Location inputLoc, int x, int y, int z) {
        Location loc = new Location(inputLoc.getWorld(), inputLoc.getX(), inputLoc.getY(), inputLoc.getZ());
        return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z).getBlock();
    }

    public static boolean pasteable(Location loc, int x, int y, int z) {
        if ((getBlock(loc, x, y, z).getType() == Material.AIR) ||
                (getBlock(loc, x, y, z).getType() == Material.GRASS) ||
                (getBlock(loc, x, y, z).getType() == Material.CAVE_AIR) ||
                (getBlock(loc, x, y, z).getType() == Material.VOID_AIR) ||
                (getBlock(loc, x, y, z).getType() == Material.TALL_GRASS)) {
            return true;
        }
        return false;
    }

    public static HashMap<UUID, HashMap<Long, HashMap<Location, Material>>> undos = new HashMap<UUID, HashMap<Long, HashMap<Location, Material>>>();
}
