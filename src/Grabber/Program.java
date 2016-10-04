//package Grabber;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Program extends Application{

	//private String user="";
	private String checkUser;
	private static String URL="";
	
	
	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		final Parameters params = getParameters(); // get command line args
		final List<String> parameters = params.getRaw();
		URL = !parameters.isEmpty() ? parameters.get(0):URL; // set the parameter[0] as url
		
		arg0.setTitle("Web Page Grabber");
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,50,50,50));

        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label lblUserName = new Label("Webpage URL: ");
        final TextField txtUserName = new TextField();
        
        if (URL.startsWith("http://") ||URL.startsWith("https://")){
        	txtUserName.setText(URL);
        }
        else txtUserName.setText("http://"+URL);
        
        Button btnLogin = new Button("Grab it!");
        final Label lblMessage = new Label();
        
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);
        
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        Text text = new Text("WEbpage Grabber");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        hb.getChildren().add(text);
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");
        
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		checkUser = txtUserName.getText().toString();
        		try {
					URL url = new URL(checkUser);
        			URLConnection con = url.openConnection();
        			con.setDoInput(true);
        			InputStream inStream = con.getInputStream();
        			BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
        			String html = "";
        		    String line = "";
        		    while ((line = input.readLine()) != null)
        		    {
        		        html += line;
        		    }
        		    File htmlTemplateFile = new File("src/"+URL.replace("/", "")+".html");
        		    FileUtils.writeStringToFile(htmlTemplateFile,html);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
        	}
        });
        
        bp.setTop(hb);
        bp.setCenter(gridPane);
        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("Grabber.css").toExternalForm());
        arg0.setScene(scene);
        arg0.titleProperty().bind(scene.widthProperty().asString().concat(" : ").concat(scene.heightProperty().asString()));
        arg0.show();
	}
}
