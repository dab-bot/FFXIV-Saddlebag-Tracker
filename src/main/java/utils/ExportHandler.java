package utils;

import Models.GameItem;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.YearMonth;

public class ExportHandler {

    public static void export(ObservableList<GameItem> items, Main main){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Save Location");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(main.getStg());
        if (file != null) {
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println("Item Name,Item Data,Universalis Link,NPC Vendor Info,Lowest Price Server,Home Server Price,Lowest Price Per Unit,Profit Amount,Sale Rates,Average Price Per Unit,Return on Investment,Profit Percentage,Lowest Price Last Update Time,Home Server Info Last Updated At");

                for(GameItem g: items){
                    pw.print(g.getReal_name()+",");
                    pw.print(g.getItem_id()+",");
                    pw.print(g.getUrl()+",");
                    pw.print(g.getNpc_vendor_info()+",");
                    pw.print(g.getServer()+",");
                    pw.print(g.getHome_Server_price()+",");
                    pw.print(g.getPpu()+",");
                    pw.print(g.getProfit_amount()+",");
                    pw.print(g.getSale_rates()+",");
                    pw.print(g.getAvg_ppu()+",");
                    pw.print(g.getRoi()+",");
                    pw.print(g.getProfit_raw_percent()+",");
                    pw.print(g.getStack_size()+",");
                    pw.print(g.getUpdateTime()+",");
                    pw.println(g.getHome_update_time());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Success");
                alert.setHeaderText(null);
                alert.setContentText("Data exported Successfully!");
                alert.showAndWait();
            } catch (FileNotFoundException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("This file cant be accessed, please ensure its not open in another program");
                alert.showAndWait();
            }
        }
    }
}
