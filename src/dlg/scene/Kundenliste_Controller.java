//Artur Pfeifer 879089

package dlg.scene;

import java.net.URL;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Hauptmenue;
import awk.Auftrag;
import awk.Kunde;
import dlg.ControlledScreen;
import dlg.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Kundenliste_Controller implements Initializable, ControlledScreen {

	@FXML
	AnchorPane myAnchorPane;

	@FXML
	Button bt_aktualisieren;
	@FXML
	Button bt_zurueck;
	@FXML
	Button bt_kundeEntfernen;
	@FXML
	Button bt_kundendatenAendern;
	@FXML
	Button bt_kreditvertraegeAnzeigen;
	@FXML
	TableView<Kunde> tv_kunde;
	@FXML
	TableColumn<Kunde, Integer> tc_KundenNr;
	@FXML
	TableColumn<Kunde, String> tc_KundenName;
	@FXML
	TableColumn<Kunde, String> tc_KundenVorname;
	@FXML
	TableColumn<Kunde, String> tc_KundenGebDatum;

	@FXML
	TextField tf_sucheKunde;
	@FXML
	TextField tf_nameNeu;
	@FXML
	TextField tf_vornameNeu;
	@FXML
	TextField tf_gebDatumNeu;

	private ScreensController myController;

	ObservableList<Kunde> kundenliste = FXCollections.observableArrayList();

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;

	}

	@FXML
	public void bt_kreditvertraegeAnzeigenClicked() {

		Kunde aKunde1 = tv_kunde.getSelectionModel().getSelectedItem();

		Collection<Auftrag> auftraege = myController.getKreditvertragManager()
				.ladeAuftraegeVonKunde(aKunde1.getKunde_nr());

		System.out.println(auftraege);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Kreditvertrag Anzeige");
		alert.setHeaderText(
				"Zum Kunden mit der Kundennummer: " + aKunde1.getKunde_nr() + " gehören folgende Positionen: ");

		alert.setContentText(String.valueOf(auftraege.toString()));

		alert.showAndWait();

	}

	@FXML
	public void bt_kundendatenAendernClicked() {

		myController.getKundenManager().ladeKunde(Integer.parseInt(tf_sucheKunde.getText()));
		String name = tf_nameNeu.getText();
		String vorname = tf_vornameNeu.getText();
		String gebDatum = tf_gebDatumNeu.getText();

		String kundenSuche = tf_sucheKunde.getText();
		Kunde aKunde = new Kunde(name, vorname, gebDatum, kundenSuche);
		myController.getKundenManager().kundenDatenAendern(aKunde, Integer.parseInt(tf_sucheKunde.getText()));

	}

	@FXML
	public void bt_kundeEntfernenClicked(ActionEvent event) {

		Stage stage = (Stage) myAnchorPane.getScene().getWindow();

		Alert.AlertType type = Alert.AlertType.CONFIRMATION;
		Alert alert = new Alert(type, ""); // --> object of alert class
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(stage);

		alert.getDialogPane().setContentText("Soll der Kunde unwiederruflich gelöscht werden?");
		alert.getDialogPane().setHeaderText("Kunden löschen");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {

			Kunde aKunde1 = tv_kunde.getSelectionModel().getSelectedItem();
			System.out.println("Datensatz von Kunde: " + aKunde1.getKunde_nr() + "wird gelöscht");

			Collection<Auftrag> auftraege = myController.getKreditvertragManager()
					.ladeAuftraegeVonKunde(aKunde1.getKunde_nr());

			for (Auftrag aAuftrag : auftraege) {
				myController.getKreditvertragManager().deleteAuftragspositionen(aAuftrag.getAuftrag_nr()); // -->
																											// integrity
																											// constraint
				myController.getKreditvertragManager().deleteAuftrag(aAuftrag);
			}
			// -->>>> erst auftraege löschen und dann kunden

			Kunde aKunde = tv_kunde.getSelectionModel().getSelectedItem();
			System.out.println(aKunde.getKunde_nr());

			myController.getKundenManager().deleteKunde(aKunde.getKunde_nr());

		} else if (result.get() == ButtonType.CANCEL) {
			System.out.println("Vorgang abgebrochen");

		}

	}

	@FXML
	public void bt_AktualisierenClicked() {
		kundenliste.clear();
		Collection<Kunde> data = myController.getKundenManager().ladeAlleKunden(); // an diese Collec werden Kunden aus

		for (Kunde kunde : data) {
			kundenliste.add(kunde);

		}
		tv_kunde.setItems(kundenliste);

	}

	@FXML
	public void bt_zurueckClicked() {
		this.myController.setScreen(Hauptmenue.MAIN_SCREEN);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tc_KundenNr.setCellValueFactory(new PropertyValueFactory<Kunde, Integer>("kunde_nr"));
		tc_KundenName.setCellValueFactory(new PropertyValueFactory<Kunde, String>("name"));
		tc_KundenVorname.setCellValueFactory(new PropertyValueFactory<Kunde, String>("vorname"));
		tc_KundenGebDatum.setCellValueFactory(new PropertyValueFactory<Kunde, String>("gebDatum"));

	}

	@Override
	public void initData() {
		this.bt_AktualisierenClicked();
	}
}
