package ch.makery.farming.view

import ch.makery.farming.model.items.PlayerState
import ch.makery.farming.model.crops._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType, Label, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class InventoryController(
  private var wheatSeedAvailable: Label,
  private var wheatSellPrice: Label,
  private var wheatQuantity: TextField,
  private var wheatHarvest: Label,

  private var cornSeedAvailable: Label,
  private var cornSellPrice: Label,
  private var cornQuantity: TextField,
  private var cornHarvest: Label,

  private var carrotSeedAvailable: Label,
  private var carrotSellPrice: Label,
  private var carrotQuantity: TextField,
  private var carrotHarvest: Label,

  private var strawberrySeedAvailable: Label,
  private var strawberrySellPrice: Label,
  private var strawberryQuantity: TextField,
  private var strawberryHarvest: Label,

  private var watermelonSeedAvailable: Label,
  private var watermelonSellPrice: Label,
  private var watermelonQuantity: TextField,
  private var watermelonHarvest: Label) {

  private var playerState: PlayerState = _

  // Crops
  private val wheat = new Wheat()
  private val corn = new Corn()
  private val carrot = new Carrot()
  private val strawberry = new Strawberry()
  private val watermelon = new Watermelon()

   wheatSeedAvailable.setText(s"Seed: ${wheat.getAvailableSeeds}")
    wheatSellPrice.setText(s"Sell Price: ${wheat.getSellPrice} coins")
    wheatHarvest.setText(s"Harvest: ${wheat.getHarvestedCrops}")

    cornSeedAvailable.setText(s"Seed: ${corn.getAvailableSeeds}")
    cornSellPrice.setText(s"Sell Price: ${corn.getSellPrice} coins")
    cornHarvest.setText(s"Harvest: ${corn.getHarvestedCrops}")

    carrotSeedAvailable.setText(s"Seed: ${carrot.getAvailableSeeds}")
    carrotSellPrice.setText(s"Sell Price: ${carrot.getSellPrice} coins")
    carrotHarvest.setText(s"Harvest: ${carrot.getHarvestedCrops}")

    strawberrySeedAvailable.setText(s"Seed: ${strawberry.getAvailableSeeds}")
    strawberrySellPrice.setText(s"Sell Price: ${strawberry.getSellPrice} coins")
    strawberryHarvest.setText(s"Harvest: ${strawberry.getHarvestedCrops}")

    watermelonSeedAvailable.setText(s"Seed: ${watermelon.getAvailableSeeds}")
    watermelonSellPrice.setText(s"Sell Price: ${watermelon.getSellPrice} coins")
    watermelonHarvest.setText(s"Harvest: ${watermelon.getHarvestedCrops}")

  def handleSellButton(): Unit = {
    try {
      val wheatQuantityValue = validateQuantity(wheatQuantity, "Wheat")
      val cornQuantityValue = validateQuantity(cornQuantity, "Corn")
      val carrotQuantityValue = validateQuantity(carrotQuantity, "Carrot")
      val strawberryQuantityValue = validateQuantity(strawberryQuantity, "Strawberry")
      val watermelonQuantityValue = validateQuantity(watermelonQuantity, "Watermelon")

      // Calculate total profit
      val totalProfit = wheatQuantityValue * wheat.sellPrice +
        cornQuantityValue * corn.sellPrice +
        carrotQuantityValue * carrot.sellPrice +
        strawberryQuantityValue * strawberry.sellPrice +
        watermelonQuantityValue * watermelon.sellPrice

      val totalQuantityValue = wheatQuantityValue + cornQuantityValue + carrotQuantityValue + strawberryQuantityValue + watermelonQuantityValue

      // Check if player has enough coins
      if (playerState.totalHarvestedPlants >= totalQuantityValue) {
        // Confirm selling //show confirm message
        val alert = new Alert(AlertType.Confirmation) {
          title = "Confirm Sell"
          headerText = "Are you sure you want to sell the harvested plant?"
          contentText = s"Total profit: $$$totalProfit coins"
        }
        val result = alert.showAndWait()

        result match {
          case Some(ButtonType.OK) =>
            playerState.addCoins(totalProfit)
            wheat.deductHarvestedCrops(wheatQuantityValue)
            corn.deductHarvestedCrops(cornQuantityValue)
            carrot.deductHarvestedCrops(carrotQuantityValue)
            strawberry.deductHarvestedCrops(strawberryQuantityValue)
            watermelon.deductHarvestedCrops(watermelonQuantityValue)

            showInformation("Sell Successful", "Congratulations!", "Your Selling was successful! You Earn the coins!")

            val stage = wheatQuantity.getScene.getWindow.asInstanceOf[Stage]
            stage.close()

          case _ =>
            val stage = wheatQuantity.getScene.getWindow.asInstanceOf[Stage]
            stage.close()
        }
      }
      showAlert("Sell Failed", "Insufficient Harvested Crop", "Not enough Harvested Crop to complete the sell.")


    } catch {
      case e: NumberFormatException =>
        showAlert("Input Error", "Invalid input", "Please enter valid integer quantities for all crops.")
    }
  }


  def validateQuantity(quantityField: TextField, cropName: String): Int = {
    try {
      val quantityStr = quantityField.text.value.trim

      if (quantityStr.isEmpty) {
        throw new NumberFormatException(s"No quantity is entered")
      }
      val quantity = quantityStr.toInt
      if (quantity < 0) {
        throw new NumberFormatException("Quantity cannot be negative.")
      }

      quantity

    } catch {
      case _: NumberFormatException =>
        showAlert("Input Error", s"Invalid quantity for $cropName", s"Please enter a valid non-negative integer in the $cropName quantity field.")
        throw new NumberFormatException(s"Invalid quantity entered for $cropName.")
    }
  }

  def showAlert(_title: String, _header: String, _content: String): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = _title
      headerText = _header
      contentText = _content
    }
    alert.showAndWait()
  }

  def showInformation(_title: String, _header: String, _content: String): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = _title
      headerText = _header
      contentText = _content
    }
    alert.showAndWait()
  }
}
