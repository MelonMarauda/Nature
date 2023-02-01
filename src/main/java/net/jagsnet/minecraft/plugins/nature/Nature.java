package net.jagsnet.minecraft.plugins.nature;

import net.jagsnet.minecraft.plugins.nature.commands.NatureMain;
import net.jagsnet.minecraft.plugins.nature.commands.Trees;
import net.jagsnet.minecraft.plugins.nature.commands.Vines;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nature extends JavaPlugin {

    private static Nature instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.getCommand("vines").setExecutor(new Vines());
        this.getCommand("nature").setExecutor(new NatureMain());
        this.getCommand("trees").setExecutor(new Trees());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Nature getInstance() {return instance;}
}
