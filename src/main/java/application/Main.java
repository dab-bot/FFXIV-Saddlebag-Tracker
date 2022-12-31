package application;


import controllers.Controller;
import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	private static Stage stg;
	public Controller c;
	private static BorderlessScene bs;
	private Image icon = new Image(getClass().getResourceAsStream("/images/img.png"));

	@Override
	public void start(Stage primaryStage) throws Exception{
		stg = primaryStage;
		System.setProperty("prism.lcdtext", "false");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FXML/MainMenu.fxml"));
		Parent root = loader.load();
		c = loader.getController();
		c.setMain(this);
		primaryStage.setTitle("Overwatch Account Tracker");
		bs = new BorderlessScene(primaryStage, StageStyle.UNDECORATED,root);
		bs.removeDefaultCSS();
		primaryStage.setScene(bs);
		primaryStage.getIcons().add(icon);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
		bs.maximizeStage();
		bs.setFill(Color.TRANSPARENT);
		bs.setResizable(true);
		c.fill();
	}

	public static void main(String[] args) {
		launch();
	}

	public Stage getStg(){
		return stg;
	}
	public BorderlessScene getBs() {
		return bs;
	}

}
