package spotification2.auth

import spotification2.common.NonBlankString

import CallbackErrorRequest.*

final case class CallbackErrorRequest(query: Query)

object CallbackErrorRequest:
  final case class Query(error: NonBlankString, state: Option[NonBlankString])

  def make(error: NonBlankString, state: Option[NonBlankString]): CallbackErrorRequest =
    CallbackErrorRequest(Query(error, state))
