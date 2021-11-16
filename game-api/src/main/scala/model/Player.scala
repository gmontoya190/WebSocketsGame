package model


sealed trait Player {
  def id: String
  def score: String
}

final case class HumanPlayer(idHuman:String, scoreHuman: String) extends Player {
  val id = idHuman
  val score = scoreHuman
}
final case class ComputerPlayer(idComputer:String, scoreComputer: String) extends Player {
  val id = idComputer
  val score = scoreComputer
}
