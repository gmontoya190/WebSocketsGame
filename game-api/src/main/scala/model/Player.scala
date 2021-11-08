package model


sealed trait Player

case class HumanPlayer(id:String, score: String) extends Player
case class ComputerPlayer(id:String, score: String) extends Player
