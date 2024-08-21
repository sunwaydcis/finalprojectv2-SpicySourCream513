package ch.makery.farming.model.crops

class Carrot(
              _growthTimeInSeconds: Int = 30,
              cost: Double = 20.0,
              sellPrice: Double = 28.0,
              seedsAvailable: Int = 0,
              harvestedCrops: Int = 0
            ) extends Crop("Carrot", _growthTimeInSeconds, cost, sellPrice, seedsAvailable, isMature = false, harvestedCrops) {

  override def getDescription: String = {
    super.getDescription + " Carrots are rich in nutrients and can be used in a variety of dishes."
  }
}
