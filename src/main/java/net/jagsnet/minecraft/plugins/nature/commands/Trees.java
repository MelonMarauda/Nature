package net.jagsnet.minecraft.plugins.nature.commands;

import net.jagsnet.minecraft.plugins.nature.otherStuff.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static net.jagsnet.minecraft.plugins.nature.otherStuff.Utils.*;

public class Trees implements CommandExecutor {
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
            Utils.sendMessage(p, "/trees <pattern> <variation> <stemHeightRange> <stemCountRange> <logBlocks> <rootBlocks> <leafBlocks> <tipBlocks> <straight> <thinness> <branchDensity>");
            Utils.sendMessage(p, "/trees variations");
            Utils.sendMessage(p, "EXAMPLES - - - - - - -");
            Utils.sendMessage(p, "/trees tree pointy 4-6 3-5 birch_wood birch_wood birch_leaves birch_leaves false 1");
            Utils.sendMessage(p, "/trees tree widepointy 6 5 birch_wood birch_wood 90%birch_leaves,10%air birch_leaves true 4");
            return true;
        }

        if (args[0].equalsIgnoreCase("variations")) {
            Utils.sendMessage(p, "Pointy, WidePointy, Palm, PointyPalm, StarPalm, DroopyPalm, DensePalm, DeadPointy, DeadFlat");
            return true;
        }

        int size = rangePick(args[2]);
        int stems = rangePick(args[3]);

        if (size > 10 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max size is 10");
            return true;
        }

        if (stems > 7 && !p.hasPermission("nature.norestrictions")) {
            Utils.sendMessage(p, "Max stems is 7");
            return true;
        }

        String standardBlocks = args[4].toLowerCase();
        String specialBlocks = args[5].toLowerCase();
        String otherBlocks = args[6].toLowerCase();
        String tip = args[7].toLowerCase();

        int thiccness = 1;
        int branchdensity = 1;

        boolean straight = Boolean.parseBoolean(patternPick(args[8]));

        Long time = System.currentTimeMillis();

        String type = patternPick(args[1]).toLowerCase();

        boolean branching = false;
        if (type.contains("branching")) {
            branching = true;
            type = type.replace("branching", "");
        }

        switch (type) {
            case "deadflat":
            case "deadpointy":
                branchdensity = rangePick(args[10]);
            case "widepointy":
            case "palm":
            case "pointypalm":
            case "starpalm":
            case "droopypalm":
            case "densepalm":
            case "pointy":
                thiccness = rangePick(args[9]);
                genPointyTree(p.getTargetBlock(null, 100).getLocation(), size, stems, standardBlocks, specialBlocks, otherBlocks, tip, type, straight, thiccness, p, time, branchdensity, branching, true);
                break;
            default:
                sendMessage(p, "Not a valid tree type");
                break;
        }



        return true;
    }

    static void genPointyTree(Location loc, int size, int stems, String standardBlocks, String specialBlocks, String otherBlocks, String tip, String leafPattern, boolean straight, int thiccness, Player p, Long time, int branchdensity, boolean branching, boolean first) {

        //roots
        if (first) {
            ArrayList<String> locs = new ArrayList<>();
            locs.add("-1,-1"); locs.add("-1,0"); locs.add("-1,1"); locs.add("0,-1"); locs.add("0,1"); locs.add("1,-1"); locs.add("1,0"); locs.add("1,1");
            for (int i = 3; i > 0; i--){
                int locloc = ThreadLocalRandom.current().nextInt(0, locs.size());
                String offset = locs.get(locloc);
                locs.remove(locloc);

                int x = Integer.parseInt(offset.split(",")[0]);
                int y = ThreadLocalRandom.current().nextInt(-1, 2);
                int z = Integer.parseInt(offset.split(",")[1]);

                setBlock(loc, x, y, z, specialBlocks, true, p, time);
                setBlock(loc, x, y - 1, z, specialBlocks, true, p, time);
                setBlock(loc, x, y - 2, z, specialBlocks, true, p, time);
            }
        }

        // --------------------------------------------------------------------
        // STEM
        // --------------------------------------------------------------------
        int length = stems * size;
        int state = 0;
        for (int i = stems; i > 0; i--) {
            for (int j = size; j > 0; j--) {
                setBlock(loc, 0, 0, 0, standardBlocks, true, p, time);

                // --------------------------------------------------------------------
                // LEAVES
                // --------------------------------------------------------------------
                switch (leafPattern) {
                    case "pointy" :
                        genPointyTreeLeaves(state, length, loc, otherBlocks, p, time);
                        break;
                    case "widepointy" :
                        genwidepointyTreeLeaves(state, length, loc, otherBlocks, stems, thiccness, p, time);
                        break;
                    case "deadpointy":
                        if (state > (length/3)) {genDeadBranches(length, stems, loc, otherBlocks, thiccness, p, time, 0.5f, branchdensity);}
                        break;
                    case "deadflat":
                        if (state > (length/3)) {genDeadBranches(length, stems, loc, otherBlocks, thiccness, p, time, -1.0f, branchdensity);}
                        break;
                    default: break;
                }

                if (branching && first && state > length/2) {
                    genPointyTree(loc.clone(), size, stems, standardBlocks, specialBlocks, otherBlocks, tip, leafPattern, straight, thiccness, p, time, branchdensity, false, false);
                    branching = false;
                    first = false;
                }

                loc.add(0, 1, 0);
                state++;
            }

            // --------------------------------------------------------------------
            // STEM CHANGE
            // --------------------------------------------------------------------
            switch (leafPattern) {
                case "pointy" :
                    genPointyTreeLeaves(state, length, loc, otherBlocks, p, time);
                    genPointyTreeLeaves(state, length, new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ()), otherBlocks, p, time);
                    break;
                case "widepointy" :
                    setCyl(loc, 1, otherBlocks, true, true, false, p, time);
                    setCyl(new Location(loc.getWorld(), loc.getX(), loc.getY() - 2, loc.getZ()), 1, otherBlocks, true, true, false, p, time);
                    break;
                default: break;
            }

            if (!straight) {
                if (i != 1) {
                    int x = ThreadLocalRandom.current().nextInt(-1, 2);
                    int z = ThreadLocalRandom.current().nextInt(-1, 2);
                    loc.add(x, 0, z);
                }
            }
        }

        // --------------------------------------------------------------------
        // GENERATE TREE TIP
        // --------------------------------------------------------------------
        switch (leafPattern) {
            case "pointy":
                for (int i = stems * 2; i > 0; i--) {
                    setBlock(loc, 0, 0, 0, tip, true, p, time);
                    loc.add(0, 1, 0);
                }
                break;
            case "widepointy":
                for (int i = stems; i > 0; i--) {
                    setBlock(loc, 0, 0, 0, tip, true, p, time);
                    loc.add(0, 1, 0);
                }
                break;
            case "palm":
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, -1f);
                break;
            case "pointypalm":
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, 0.5f);
                break;
            case "starpalm":
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, -1.5f);
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, 0.5f);
                break;
            case "droopypalm":
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, -2f);
                break;
            case "densepalm":
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, -1.0f);
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, -2.5f);
                genPalmLeaves(length, stems, loc,otherBlocks, thiccness, p, time, 0.5f);
                break;
            default: break;
        }
    }

    static void genPointyTreeLeaves(int state, int length, Location loc, String otherBlocks, Player p, Long time) {
        if (state > 3) {
            if (state < length * 0.25) {
                setCyl(loc, 1, otherBlocks, true, true, false, p, time);
            } else if (state < length * 0.75) {
                setCyl(loc, 2, otherBlocks, true, true, false, p, time);
            } else if (state >= length * 0.75) {
                setCyl(loc, 1, otherBlocks, true, true, false, p, time);
            }
        }
    }

    static void genwidepointyTreeLeaves(int state, int length, Location loc, String otherBlocks, int stems, int thiccness, Player p, Long time) {
        if (state > 5) {
            if ((state % 2) == 0) {
                setCyl(loc, ((length - state + stems) / thiccness / 2), otherBlocks, true, true, false, p, time);
            } else {
                setCyl(loc, ((length - state + stems) / thiccness), otherBlocks, true, true, false, p, time);
            }
        } else if (state > 3) {
            if ((state % 2) == 0) {
                setCyl(loc, ((length - state + stems) / thiccness / 2 / 2), otherBlocks, true, true, false, p, time);
            } else {
                setCyl(loc, ((length - state + stems) / thiccness / 2), otherBlocks, true, true, false, p, time);
            }
        }
    }

    static void genPalmLeaves(int length, int stems, Location loc, String otherBlocks, int thiccness, Player p, Long time, float height) {
        int size = length * stems;
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                if (z != 0 || x != 0) {
                    float div = 1;
                    if (z != 0 && x != 0) {
                        div = 1.4f;
                    }
                    Location l0 = loc.clone().add(0.5, -1.5, 0.5);
                    Location l1 = l0.clone().add((((size/thiccness) * x) + pm())/div, (size/8) + pm(), (((size/thiccness) * z) + pm())/div);
                    Location l2 = l1.clone().add((((size/thiccness) * x) + pm())/div, ((size/16) * height) + pm(), (((size/thiccness) * z) + pm())/div);
                    Utils.setCurve((size*2), l0, l1, l2, otherBlocks, false, p, time);
                }
            }
        }
    }

    static void genDeadBranches(int length, int stems, Location loc, String otherBlocks, int thiccness, Player p, Long time, float height, int branchdensity) {
        int size = length * stems;
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                if (z != 0 || x != 0) {
                    float div = 1;
                    if (z != 0 && x != 0) {
                        div = 1.4f;
                    }
                    if (ThreadLocalRandom.current().nextInt(1, branchdensity) == 1) {
                        Location l0 = loc.clone().add(0.5, -1.5, 0.5);
                        Location l1 = l0.clone().add((((size / thiccness) * x) + pm()) / div, (size / 8) + pm(), (((size / thiccness) * z) + pm()) / div);
                        Location l2 = l1.clone().add((((size / thiccness) * x) + pm()) / div, ((size / 16) * height) + pm(), (((size / thiccness) * z) + pm()) / div);
                        Utils.setCurve((size * 2), l0, l1, l2, otherBlocks, false, p, time);
                    }
                }
            }
        }
    }

    static void genWillowBranch(int length, int stems, Location loc, String otherBlocks, int thiccness, Player p, Long time, float height, int branchdensity) {
        int size = length * stems;
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                if (z != 0 || x != 0) {
                    float div = 1;
                    if (z != 0 && x != 0) {
                        div = 1.4f;
                    }
                    if (ThreadLocalRandom.current().nextInt(1, branchdensity) == 1) {
                        Location l0 = loc.clone().add(0.5, -1.5, 0.5);
                        Location l1 = l0.clone().add((((size / thiccness) * x) + pm()) / div, (size / 8) + pm(), (((size / thiccness) * z) + pm()) / div);
                        Location l2 = l1.clone().add((((size / thiccness) * x) + pm()) / div, ((size / 16) * height) + pm(), (((size / thiccness) * z) + pm()) / div);
                        Utils.setCurve((size * 2), l0, l1, l2, otherBlocks, false, p, time);
                    }
                }
            }
        }
    }

    static int pm() {
        return ThreadLocalRandom.current().nextInt(-1, 2);
    }
}
