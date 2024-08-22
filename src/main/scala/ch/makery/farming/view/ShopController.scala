package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Corn, Strawberry, Watermelon, Wheat}
import ch.makery.farming.model.items.PlayerState
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType, Label, TextField}
import scalafx.stage.Stage
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
        // Confirm purchase //show confirm message
        val alert = new Alert(AlertType.Confirmation) {
          title = "Confirm Purchase"
          headerText = "Are you sure you want to make this purchase?"
          contentText = s"Total cost: $$$totalCost coins"
        }
        val result = alert.showAndWait()

        result match {
          case Some(ButtonType.OK) =>
            playerState.coins -= totalCost
            wheat.addSeedsAvailable(wheatQuantityValue)
            corn.addSeedsAvailable(cornQuantityValue)
            carrot.addSeedsAvailable(carrotQuantityValue)
            strawberry.addSeedsAvailable(strawberryQuantityValue)
            watermelon.addSeedsAvailable(watermelonQuantityValue)

            showInformation("Purchase Successful", "Congratulations!", "Your purchase was successful!")

            val stage = wheatQuantity.getScene.getWindow.asInstanceOf[Stage]
            stage.close()

          case _ =>
            val stage = wheatQuantity.getScene.getWindow.asInstanceOf[Stage]
            stage.close()
        }
      }
      showAlert("Purchase Failed", "Insufficient Seeds", "Not enough coins to complete this purchase.")


    } catch {
      case e: NumberFormatException =>
        showAlert("Input Error", "Invalid input", "Please enter valid integer quantities for all crops.")
    }
  }

  def validateQuantity(quantityField: TextField, cropName: String): Int = {
    try {
      val quantityStr = quantityField.text.value.trim
      val quantity = quantityStr.toInt
      if (quantity < 0) {
        throw new NumberFormatException(s"$cropName quantity cannot be negative.")
      }
      quantity

    } catch {
      case _: NumberFormatException =>
        showAlert("Input Error", s"Invalid quantity for $cropName", s"Please enter a valid non-negative integer for $cropName.")

        throw new NumberFormatException(s"Invalid quantity entered for $cropName.")
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

  def showInformation(_title: String, _header: String, _content: String): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = _title
      headerText =  _header
      contentText = _content
    }
    alert.showAndWait()
  }
}