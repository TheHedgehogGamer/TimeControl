package de.setlex.timecontrol;

import de.setlex.timecontrol.listener.PlayerJoinListener;
import de.setlex.timecontrol.listener.PlayerQuitListener;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class TimeControl extends JavaPlugin {

    private static TimeControl instance;

    private Map<UUID, Mode> onlinePlayer;
    private Mode defaultMode = Mode.DEFAULT();

    @Override
    public void onLoad() {

        instance = this;

        // registering events
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);

    }

    @Override
    public void onEnable() {
        // If the server restarts, all online players will be added automatically
        onlinePlayer = new HashMap<UUID, Mode>() {};
        Bukkit.getOnlinePlayers().forEach(player -> onlinePlayer.put(player.getUniqueId(), Mode.DEFAULT()));

        new BukkitRunnable() {
            @Override
            public void run() {
                onTick();
            }
        }.runTaskTimer(this, 0, 1);
    }

    private void onTick() {
        for (int i = 0; i < onlinePlayer.size(); i++) {
            UUID uuid = (UUID) onlinePlayer.keySet().toArray()[i];
            Mode mode = (Mode) onlinePlayer.values().toArray()[i];
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || player.getAddress() == null) continue;

            PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().c;

            TimeManager.managePlayer(player, mode, playerConnection);
        }
    }

    /**
     * @param player The target player
     * @param mode The mode you want to assign to the player
     */
    public void setPlayerMode(Player player, Mode mode) {
        onlinePlayer.put(player.getUniqueId(), mode);
    }

    /**
     * @param mode The mode you want to assign to the online players
     */
    public void setAllOnlinePlayersMode(Mode mode) {
        Bukkit.getOnlinePlayers().forEach(player -> onlinePlayer.put(player.getUniqueId(), mode));
    }

    /**
     * @param mode The mode you want to assign to every player
     */
    public void setDefaultMode(Mode mode) {
        defaultMode = mode;
    }

    /**
     * @param player The target player
     * @return The mode that is assign to the player
     */
    public Mode getPlayerMode(Player player) {
        return onlinePlayer.get(player.getUniqueId());
    }

    /**
     * @param uuid The UUID of the target player
     * @param mode The mode you want to assign to the player
     */
    public void setPlayerMode(UUID uuid, Mode mode) {
        onlinePlayer.put(uuid, mode);
    }

    /**
     * @param uuid The UUID of the target player
     * @return The mode that is assign to the player
     */
    public Mode getPlayerMode(UUID uuid) {
        return onlinePlayer.get(uuid);
    }

    /**
     * @return the instance of this class
     */
    public static TimeControl getInstance() {
        return instance;
    }

    /**
     * @param player The player to register
     */
    public void registerPlayer(Player player) {
        onlinePlayer.put(player.getUniqueId(), defaultMode);
    }

    /**
     * @param player The player to unregister
     */
    public void unregisterPlayer(Player player) {
        onlinePlayer.remove(player.getUniqueId());
    }

    /**
     * @param uuid The UUID of the player to register
     */
    public void registerPlayer(UUID uuid) {
        onlinePlayer.put(uuid, defaultMode);
    }

    /**
     * @param uuid The UUID of the player to unregister
     */
    public void unregisterPlayer(UUID uuid) {
        onlinePlayer.remove(uuid);
    }
}
