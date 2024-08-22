package ch.makery.farming.model.crops

class Corn( _name: String = "Corn",
            _growthTimeInSeconds: Int = 12,
            _cost: Double = 10.0,
            _sellPrice: Double = 15.0,
            _seedsAvailable: Int = 5,
            _isMature: Boolean = false,
            _harvestedCrops: Int = 0
          ) extends Crop(_name, _growthTimeInSeconds, _cost, _sellPrice, _seedsAvailable, _isMature, _harvestedCrops) {

  override def getDescription: String = {
    val profit = _sellPrice - _cost
    s" Corn is a versatile crop that grows tall and is used in many foods. \n" +
      s"Growth Time: ${_growthTimeInSeconds} seconds\nCost: $$${_cost}\nSell Price: $$${_sellPrice}, Profit: $$${profit}."
  }
}
