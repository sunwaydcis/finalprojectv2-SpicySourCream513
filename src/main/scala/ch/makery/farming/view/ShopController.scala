package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Corn, Strawberry, Tomato, Watermelon, Wheat}
import ch.makery.farming.model.items.PlayerState
import javafx.fxml.FXML
import javafx.scene.control.{Alert, ButtonType, Label, TextField}
import javafx.scene.image.ImageView
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml
import java.util.Optional

@sfxml
class ShopController {
  //Wheat
  @FXML private var wheatGrowthtime: Label = _
  @FXML private var wheatCost: Label = _
  @FXML private var wheatQuantity: TextField = _

  //Corn
  @FXML private var cornGrowthtime: Label = _
  @FXML private var cornCost: Label = _
  @FXML private var cornQuantity: TextField = _

  //Carrot
  @FXML private var carrotGrowthtime: Label = _
  @FXML private var carrotCost: Label = _
  @FXML private var carrotQuantity: TextField = _

  //Tomato
  @FXML private var tomatoGrowthtime: Label = _
  @FXML private var tomatoCost: Label = _
  @FXML private var tomatoQuantity: TextField = _

  //Strawberry
  @FXML private var strawberryGrowthtime: Label = _
  @FXML private var strawberryCost: Label = _
  @FXML private var strawberryQuantity: TextField = _

  //Watermelon
  @FXML private var watermelonGrowthtime: Label = _
  @FXML private var watermelonCost: Label = _
  @FXML private var watermelonQuantity: TextField = _

  //Images
  @FXML private var wheatImage: ImageView = _
  @FXML private var cornImage: ImageView = _
  @FXML private var strawberryImage: ImageView = _
  @FXML private var watermelonImage: ImageView = _
  @FXML private var tomatoImage: ImageView = _
  @FXML private var carrotImage: ImageView = _

  private var playerState: PlayerState = _

  // Crops
  private val wheat = new Wheat()
  private val corn = new Corn()
  private val carrot = new Carrot()
  private val tomato = new Tomato()
  private val strawberry = new Strawberry()
  private val watermelon = new Watermelon()

  @FXML
  private def initialize(): Unit = {
    updateCropDetails()
  }

  private def updateCropDetails(): Unit = {
    // Update Wheat details
    wheatGrowthtime.setText(s"Growthtime: ${wheat.getGrowthTime}")
    wheatCost.setText(s"Cost: ${wheat.getCost} coins")

    // Update Corn details
    cornGrowthtime.setText(s"Growthtime: ${corn.getGrowthTime}")
    cornCost.setText(s"Cost: ${corn.getCost} coins")

    // Update Carrot details
    carrotGrowthtime.setText(s"Growthtime: ${carrot.getGrowthTime}")
    carrotCost.setText(s"Cost: ${carrot.getCost} coins")

    // Update Tomato details
    tomatoGrowthtime.setText(s"Growthtime: ${tomato.getGrowthTime}")
    tomatoCost.setText(s"Cost: ${tomato.getCost} coins")

    // Update Strawberry details
    strawberryGrowthtime.setText(s"Growthtime: ${strawberry.getGrowthTime}")
    strawberryCost.setText(s"Cost: ${strawberry.getCost} coins")

    // Update Watermelon details
    watermelonGrowthtime.setText(s"Growthtime: ${watermelon.getGrowthTime}")
    watermelonCost.setText(s"Cost: ${watermelon.getCost} coins")

    // Set images for crops (assuming image paths are correct)
    wheatImage.setImage(new javafx.scene.image.Image("resources/wheat.png"))
    cornImage.setImage(new javafx.scene.image.Image("resources/corn.png"))
    carrotImage.setImage(new javafx.scene.image.Image("resources/carrot.png"))
    tomatoImage.setImage(new javafx.scene.image.Image("resources/tomato.png"))
    strawberryImage.setImage(new javafx.scene.image.Image("resources/strawberry.png"))
    watermelonImage.setImage(new javafx.scene.image.Image("resources/watermelon.png"))
  }

  @FXML
  private def handlePurchase(): Unit = {
    try {
      // Read quantities from text fields
      val wheatQuantityValue = getQuantity(wheatQuantity)
      val cornQuantityValue = getQuantity(cornQuantity)
      val carrotQuantityValue = getQuantity(carrotQuantity)
      val tomatoQuantityValue = getQuantity(tomatoQuantity)
      val strawberryQuantityValue = getQuantity(strawberryQuantity)
      val watermelonQuantityValue = getQuantity(watermelonQuantity)

      // Calculate total cost
      val totalCost = wheatQuantityValue * wheat.getCost +
        cornQuantityValue * corn.getCost +
        carrotQuantityValue * carrot.getCost +
        tomatoQuantityValue * tomato.getCost +
        strawberryQuantityValue * strawberry.getCost +
        watermelonQuantityValue * watermelon.getCost

      // Check if player has enough coins
      if (playerState.coins >= totalCost) {
        // Confirm purchase
        val confirmation = new Alert(AlertType.Confirmation)
        confirmation.setTitle("Confirm Purchase")
        confirmation.setHeaderText("Are you sure you want to make this purchase?")
        confirmation.setContentText(s"Total cost: $totalCost coins")
        val result = confirmation.showAndWait()

        if (result.contains(ButtonType.OK)) {
          // Deduct coins from player
          playerState.coins -= totalCost

          // Deduct seeds from store
          val successfulWheat = wheat.deductSeedsAvailable(wheatQuantityValue)
          val successfulCorn = corn.deductSeedsAvailable(cornQuantityValue)
          val successfulCarrot = carrot.deductSeedsAvailable(carrotQuantityValue)
          val successfulTomato = tomato.deductSeedsAvailable(tomatoQuantityValue)
          val successfulStrawberry = strawberry.deductSeedsAvailable(strawberryQuantityValue)
          val successfulWatermelon = watermelon.deductSeedsAvailable(watermelonQuantityValue)

          if (successfulWheat && successfulCorn && successfulCarrot &&
            successfulTomato && successfulStrawberry && successfulWatermelon) {
            // Purchase successful
            updateCropDetails()  // Refresh details to reflect updated seed quantities
          } else {
            // Restore seeds if purchase failed
            wheat.addSeedsAvailable(wheatQuantityValue)
            corn.addSeedsAvailable(cornQuantityValue)
            carrot.addSeedsAvailable(carrotQuantityValue)
            tomato.addSeedsAvailable(tomatoQuantityValue)
            strawberry.addSeedsAvailable(strawberryQuantityValue)
            watermelon.addSeedsAvailable(watermelonQuantityValue)

            showAlert(AlertType.Error, "Purchase Failed", "Insufficient Seeds", "Not enough seeds available in the store.")

          }
        }
      } else {
        showAlert(AlertType.Error, "Purchase Failed", "Not Enough Coins", "You don't have enough coins to complete this purchase.")

      }
    } catch {
      case e: NumberFormatException =>
        showAlert(AlertType.Error, "Input Error", "Invalid Quantity", "Please enter valid quantities for all crops.")

    }
  }

  private def showAlert(alertType: AlertType, title: String, headerText: String, contentText: String): Unit = {
    val alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(headerText)
    alert.setContentText(contentText)
    alert.showAndWait()
  }

  private def getQuantity(quantityField: TextField): Int = {
    Option(quantityField.getText).filter(_.nonEmpty).map(_.toInt).getOrElse(0)
  }
}