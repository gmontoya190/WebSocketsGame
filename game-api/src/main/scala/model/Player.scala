package model


sealed trait Player

case class HumanPlayer(score: Long) extends Player
case class ComputerPlayer(score: Long) extends Player
