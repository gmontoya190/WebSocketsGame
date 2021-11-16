package service

class Random(maxNumber: Int) {

  def nextInt: Int = {
    val rng = new scala.util.Random
    val res = rng.nextInt(maxNumber)
    res
  }
}

