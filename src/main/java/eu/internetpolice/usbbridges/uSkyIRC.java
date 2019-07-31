package eu.internetpolice.usbbridges;

import com.ensifera.animosity.craftirc.CraftIRC;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class uSkyIRC extends JavaPlugin {
    private static final String TAG_ISLAND_CHAT = "usb-islandchat";
    private static final String TAG_PARTY_CHAT = "usb-partychat";

    private uSkyEndPoint islandEndPoint;
    private uSkyEndPoint partyEndPoint;

    private CraftIRC craftIrc;

    @Override
    public void onEnable() {
        try {
            if (!getServer().getPluginManager().isPluginEnabled("CraftIRC")) {
                throw new RuntimeException("CraftIRC is not enabled!");
            }
            this.craftIrc = (CraftIRC) getServer().getPluginManager().getPlugin("CraftIRC");

            //noinspection ConstantConditions
            if (craftIrc.cAutoPaths()) {
                throw new RuntimeException("Autopaths are enabled in the CraftIRC config. Please disable Authpaths to" +
                        "make uSkyIRC work.");
            }

            registerEndPoints();

            getServer().getPluginManager().registerEvents(new uSkyListener(this, craftIrc), this);
            getLogger().info("Listener registered -- ready to talk to IRC");
        } catch (RuntimeException ex) {
            getLogger().warning(ex.getMessage());
            getLogger().warning("Failed to load uSkyIRC. Disabling...");
        }
    }

    @Override
    public void onDisable() {
        unregisterEndPoints();
    }

    private void registerEndPoints() {
        islandEndPoint = new uSkyEndPoint(craftIrc, TAG_ISLAND_CHAT);
        if (!islandEndPoint.register()) {
            throw new RuntimeException("Failed to register IslandChat to CraftIRC");
        }

        partyEndPoint = new uSkyEndPoint(craftIrc, TAG_PARTY_CHAT);
        if (!partyEndPoint.register()) {
            throw new RuntimeException("Failed to register PartyChat to CraftIRC");
        }
        getLogger().info("Hooked into CraftIRC");
    }

    private void unregisterEndPoints() {
        if (islandEndPoint != null) {
            islandEndPoint.unregister();
        }

        if (partyEndPoint != null) {
            partyEndPoint.unregister();
        }
    }

    @Nullable
    uSkyEndPoint getIslandEndPoint() {
        return islandEndPoint;
    }

    @Nullable
    uSkyEndPoint getPartyEndPoint() {
        return partyEndPoint;
    }
}
