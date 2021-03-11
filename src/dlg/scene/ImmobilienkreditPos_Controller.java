//Artur Pfeifer 879089

package dlg.scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Hauptmenue;
import awk.Auftrag;
import awk.Auftragsposition;
import dlg.ControlledScreen;
import dlg.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ImmobilienkreditPos_Controller implements Initializable, ControlledScreen {

	private ScreensController myController;

	@FXML
	TextField tf_nr;
	@FXML
	Label lb_vertragsart;
	@FXML
	TextField tf_kreditbetrag;
	@FXML
	TextField tf_zinsbindung;
	@FXML
	TextField tf_sollzins;
	@FXML
	TextField tf_tilgungsrate;
	@FXML
	TextField tf_grundbuch;
	@FXML
	TextField tf_flurparzelle;

	Auftrag auftrag;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initData() {
		tf_nr.setText("");
		lb_vertragsart.setText("Immobilienkredit");
		tf_kreditbetrag.setText("");
		tf_zinsbindung.setText("");
		tf_sollzins.setText("");
		tf_tilgungsrate.setText("");
		tf_grundbuch.setText("");
		tf_flurparzelle.setText("");

	}

	@FXML
	public void bt_speichernClicked() throws IOException {

		Auftragsposition aPosition = new Auftragsposition();
		if (istInteger(tf_nr, tf_nr.getText()) == true) {
			aPosition.setNr_im_auftrag(Integer.valueOf(tf_nr.getText()));
		} else
			System.out.println("Ungueltige Eingabe");

		aPosition.setVertragsart(lb_vertragsart.getText());

		if (istInteger(tf_kreditbetrag, tf_kreditbetrag.getText()) == true) {
			aPosition.setKreditbetrag(Integer.valueOf(tf_kreditbetrag.getText()));
		} else
			System.out.println("Ungueltige Eingabe");

		aPosition.setZinsbindung(tf_zinsbindung.getText());

		if (istDouble(tf_sollzins, tf_sollzins.getText()) == true) {
			aPosition.setSollzins(Double.valueOf((tf_sollzins.getText())));
		} else
			System.out.println("Ungueltige Eingabe");

		if (istDouble(tf_tilgungsrate, tf_tilgungsrate.getText()) == true) {
			aPosition.setTilgungsrate(Double.valueOf((tf_tilgungsrate.getText())));
		}

		else
			System.out.println("Ungueltige Eingabe");
		aPosition.setGrundbuch(tf_grundbuch.getText());

		if (istInteger(tf_flurparzelle, tf_flurparzelle.getText()) == true) {
			aPosition.setFlurparzelle(Integer.valueOf(tf_flurparzelle.getText()));
		} else
			System.out.println("Ungueltige Eingabe");

		KreditvertragListe_Controller controller = (KreditvertragListe_Controller) myController
				.getController(Hauptmenue.KREDITVERTRAGLISTE_SCENE_SCREEN);
		controller.addPosition(aPosition);
		controller.refreshKreditvertragListeScene();
		this.myController.setScreen(Hauptmenue.KREDITVERTRAGLISTE_SCENE_SCREEN);
	}

	@FXML
	public void bt_zurueckClicked() {
		this.myController.setScreen(Hauptmenue.KREDITVERTRAGLISTE_SCENE_SCREEN);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	private boolean istInteger(TextField input, String message) {
		try {
			int x = Integer.parseInt(input.getText());
			return true;
		} catch (NumberFormatException e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Falsche Eingabe");
			alert.setHeaderText("Bitte geben Sie eine Zahl ein");

			alert.setContentText(message + "ist keine Zahl");

			alert.showAndWait();

			return false;
		}

	}

	private boolean istDouble(TextField input, String message) {
		try {
			double x = Double.parseDouble(input.getText());
			return true;
		} catch (NumberFormatException e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Falsche Eingabe");
			alert.setHeaderText("Bitte geben Sie eine Zahl ein");

			alert.setContentText(message + "ist keine Zahl");

			alert.showAndWait();

			return false;
		}
	}

}
