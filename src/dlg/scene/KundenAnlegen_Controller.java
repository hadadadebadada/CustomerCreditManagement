//Artur Pfeifer 879089

package dlg.scene;

import java.net.URL;
import java.util.ResourceBundle;

import application.Hauptmenue;
import awk.Kunde;
import dlg.ControlledScreen;

import dlg.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class KundenAnlegen_Controller implements Initializable, ControlledScreen {

	@FXML
	Button bt_zurueck;
	@FXML
	Button bt_kundenSpeichern;
	@FXML
	TextField tf_kundenName;
	@FXML
	TextField tf_kundenVorname;
	@FXML
	TextField tf_kundenGebDatum;

	private ScreensController myController;

//	FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));  //-->> controller zu fxml hashmap checken
//	loader.setController(new MainController(path));
//	Pane mainPane = loader.load();

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void bt_kundenSpeichernButtonClicked() {
		String name = tf_kundenName.getText();
		String vorname = tf_kundenVorname.getText();
		String gebDatum = tf_kundenGebDatum.getText();
		Kunde aKunde = new Kunde(name, vorname, gebDatum);
		myController.getKundenManager().speicherKunde(aKunde);
		this.myController.setScreen(Hauptmenue.MAIN_SCREEN);

	}

	@FXML
	public void bt_zurueckClicked() {
		this.myController.setScreen(Hauptmenue.MAIN_SCREEN);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
}
