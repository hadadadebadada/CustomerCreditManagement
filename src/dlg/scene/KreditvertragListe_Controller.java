//Artur Pfeifer 879089

package dlg.scene;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import application.Hauptmenue;
import awk.Auftrag;
import awk.Auftragsposition;
import awk.Kunde;
import dlg.ControlledScreen;
import dlg.ScreensController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KreditvertragListe_Controller implements Initializable, ControlledScreen {

	// KUNDENMÜSSEN BEI BUTTONCLICK ZUERST GELADEN WERDEN
	private double oldWidth;
	private double oldHeight;

	private ScreensController myController;

	private Auftrag auftrag;
	private Collection<Auftragsposition> auftragspositionen;
	private Kunde kunde;

	@FXML
	Button bt_zurueck2;

	@FXML
	Button bt_aktualiesieren;

	@FXML
	Button bt_speichern;
	@FXML
	Button bt_Immobilienkredit;
	@FXML
	Button bt_Konsumentenkredit;

	@FXML
	Button bt_posEntfernen;
	@FXML
	Label lb_kundenVorname;
	@FXML
	Label lb_kundenName;

	@FXML
	TableView<Auftragsposition> tv_vertragsposition;

	@FXML
	TableColumn<Auftragsposition, Integer> tc_VertragsID;
	@FXML
	TableColumn<Auftragsposition, String> tc_Vertragsart;
	@FXML
	TableColumn<Auftragsposition, Integer> tc_Kreditbetrag;
	@FXML
	TableColumn<Auftragsposition, String> tc_Zinsbindung;
	@FXML
	TableColumn<Auftragsposition, String> tc_Sollzins;
	@FXML
	TableColumn<Auftragsposition, String> tc_Tilgungsrate;
	@FXML
	TableColumn<Auftragsposition, String> tc_Grundbuch;
	@FXML
	TableColumn<Auftragsposition, Integer> tc_Flurparzelle;
	@FXML
	TableColumn<Auftragsposition, String> tc_Konsumgut;

	ObservableList<Kunde> kundenliste = FXCollections.observableArrayList();
	ObservableList<Auftragsposition> positionsListeTableData = FXCollections.observableArrayList();

	@FXML
	ChoiceBox<Kunde> cb_selectKunde = new ChoiceBox<Kunde>(kundenliste);
	ChangeListener<Kunde> changeListener = new ChangeListener<Kunde>() {

		@Override
		public void changed(ObservableValue<? extends Kunde> observable, // //methode zum ausgewählten kunden anzuzeigen
				Kunde oldValue, Kunde newValue) {
			System.out.println("cb_selectKunde changed");
			if (newValue != null) {
				kundeSelectedEvent();
				lb_kundenName.setText(newValue.getName());
				lb_kundenVorname.setText(newValue.getVorname());
			}
		}
	};

	@FXML
	public void kundeSelectedEvent() {
		this.kunde = (Kunde) cb_selectKunde.getValue();
		this.auftrag = new Auftrag();
		this.auftrag.setKunde(this.kunde);
		this.auftragspositionen = new ArrayList<Auftragsposition>();
		System.out.println("Selected Kunde: " + this.kunde.getName());
	}

	@FXML
	public void bt_zurueckClicked() {
		this.myController.setScreen(Hauptmenue.MAIN_SCREEN);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	//	initData();
		tc_VertragsID.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Integer>("nr_im_auftrag"));
		tc_Vertragsart.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("vertragsart"));
		tc_Kreditbetrag.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Integer>("kreditbetrag"));
		tc_Zinsbindung.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("zinsbindung"));
		tc_Sollzins.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("sollzins"));
		tc_Tilgungsrate.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("tilgungsrate"));
		tc_Grundbuch.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("grundbuch"));
		tc_Flurparzelle.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Integer>("flurparzelle"));
		tc_Konsumgut.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("konsumgut"));

		
		cb_selectKunde.getSelectionModel().selectedItemProperty().addListener(changeListener);

	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;

	}

	@FXML
	public void bt_KonsumentenkreditClicked() {

		this.oldWidth = bt_speichern.getScene().getWindow().getWidth();
		this.oldHeight = bt_speichern.getScene().getWindow().getHeight();

		bt_speichern.getScene().getWindow().setWidth(800);
		bt_speichern.getScene().getWindow().setHeight(600);
		this.myController.setScreen(Hauptmenue.APOSITION_ANLEGEN_SCREEN);
	}

	@FXML
	public void bt_ImmobilienkreditClicked() {

		this.oldWidth = bt_speichern.getScene().getWindow().getWidth();
		this.oldHeight = bt_speichern.getScene().getWindow().getHeight();

		bt_speichern.getScene().getWindow().setWidth(800);
		bt_speichern.getScene().getWindow().setHeight(600);
		this.myController.setScreen(Hauptmenue.APOSITION_ANLEGEN2_SCREEN);
	}

	@FXML
	public void bt_PosEntfernenClicked() {
		Auftragsposition einePosition = tv_vertragsposition.getSelectionModel().getSelectedItem();
		System.out.println(einePosition.getNr_im_auftrag());
		removePosition(einePosition);

	}

	@FXML
	public void bt_speichernClicked() {
		this.auftrag.setaDatum(new Date());
		this.auftrag.setKunde((Kunde) cb_selectKunde.getValue());
		this.auftrag.setAuftragspositionen(this.auftragspositionen);
		System.out.println("Auftrag vor Speichern: \n" + this.auftrag.toString());
		myController.getKreditvertragManager().auftragSpeichern(this.auftrag);

		// Screen
		this.kunde = null;
		this.setAuftrag(new Auftrag());
		this.auftragspositionen = new ArrayList<Auftragsposition>();

		// Hauptmenue anzeigen
		this.myController.setScreen(Hauptmenue.MAIN_SCREEN);
	}

	@Override
	public void initData() {
		bt_aktualisierenClicked();

	}

	@FXML
	public void bt_aktualisierenClicked() {

		if (this.kundenliste.size() == 0) {
			// wird nur beim ersten Laden der Scene ausgeführt:
			Collection<Kunde> data = myController.getKundenManager().ladeAlleKunden();
			for (Kunde kunde : data) {
				kundenliste.add(kunde);
			}
			this.setAuftrag(new Auftrag());
			this.auftragspositionen = new ArrayList<Auftragsposition>();
		}

		cb_selectKunde.setItems(kundenliste);
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	public void addPosition(Auftragsposition aPosition) {
		aPosition.setAuftrag(this.auftrag);
		this.auftragspositionen.add(aPosition);
		this.positionsListeTableData.add(aPosition);
	}

	public void removePosition(Auftragsposition aPosition) {
		this.auftragspositionen.remove(aPosition);
		this.positionsListeTableData.remove(aPosition);
	}

	public void refreshKreditvertragListeScene() {
		tv_vertragsposition.setItems(this.positionsListeTableData);
	}

}
