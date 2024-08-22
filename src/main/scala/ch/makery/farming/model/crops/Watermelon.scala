package ch.makery.farming.model.crops

class Watermelon( _name: String = "Watermelon",
                  _growthTimeInSeconds: Int = 75,
                  _cost: Double = 60.0,
                  _sellPrice: Double = 85.0,
                  _seedsAvailable: Int = 0,
                  _isMature: Boolean = false,
                  _harvestedCrops: Int = 0
                ) extends Crop(_name, _growthTimeInSeconds, _cost, _sellPrice, _seedsAvailable, _isMature, _harvestedCrops) {

  override def getDescription: String = {
    val profit = _sellPrice - _cost
    s" Watermelons are large, refreshing fruits perfect for hot days.\n" +
      s"Growth Time: ${_growthTimeInSeconds} seconds\nCost: $$${_cost}\nSell Price: $$${_sellPrice}, Profit: $$${profit}."
  }
}
