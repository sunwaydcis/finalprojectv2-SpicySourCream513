package ch.makery.farming.model.crops

class Corn(
            _growthTimeInSeconds: Int = 12,
            cost: Double = 10.0,
            sellPrice: Double = 15.0,
            seedsAvailable: Int = 5,
            harvestedCrops: Int = 0
          ) extends Crop("Corn", _growthTimeInSeconds, cost, sellPrice, seedsAvailable, isMature = false, harvestedCrops) {

  override def getDescription: String = {
    super.getDescription + " Corn is a versatile crop that grows tall and is used in many foods."
  }
}
