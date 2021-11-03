package main.scala.com.luckygames.domain

sealed trait Player

case class BotPlayer(name: String) extends Player
case class ComputerPlayer(name: String) extends Player
