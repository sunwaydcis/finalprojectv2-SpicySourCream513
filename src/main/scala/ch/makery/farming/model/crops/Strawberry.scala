package ch.makery.farming.model.crops

class Strawberry( _name: String = "Strawberry",
                  _growthTimeInSeconds: Int = 60,
                  _cost: Double = 50.0,
                  _sellPrice: Double = 70.0,
                  _seedsAvailable: Int = 0,
                  _isMature: Boolean = false,
                  _harvestedCrops: Int = 0
                ) extends Crop(_name, _growthTimeInSeconds, _cost, _sellPrice, _seedsAvailable, _isMature, _harvestedCrops) {

  override def getDescription: String = {
    val profit = _sellPrice - _cost
    s" Strawberries are a sweet fruit often used in desserts." +
      s"Growth Time: ${_growthTimeInSeconds} seconds, Cost: $$${_cost}, " +
      s"Sell Price: $$${_sellPrice}, Profit: $$${profit}."
  }
}
