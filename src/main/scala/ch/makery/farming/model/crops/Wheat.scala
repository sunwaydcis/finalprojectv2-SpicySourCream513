package ch.makery.farming.model.crops

class Wheat(
             _growthTimeInSeconds: Int = 5,
             cost: Double = 5.0,
             sellPrice: Double = 8.0,
             seedsAvailable: Int = 10,
             harvestedCrops: Int = 0

           ) extends Crop("Wheat", _growthTimeInSeconds, cost, sellPrice, seedsAvailable, isMature = false, harvestedCrops) {

  override def getDescription: String = {
    super.getDescription + " Wheat is a staple crop used to produce bread and other products."
  }
}
