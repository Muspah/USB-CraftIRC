package eu.internetpolice.usbbridges;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.RelayedMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import us.talabrek.ultimateskyblock.api.event.IslandChatEvent;

public class uSkyListener implements Listener {
    private uSkyIRC plugin;
    private CraftIRC craftIrc;

    uSkyListener(@NotNull uSkyIRC plugin, @NotNull CraftIRC craftIrc) {
        this.plugin = plugin;
        this.craftIrc = craftIrc;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    @SuppressWarnings("unused")
    public void onIslandChatEvent(IslandChatEvent event) {
        uSkyEndPoint endPoint = null;
        if (event.getType() == IslandChatEvent.Type.ISLAND) {
            endPoint = plugin.getIslandEndPoint();
        } else if (event.getType() == IslandChatEvent.Type.PARTY) {
            endPoint = plugin.getPartyEndPoint();
        }

        if (endPoint == null) {
            return;
        }

        RelayedMessage message = craftIrc.newMsg(endPoint, null, "chat");
        if (message == null) {
            return;
        }

        Player player = event.getPlayer();
        message.setField("sender", player.getDisplayName());
        message.setField("message", event.getMessage());
        message.setField("world", player.getWorld().getName());
        message.setField("realSender", player.getName());
        message.setField("prefix", craftIrc.getPrefix(player));
        message.setField("suffix", craftIrc.getSuffix(player));
        message.post();
    }
}
