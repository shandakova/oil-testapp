

object oiltestapp extends PlayModule with SingleModule {

  def scalaVersion = "2.13.11"

  def playVersion = "2.8.20"

  def twirlVersion = "1.5.1"

  object test extends PlayTests
}
