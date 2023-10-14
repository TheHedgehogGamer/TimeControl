package de.setlex.timecontrol;

import net.minecraft.network.protocol.game.PacketPlayOutUpdateTime;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TimeManager {

    public static void managePlayer(Player player, Mode mode, PlayerConnection playerConnection) {

        if (mode.getType() == Mode.Type.DEFAULT) {
            World world = player.getWorld();

            playerConnection.a(new PacketPlayOutUpdateTime(world.getFullTime(), world.getTime(), false));
            return;
        }

        if (mode.getType() == Mode.Type.FROZEN) {
            playerConnection.a(new PacketPlayOutUpdateTime(mode.getWorldAge(), -mode.getTimeOfDay(), false));
            return;
        }

        if (mode.getType() == Mode.Type.CUSTOM) {
            Mode.Time time = mode.getFunction().apply(player);
            playerConnection.a(new PacketPlayOutUpdateTime(time.getWorldAge(), time.getTimeOfDay(), false));
            return;
        }

    }

}
