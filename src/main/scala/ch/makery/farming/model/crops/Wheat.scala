package ch.makery.farming.model.crops

class Wheat(
              _growthTimeInSeconds: Int = 70,
              cost: Double = 9.0,
              sellPrice: Double = 15.0,
              seedsAvailable: Int = 20
            ) extends Crop("Wheat", _growthTimeInSeconds, cost, sellPrice, seedsAvailable, false) {

  override def getDescription: String = {
    super.getDescription + " This is wheat."
  }
}
