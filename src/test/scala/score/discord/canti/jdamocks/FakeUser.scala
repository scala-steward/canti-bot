package score.discord.canti.jdamocks

import java.util

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.{Guild, User}
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.CacheRestAction

open class FakeUser(jda: JDA, name: String, id: Long) extends User:

  override def getName: String = name

  override def getDiscriminator: String = "1234"

  override def getAvatarId: String = "123456789"

  override def getDefaultAvatarId: String = ???

  override def hasPrivateChannel: Boolean = ???

  override def openPrivateChannel(): CacheRestAction[PrivateChannel] = ???

  override def getMutualGuilds: util.List[Guild] = ???

  override def isBot: Boolean = ???

  override def getJDA: JDA = jda

  override def getIdLong: Long = id

  override def getAsMention: String = ???

  override def getAsTag: String = ???

  override def getFlags: util.EnumSet[User.UserFlag] = ???

  override def getFlagsRaw: Int = ???

  override def isSystem: Boolean = ???

  override def retrieveProfile(): CacheRestAction[User.Profile] = ???

  override def getGlobalName(): String = ???
