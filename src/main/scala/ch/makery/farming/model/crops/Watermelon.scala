package ch.makery.farming.model.crops

class Watermelon(
                  _growthTimeInSeconds: Int = 75,
                  cost: Double =60.0,
                  sellPrice: Double = 85.0,
                  seedsAvailable: Int = 0
                ) extends Crop("Watermelon", _growthTimeInSeconds, cost, sellPrice, seedsAvailable) {

  override def getDescription: String = {
    super.getDescription + " Watermelons are large, refreshing fruits perfect for hot days."
  }
}
