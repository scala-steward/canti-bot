package score.discord.generalbot.util

import net.dv8tion.jda.core.entities.{Guild, Role}
import slick.jdbc.SQLiteProfile.api._
import slick.jdbc.meta.MTable

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class RoleByGuild(database: Database, tableName: String) {

  private class RoleByGuildTable(tag: Tag) extends Table[(Long, Long)](tag, tableName) {
    val guildId = column[Long]("guild", O.PrimaryKey)
    val roleId = column[Long]("role")

    override def * = (guildId, roleId)
  }

  private val roleByGuildTable = TableQuery[RoleByGuildTable]

  Await.result(database.run(MTable.getTables).map(v => {
    val names = v.map(mt => mt.name.name)
    if (!names.contains(tableName)) {
      Await.result(database.run(roleByGuildTable.schema.create), Duration.Inf)
    }
  }), Duration.Inf)

  private val roleByGuild = mutable.HashMap(
    Await.result(database.run(roleByGuildTable.result), Duration.Inf): _*
  )

  def apply(guild: Guild): Option[Role] = apply(guild.getIdLong).flatMap(roleId => Option(guild.getRoleById(roleId)))

  def apply(guild: Long): Option[Long] = roleByGuild.get(guild)

  def update(guild: Guild, role: Role): Unit = update(guild.getIdLong, role.getIdLong)

  def update(guild: Long, role: Long) {
    roleByGuild(guild) = role
    // TODO: Do I need to await this?
    database.run(roleByGuildTable.insertOrUpdate(guild, role))
  }

  def remove(guild: Guild): Unit = remove(guild.getIdLong)

  def remove(guild: Long) {
    roleByGuild.remove(guild)
    // TODO: Do I need to await this?
    database.run(roleByGuildTable.filter(_.guildId === guild).delete)
  }
}
