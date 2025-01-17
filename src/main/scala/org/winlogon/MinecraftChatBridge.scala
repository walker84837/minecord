package org.winlogon

import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.event.player.{AsyncPlayerChatEvent, PlayerJoinEvent}

class MinecraftChatBridge(config: Configuration, discordBotManager: DiscordBotManager) extends Listener {

  @EventHandler
  def onPlayerChat(event: AsyncPlayerChatEvent): Unit = {
    // TODO: remove minimessage syntax when sending to discord
    val playerMessage = "&[a-zA-Z0-9]".r replaceAllIn (event.getMessage, "")
    val message = config.minecraftMessage
      .replace("$user_name", event.getPlayer.getName)
      .replace("$message", playerMessage.trim)
    discordBotManager.sendMessageToDiscord(message)
  }

  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit = {
      if (config.sendPlayerJoinMessages) {
        val player = event.getPlayer()
        discordBotManager.sendMessageToDiscord(s"**${player.getName}** has joined the server!")
      }
    }
}

