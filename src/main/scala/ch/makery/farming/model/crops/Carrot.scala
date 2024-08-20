package ch.makery.farming.model.crops

class Carrot(_growthTimeInSeconds: Int = 70,
              cost: Double = 5.0,
              sellPrice: Double = 15.0,
              seedsAvailable: Int = 20
            ) extends Crop("Carrot", _growthTimeInSeconds, cost, sellPrice, seedsAvailable, false) {

  override def getDescription: String = {
    super.getDescription + " Carrot requires rich, sandy soil and moderate water."
  }
}
