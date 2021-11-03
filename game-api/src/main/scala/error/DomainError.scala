package error

import error.DomainErrorCodes.INVALID_MESSAGE_TYPE

sealed trait DomainError extends Product with Serializable {
  val message: String
  val code: String
}

final case class InvalidMessageType(messageType: String) extends DomainError {
  override val code: String = INVALID_MESSAGE_TYPE
  override val message: String = s"Unknown message type: '$messageType'"
}

final case class NotRecognizedMessageType(messageType: String) extends DomainError {
  override val code: String = INVALID_MESSAGE_TYPE
  override val message: String = s"Unknown message type: '$messageType'"
}

object DomainErrorCodes {
   val INVALID_MESSAGE_TYPE = "invalid"
}