package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Corn, Strawberry, Watermelon, Wheat}
import ch.makery.farming.model.items.PlayerState
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label, TextField}
import scalafxml.core.macros.sfxml

@sfxml
class ShopController (
  //Wheat
  private var wheatGrowthtime: Label,
  private var wheatCost: Label,
  private var wheatQuantity: TextField,

  //Corn
  private var cornGrowthtime: Label,
  private var cornCost: Label,
  private var cornQuantity: TextField,

  //Carrot
  private var carrotGrowthtime: Label,
  private var carrotCost: Label,
  private var carrotQuantity: TextField,

  //Strawberry
  private var strawberryGrowthtime: Label,
  private var strawberryCost: Label,
  private var strawberryQuantity: TextField,

  //Watermelon
  private var watermelonGrowthtime: Label,
  private var watermelonCost: Label,
  private var watermelonQuantity: TextField){


  private var playerState: PlayerState = _

  // Crops
  private val wheat = new Wheat()
  private val corn = new Corn()
  private val carrot = new Carrot()
  private val strawberry = new Strawberry()
  private val watermelon = new Watermelon()


    wheatGrowthtime.setText(s"Growthtime: ${wheat.getGrowthTime}")
    cornGrowthtime.setText(s"Growthtime: ${corn.getGrowthTime}")
    carrotGrowthtime.setText(s"Growthtime: ${carrot.getGrowthTime}")
    strawberryGrowthtime.setText(s"Growthtime: ${strawberry.getGrowthTime}")
    watermelonGrowthtime.setText(s"Growthtime: ${watermelon.getGrowthTime}")


  def handleBuyButton(): Unit = {
    try {
      // Read quantities from text fields
      val wheatQuantityValue = validateQuantity(wheatQuantity, "Wheat")
      val cornQuantityValue = validateQuantity(cornQuantity, "Corn")
      val carrotQuantityValue = validateQuantity(carrotQuantity, "Carrot")
      val strawberryQuantityValue = validateQuantity(strawberryQuantity, "Strawberry")
      val watermelonQuantityValue = validateQuantity(watermelonQuantity, "Watermelon")

      // Calculate total cost
      val totalCost = wheatQuantityValue * wheat.getCost +
        cornQuantityValue * corn.getCost +
        carrotQuantityValue * carrot.getCost +
        strawberryQuantityValue * strawberry.getCost +
        watermelonQuantityValue * watermelon.getCost

      // Check if player has enough coins
      if (playerState.coins >= totalCost) {
        // Confirm purchase
        showConfirmation("Confirm Purchase", "Are you sure you want to make this purchase?", s"Total cost: $totalCost coins"): Unit = {

          val confirmation = new Alert(AlertType.Confirmation)
        confirmation.setTitle("Confirm Purchase")
        confirmation.setHeaderText("Are you sure you want to make this purchase?")
        confirmation.setContentText(s"Total cost: $totalCost coins")
        val result = confirmation.showAndWait()

        if (result.isPresent && result.get == ButtonType.OK) {
          playerState.coins -= totalCost

          // Deduct seeds from store
          val successfulWheat = wheat.deductSeedsAvailable(wheatQuantityValue)
          val successfulCorn = corn.deductSeedsAvailable(cornQuantityValue)
          val successfulCarrot = carrot.deductSeedsAvailable(carrotQuantityValue)
          val successfulStrawberry = strawberry.deductSeedsAvailable(strawberryQuantityValue)
          val successfulWatermelon = watermelon.deductSeedsAvailable(watermelonQuantityValue)

          if (successfulWheat && successfulCorn && successfulCarrot &&
            successfulStrawberry && successfulWatermelon) {
            showAlert(AlertType.Information, "Purchase Successful", "Congratulations!", "Your purchase was successful!")
          } else {
            showAlert(AlertType.Error, "Purchase Failed", "Insufficient Seeds", "Not enough seeds available in the store.")
          }
        } else {
          initialize() // User canceled the purchase, reinitialize the shop
        }
      } else {
        showAlert(AlertType.Error, "Purchase Failed", "Not Enough Coins", "You don't have enough coins to complete this purchase.")
      }
    } catch {
      case e: NumberFormatException =>
        showAlert(AlertType.Error, "Input Error", "Invalid input", "Please enter valid integer quantities for all crops.")
    }
  }

  def validateQuantity(quantityField: TextField, cropName: String): Int = {
    if (quantityField.getText == null || quantityField.getText.trim.isEmpty) {
      return 0
    }
    try {
      quantityField.getText.toInt
    } catch {
      case _: NumberFormatException =>
        showAlert(AlertType.Error, "Invalid Input", s"Invalid input for $cropName", "Only integer values are allowed.")
        throw new NumberFormatException() // Re-throw to be caught by the outer try-catch
    }
  }

  def showAlert(_title: String, _header: String, _content: String): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = _title
      headerText =  _header
      contentText = _content
    }
    alert.showAndWait()
  }

  def showConfirmation(_title: String, _header: String, _content: String): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      title = _title
      headerText =  _header
      contentText = _content
    }
    alert.showAndWait()
  }
}