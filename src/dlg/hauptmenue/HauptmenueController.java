//Artur Pfeifer 879089

package dlg.hauptmenue;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Hauptmenue;

import dlg.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HauptmenueController implements Initializable, dlg.ControlledScreen {

	@FXML
	Button bt_kundenliste;
	@FXML
	Button bt_scene2;
	@FXML
	Button bt_scene3;
	@FXML
	Button bt_kreditvertraege;
	@FXML
	Button bt_beenden;

	private double oldWidth;
	private double oldHeight;
	private ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void bt_kundenlisteClicked() {
		this.oldHeight = bt_kundenliste.getScene().getWindow().getHeight();
		this.oldWidth = bt_kundenliste.getScene().getWindow().getWidth();
		bt_kundenliste.getScene().getWindow().setWidth(800);
		bt_kundenliste.getScene().getWindow().setHeight(500);
		
		
		this.myController.setScreen(Hauptmenue.KUNDENLISTE_SCENE_SCREEN);
	}

	@FXML
	public void bt_scene2Clicked() {
		this.myController.setScreen(Hauptmenue.SCENE2_SCREEN);
	}

	@FXML
	public void bt_kreditvertraegeClicked() {
		this.oldWidth = bt_kreditvertraege.getScene().getWindow().getWidth();
		this.oldHeight = bt_kreditvertraege.getScene().getWindow().getHeight();
		
		bt_kreditvertraege.getScene().getWindow().setWidth(1000);
		bt_kreditvertraege.getScene().getWindow().setHeight(500);
		this.myController.setScreen(Hauptmenue.KREDITVERTRAGLISTE_SCENE_SCREEN);
	}

	@FXML
	public void bt_beendenClicked() {
		Stage mainStage = (Stage) bt_beenden.getScene().getWindow();
		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
		Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
		exitButton.setText("Exit");
		closeConfirmation.setHeaderText("Confirm Exit");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
		closeConfirmation.initOwner(mainStage);

		closeConfirmation.setX(mainStage.getX() + 150);
		closeConfirmation.setY(mainStage.getY() - 300 + mainStage.getHeight());

		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();

		if (ButtonType.OK.equals(closeResponse.get())) {
			System.exit(0);
		}

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
