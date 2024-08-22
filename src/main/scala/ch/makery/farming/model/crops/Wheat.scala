package ch.makery.farming.model.crops

class Wheat( _name: String = "Wheat",
             _growthTimeInSeconds: Int = 5,
             _cost: Double = 5.0,
             _sellPrice: Double = 8.0,
             _seedsAvailable: Int = 10,
             _isMature: Boolean = false,
             _harvestedCrops: Int = 0
           ) extends Crop(_name, _growthTimeInSeconds, _cost, _sellPrice, _seedsAvailable, _isMature, _harvestedCrops) {

  override def getDescription: String = {
    val profit = _sellPrice - _cost
    " Wheat is a staple crop used to produce bread and other products.\n" +
      s"Growth Time: ${_growthTimeInSeconds} seconds\nCost: $$${_cost}\nSell Price: $$${_sellPrice}, Profit: $$${profit}."
  }
}
