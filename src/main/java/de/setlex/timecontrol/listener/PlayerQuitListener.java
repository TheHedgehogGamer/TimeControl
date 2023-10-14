package de.setlex.timecontrol.listener;

import de.setlex.timecontrol.TimeControl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        TimeControl.getInstance().unregisterPlayer(event.getPlayer());
    }
}
