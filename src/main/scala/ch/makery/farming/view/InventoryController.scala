package ch.makery.farming.view

import ch.makery.farming.model.items.PlayerState
import ch.makery.farming.model.crops._
import javafx.fxml.FXML
import javafx.scene.control.{Alert, ButtonType, Label, TextField}
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml

@sfxml
class InventoryController {

  @FXML private var wheatSeedAvailable: Label = _
  @FXML private var wheatSellPrice: Label = _
  @FXML private var wheatQuantity: TextField = _
  @FXML private var wheatHarvest: Label = _

  @FXML private var cornSeedAvailable: Label = _
  @FXML private var cornSellPrice: Label = _
  @FXML private var cornQuantity: TextField = _
  @FXML private var cornHarvest: Label = _

  @FXML private var carrotSeedAvailable: Label = _
  @FXML private var carrotSellPrice: Label = _
  @FXML private var carrotQuantity: TextField = _
  @FXML private var carrotHarvest: Label = _

  @FXML private var strawberrySeedAvailable: Label = _
  @FXML private var strawberrySellPrice: Label = _
  @FXML private var strawberryQuantity: TextField = _
  @FXML private var strawberryHarvest: Label = _

  @FXML private var watermelonSeedAvailable: Label = _
  @FXML private var watermelonSellPrice: Label = _
  @FXML private var watermelonQuantity: TextField = _
  @FXML private var watermelonHarvest: Label = _

  private var playerState: PlayerState = _

  // Crops
  private val wheat = new Wheat()
  private val corn = new Corn()
  private val carrot = new Carrot()
  private val strawberry = new Strawberry()
  private val watermelon = new Watermelon()

  @FXML
  def initialize(): Unit = {
    updateCropDetails()
  }

  private def updateCropDetails(): Unit = {
    // Fetch details from Crop class and update labels
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
  }

  @FXML
  def handleSellButton(): Unit = {
    try {
      // Validate and fetch quantities from text fields
      val wheatQuantityValue = validateQuantity(wheatQuantity, wheat)
      val cornQuantityValue = validateQuantity(cornQuantity, corn)
      val carrotQuantityValue = validateQuantity(carrotQuantity, carrot)
      val strawberryQuantityValue = validateQuantity(strawberryQuantity, strawberry)
      val watermelonQuantityValue = validateQuantity(watermelonQuantity, watermelon)

      // Calculate total profit
      val totalProfit = wheatQuantityValue * wheat.sellPrice +
        cornQuantityValue * corn.sellPrice +
        carrotQuantityValue * carrot.sellPrice +
        strawberryQuantityValue * strawberry.sellPrice +
        watermelonQuantityValue * watermelon.sellPrice

      // Update player's state and crop details
      playerState.addCoins(totalProfit)
      wheat.deductHarvestedCrops(wheatQuantityValue)
      corn.deductHarvestedCrops(cornQuantityValue)
      carrot.deductHarvestedCrops(carrotQuantityValue)
      strawberry.deductHarvestedCrops(strawberryQuantityValue)
      watermelon.deductHarvestedCrops(watermelonQuantityValue)

      // Show success message
      showAlert(AlertType.Information, "Sale Successful", "Congratulations!", s"Your sale was successful! You earned $totalProfit coins.")
      updateCropDetails() // Refresh the inventory details
    } catch {
      case e: NumberFormatException =>
        showAlert(AlertType.Error, "Input Error", "Invalid Input", "Please enter a valid integer quantity.")
    }

    def validateQuantity(quantityField: TextField, crop: Crop): Int = {
      if (quantityField.getText == null || quantityField.getText.trim.isEmpty) {
        showAlert(AlertType.Warning, "Input Error", "No Input", "Please enter a quantity to sell.")
        throw new NumberFormatException() // Force the catch block to handle this as an error
      }

      try {
        val quantity = quantityField.getText.toInt
        if (quantity > crop.getHarvestedCrops) {
          showAlert(AlertType.Error, "Insufficient Harvest", "Not Enough Harvested Crops", s"You don't have enough harvested crops of ${crop.getName}. Reduce the quantity.")
          throw new NumberFormatException() // Force the catch block to handle this as an error
        }
        quantity
      } catch {
        case _: NumberFormatException =>
          showAlert(AlertType.Error, "Invalid Input", "Invalid Quantity", "Please enter a valid integer quantity.")
          throw new NumberFormatException() // Re-throw to be caught by the outer try-catch
      }
    }

    def showAlert(alertType: AlertType, title: String, headerText: String, contentText: String): Unit = {
      val alert = new Alert(alertType)
      alert.setTitle(title)
      alert.setHeaderText(headerText)
      alert.setContentText(contentText)
      alert.showAndWait()
    }
  }
}
