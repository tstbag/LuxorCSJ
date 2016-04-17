package poker.app.view;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import poker.app.MainApp;
import pokerBase.Action;
import pokerBase.Card;
import pokerBase.Deck;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Hand;
import pokerBase.Player;
import pokerBase.Table;
import pokerEnums.eAction;
import pokerEnums.ePlayerPosition;

public class PokerTableController {

	// Reference to the main application.
	private MainApp mainApp;

	public PokerTableController() {
	}

	@FXML
	private ToggleButton btnPos1SitLeave;
	@FXML
	private ToggleButton btnPos2SitLeave;
	@FXML
	private ToggleButton btnPos3SitLeave;
	@FXML
	private ToggleButton btnPos4SitLeave;

	@FXML
	private Label lblPos1Name;
	@FXML
	private Label lblPos2Name;
	@FXML
	private Label lblPos3Name;
	@FXML
	private Label lblPos4Name;

	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	@FXML
	private void handlePlay() {
	}

	@FXML
	public void GetGameState() {
		Action act = new Action(eAction.GameState, mainApp.getPlayer());
		mainApp.messageSend(act);
	}

	public void btnSitLeave_click(ActionEvent event) {
		ToggleButton btnSitLeave = (ToggleButton) event.getSource();
		Action act = new Action(btnSitLeave.isSelected() ? eAction.Sit : eAction.Leave, mainApp.getPlayer());
		switch (btnSitLeave.getId().toString()) {
		case "btnPos1SitLeave":
			mainApp.getPlayer().setiPlayerPosition(ePlayerPosition.ONE.getiPlayerPosition());			
			act.setiPlayerPosition(ePlayerPosition.ONE.getiPlayerPosition());
			//btnPos2SitLeave.setVisible(!btnSitLeave.isSelected());
			//btnPos3SitLeave.setVisible(!btnSitLeave.isSelected());
			//btnPos4SitLeave.setVisible(!btnSitLeave.isSelected());
			break;
		case "btnPos2SitLeave":
			mainApp.getPlayer().setiPlayerPosition(ePlayerPosition.TWO.getiPlayerPosition());
			//lblPos2Name.setText(btnSitLeave.isSelected() ? mainApp.getPlayer().getPlayerName() : "");
			act.setiPlayerPosition(ePlayerPosition.TWO.getiPlayerPosition());
			btnPos1SitLeave.setVisible(!btnSitLeave.isSelected());
			btnPos3SitLeave.setVisible(!btnSitLeave.isSelected());
			btnPos4SitLeave.setVisible(!btnSitLeave.isSelected());
			break;
		case "btnPos3SitLeave":
			mainApp.getPlayer().setiPlayerPosition(ePlayerPosition.THREE.getiPlayerPosition());
			//lblPos3Name.setText(btnSitLeave.isSelected() ? mainApp.getPlayer().getPlayerName() : "");
			act.setiPlayerPosition(ePlayerPosition.THREE.getiPlayerPosition());
			//btnPos1SitLeave.setVisible(!btnSitLeave.isSelected());
			//btnPos2SitLeave.setVisible(!btnSitLeave.isSelected());
			//btnPos4SitLeave.setVisible(!btnSitLeave.isSelected());

			break;
		case "btnPos4SitLeave":
			mainApp.getPlayer().setiPlayerPosition(ePlayerPosition.FOUR.getiPlayerPosition());
			//lblPos4Name.setText(btnSitLeave.isSelected() ? mainApp.getPlayer().getPlayerName() : "");
			act.setiPlayerPosition(ePlayerPosition.FOUR.getiPlayerPosition());
			//btnPos1SitLeave.setVisible(!btnSitLeave.isSelected());
			//btnPos2SitLeave.setVisible(!btnSitLeave.isSelected());
			//btnPos3SitLeave.setVisible(!btnSitLeave.isSelected());

			break;
		}
		mainApp.messageSend(act);
	}

	public void btnSitLeave_response(Table HubPokerTable) {

		System.out.println("Executing: btnSitLeave_response");
		lblPos1Name.setText("");
		lblPos2Name.setText("");
		lblPos3Name.setText("");
		lblPos4Name.setText("");
		
		Iterator it = HubPokerTable.getHashPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			switch (p.getiPlayerPosition())
			{
			case 1:
				lblPos1Name.setText(p.getPlayerName().toString());
				break;
			case 2:
				lblPos2Name.setText(p.getPlayerName().toString());
				break;
			case 3:
				lblPos3Name.setText(p.getPlayerName().toString());
				break;
			case 4:
				lblPos4Name.setText(p.getPlayerName().toString());
				break;
			}
		}
	}

	@FXML
	void btnStart(ActionEvent event) {
		Action act = new Action(eAction.StartGame, mainApp.getPlayer());
		mainApp.messageSend(act);
	}

	@FXML
	void btnDeal_Click(ActionEvent event) {
		Action act = new Action(eAction.Deal, mainApp.getPlayer());
		mainApp.messageSend(act);
	}

	@FXML
	public void btnFold(ActionEvent event) {
		Button btnFold = (Button) event.getSource();

		switch (btnFold.getId().toString()) {
		case "btnPlayer1Fold":
			System.out.println("Player Number: " + mainApp.GetPlayerID());
			mainApp.messageSend("Test 123");

			/*
			 * Action act = new Action(eAction.Fold,mainApp.getPlayer()); String
			 * str = SerializeMe(act).toString();
			 * 
			 * mainApp.SendMessage(str); System.out.println(str);
			 */

			break;
		}
	}

	public StringWriter SerializeMe(Action act) {
		StringWriter sw = new StringWriter();
		try {
			// Write it
			JAXBContext ctx = JAXBContext.newInstance(Action.class);
			Marshaller m = ctx.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(act, sw);
			sw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sw;
	}

}
