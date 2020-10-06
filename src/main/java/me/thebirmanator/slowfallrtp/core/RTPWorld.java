package me.thebirmanator.slowfallrtp.core;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class RTPWorld {

    private World world;
    private int maxHeight;
    private int tpMin;
    private int tpMax;
    private int tpExcludeMin;
    private int tpExcludeMax;

    private static List<RTPWorld> allRTPWorlds = new ArrayList<RTPWorld>();
    private static List<Material> blacklistedMaterials = new ArrayList<Material>();
    private static int cooldown = 0;

    public RTPWorld(World world, int maxHeight, int tpMin, int tpMax, int tpExcludeMin, int tpExcludeMax) {

        this.world = world;
        this.maxHeight = maxHeight;
        this.tpMin = tpMin;
        this.tpMax = tpMax;
        this.tpExcludeMin = tpExcludeMin;
        this.tpExcludeMax = tpExcludeMax;
    }

    public World getBukkitWorld() {
        return world;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getTpMin() {
        return tpMin;
    }

    public int getTpMax() {
        return tpMax;
    }

    public int getTpExcludedMin() {
        return tpExcludeMin;
    }

    public int getTpExcludedMax() {
        return tpExcludeMax;
    }

    public static List<RTPWorld> getRTPWorlds() {
        return allRTPWorlds;
    }

    public static List<Material> getBlacklistedMaterials() {
        return blacklistedMaterials;
    }

    public static int getCooldown() {
        return cooldown;
    }

    public static void setCooldown(int newCooldown) {
        cooldown = newCooldown;
    }

    public void addRTPWorld() {
        allRTPWorlds.add(this);
    }

    public static RTPWorld getRTPWorld(World world) {
        for (RTPWorld rtpWorld : allRTPWorlds) {
            if (rtpWorld.getBukkitWorld().equals(world)) {
                return rtpWorld;
            }
        }

        return null;
    }

}
