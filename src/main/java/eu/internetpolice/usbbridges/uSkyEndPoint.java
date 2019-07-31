package eu.internetpolice.usbbridges;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.EndPoint;
import com.ensifera.animosity.craftirc.RelayedMessage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class uSkyEndPoint implements EndPoint {
    private CraftIRC craftIrc;
    private String tag;

    uSkyEndPoint(@NotNull CraftIRC craftIrc, @NotNull String tag) {
        this.craftIrc = craftIrc;
        this.tag = tag;
    }

    boolean register() {
        return craftIrc.registerEndPoint(tag, this);
    }

    boolean unregister() {
        return craftIrc.unregisterEndPoint(tag);
    }

    @Override
    public Type getType() {
        return Type.MINECRAFT;
    }

    @Override
    public void messageIn(RelayedMessage message) {
        // Ignore messages from IRC to Minecraft, we only care about relaying ingame chat to IRC.
    }

    @Override
    public boolean userMessageIn(String username, RelayedMessage message) {
        return false;
    }

    @Override
    public boolean adminMessageIn(RelayedMessage message) {
        return false;
    }

    @Override
    public List<String> listUsers() {
        return null;
    }

    @Override
    public List<String> listDisplayUsers() {
        return null;
    }
}
