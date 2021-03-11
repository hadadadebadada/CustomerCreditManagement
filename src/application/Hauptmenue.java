//Artur Pfeifer 879089
package application;

import java.util.Optional;

import awk.usecase.IKreditvertragVw;
import awk.usecase.IKundenVw;
import awk.usecase.impl.KreditvertragVerwalter;
import awk.usecase.impl.KundenVerwalter;
import dlg.ScreensController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class Hauptmenue extends Application {

	private Stage mainStage;
	private IKundenVw kundenManager;
	private IKreditvertragVw kreditvertragManager;

	public static final String MAIN_SCREEN = "main";
	public static final String MAIN_SCREEN_FXML = "dlg/hauptmenue/Hauptmenue.fxml";

	public static final String KUNDENLISTE_SCENE_SCREEN = "Kundenliste_Screen";
	public static final String KUNDENLISTE_SCENE_SCREEN_FXML = "dlg/scene/Kundenliste_Scene.fxml";

	public static final String SCENE2_SCREEN = "scene2";
	public static final String SCENE2_SCREEN_FXML = "dlg/scene/KundenAnlegen_Scene.fxml"; // !!

	public static final String KREDITVERTRAGLISTE_SCENE_SCREEN = "KreditvertragListe_Screen";
	public static final String KREDITVERTRAGLISTE_SCENE_SCREEN_FXML = "dlg/scene/KreditvertragListe_Scene.fxml";

	public static final String APOSITION_ANLEGEN_SCREEN = "scene4";
	public static final String APOSITION_ANLEGEN_SCREEN_FXML = "dlg/scene/AuftragspositionAnlegen_Scene.fxml";

	public static final String APOSITION_ANLEGEN2_SCREEN = "scene5";
	public static final String APOSITION_ANLEGEN2_SCREEN_FXML = "dlg/scene/AuftragspositionAnlegen2_Scene.fxml";

	@Override
	public void start(Stage primaryStage) {

		this.mainStage = primaryStage;

		this.kundenManager = new KundenVerwalter();
		this.kreditvertragManager = new KreditvertragVerwalter();

		ScreensController mainContainer = new ScreensController();

		mainContainer.setKundenManager(this.kundenManager);
		mainContainer.setKreditvertragManager(this.kreditvertragManager);

		mainContainer.loadScreen(Hauptmenue.MAIN_SCREEN, Hauptmenue.MAIN_SCREEN_FXML);
		mainContainer.loadScreen(Hauptmenue.KUNDENLISTE_SCENE_SCREEN, Hauptmenue.KUNDENLISTE_SCENE_SCREEN_FXML);
		mainContainer.loadScreen(Hauptmenue.SCENE2_SCREEN, Hauptmenue.SCENE2_SCREEN_FXML);
		mainContainer.loadScreen(Hauptmenue.KREDITVERTRAGLISTE_SCENE_SCREEN,
				Hauptmenue.KREDITVERTRAGLISTE_SCENE_SCREEN_FXML);
		mainContainer.loadScreen(Hauptmenue.APOSITION_ANLEGEN_SCREEN, Hauptmenue.APOSITION_ANLEGEN_SCREEN_FXML);
		mainContainer.loadScreen(Hauptmenue.APOSITION_ANLEGEN2_SCREEN, Hauptmenue.APOSITION_ANLEGEN2_SCREEN_FXML);

		mainContainer.setScreen(Hauptmenue.MAIN_SCREEN);

		try {
			Group root = new Group();
			root.getChildren().addAll(mainContainer);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(confirmCloseEventHandler);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
		// Quelle:
		// http://stackoverflow.com/questions/29710492/javafx-internal-close-request
		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
		Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
		exitButton.setText("Exit");
		closeConfirmation.setHeaderText("Confirm Exit");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
		closeConfirmation.initOwner(mainStage);

		// normally, you would just use the default alert positioning,
		// but for this simple sample the main stage is small,
		// so explicitly position the alert so that the main window can still be seen.
		closeConfirmation.setX(mainStage.getX() + 150);
		closeConfirmation.setY(mainStage.getY() - 300 + mainStage.getHeight());

		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
		if (!ButtonType.OK.equals(closeResponse.get())) {
			event.consume();
		}
	};

	public static void main(String[] args) {
		launch(args);
	}
}
