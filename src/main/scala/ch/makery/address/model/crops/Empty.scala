package ch.makery.address.model.crops

class EmptyCrop extends Crop(name = "Empty",
  growthTimeInSeconds = 0,
  health = 0,
  cost = 0,
  sellPrice = 0) {

  override def water(): Unit = {}

  override def grow(): Unit = {}

  override def harvest(): Option[Int] = {
    println("There is no crop to harvest.")
    None
  }

  override def removeCrop(playerCoins: Int): (Boolean, Int) = {
    println("There is no crop to remove.")
    (false, playerCoins)
  }
}
