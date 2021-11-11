package service

case class Random(seed:Int) {
  def nextInt:(Int, Random) = {
    val rng = new scala.util.Random(seed)
    val res = rng.nextInt
    val newSeed = Random(seed+1)
    (res, newSeed)
  }
}
