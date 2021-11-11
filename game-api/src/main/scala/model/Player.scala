package model


sealed trait Player

final case class HumanPlayer(id:String, score: String) extends Player
final case class ComputerPlayer(id:String, score: String) extends Player
