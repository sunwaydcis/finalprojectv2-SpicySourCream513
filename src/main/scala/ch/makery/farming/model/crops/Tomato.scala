package ch.makery.farming.model.crops

class Tomato(
              _growthTimeInSeconds: Int = 45,
              cost: Double = 35.0,
              sellPrice: Double = 50.0,
              seedsAvailable: Int = 0
            ) extends Crop("Tomato", _growthTimeInSeconds, cost, sellPrice, seedsAvailable) {

  override def getDescription: String = {
    super.getDescription + " Tomatoes are a staple in many cuisines around the world."
  }
}
