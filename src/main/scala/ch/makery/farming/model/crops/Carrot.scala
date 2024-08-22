package ch.makery.farming.model.crops

class Carrot( _name: String = "Carrot",
             _growthTimeInSeconds: Int = 30,
             _cost: Double = 20.0,
             _sellPrice: Double = 28.0,
             _seedsAvailable: Int = 2,
             _isMature: Boolean = false,
             _harvestedCrops: Int = 0
           ) extends Crop(_name, _growthTimeInSeconds, _cost, _sellPrice, _seedsAvailable, _isMature, _harvestedCrops) {

  override def getDescription: String = {
    val profit = _sellPrice - _cost
    s" Carrots are rich in nutrients and can be used in a variety of dishes. " +
      s"Growth Time: ${_growthTimeInSeconds} seconds, Cost: $$${_cost}, " +
      s"Sell Price: $$${_sellPrice}, Profit: $$${profit}."
  }
}
