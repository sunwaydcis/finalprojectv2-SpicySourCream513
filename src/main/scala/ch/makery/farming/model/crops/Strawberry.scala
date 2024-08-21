package ch.makery.farming.model.crops

class Strawberry(
                  _growthTimeInSeconds: Int = 60,
                  cost: Double =50.0,
                  sellPrice: Double = 70.0,
                  seedsAvailable: Int = 0,
                  harvestedCrops: Int = 0
                ) extends Crop("Strawberry", _growthTimeInSeconds, cost, sellPrice, seedsAvailable, isMature = false, harvestedCrops) {

  override def getDescription: String = {
    super.getDescription + " Strawberries are a sweet fruit often used in desserts."
  }
}
