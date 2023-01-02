package controllers;


import Models.FilterItem;
import Models.GameItem;
import Models.ServerLocation;
import application.Main;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import utils.ExportHandler;
import utils.SearchHandler;

import java.util.Comparator;
import java.util.List;


public class MainMenuController extends Controller {

    @FXML
    private BorderPane topPane;
    @FXML
    private Button maximize, minimize, close;
    @FXML
    private MFXCheckListView<FilterItem> itemFilterList;
    @FXML
    private MFXButton boosButton, csButton, fssButton, lqoossButton, mvsButton, nvisButton, occmsButton, ocqiqsButton, ofimsButton, ofiqsButton, ogfqsButton, ogmsButton, ohiciButton, ohifiButton, ohvgiButton, ohvmButton, ommaciqsButton;
    @FXML
    private MFXButton searchButton;
    @FXML
    private MFXTextField scanHoursField, saleAmountField, roiField, minStackSizeField, minProfitAmountField, avgPricePerUnitField, apiEndpointField;
    @FXML
    private MFXCheckbox enableHQCheck, regionWideSearchCheck, includeVendorCheck, includeOOSCheck;
    @FXML
    private MFXComboBox<ServerLocation> locationPicker;
    @FXML
    private MFXProgressSpinner progressIndicator;
    @FXML
    private Label resultsLabel;
    @FXML
    private MFXButton exportButton;

    private MFXTableColumn<GameItem> itemNameCol;
    private MFXTableColumn<GameItem> linkCol;
    private MFXTableColumn<GameItem> universalisLinkCol;
    private MFXTableColumn<GameItem> npcVendorInforCol;
    private MFXTableColumn<GameItem> lowestPriceServerCol;
    private MFXTableColumn<GameItem> homeServerPriceCol;
    private MFXTableColumn<GameItem> lowestPricePerUnitCol;
    private MFXTableColumn<GameItem> profitAmountCol;
    private MFXTableColumn<GameItem> saleRatesCol;
    private MFXTableColumn<GameItem> avgPricePerUnitCol;
    private MFXTableColumn<GameItem> roiCol;
    private MFXTableColumn<GameItem> profitPercentageCol;
    private MFXTableColumn<GameItem> lowestPriceStackPrize;
    private MFXTableColumn<GameItem> lowestPriceLastUpdateTimeCol;
    private MFXTableColumn<GameItem> homeServerInfoLastUpdatedAtCol;
    @FXML
    private MFXTableView<GameItem> resultsTable;

    private Main main;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    public void fill() {
        this.main.getBs().setMoveControl(topPane);
        close.setOnAction(a -> this.main.getStg().close());
        minimize.setOnAction(a -> this.main.getStg().setIconified(true));
        maximize.setOnAction(a -> this.main.getBs().maximizeStage());
        setupServerLocations();
        setupQuickSearch();
        fillItemFilterList();
        locationPicker.setValue(locationPicker.getItems().get(0));
        searchButton.setOnAction(actionEvent -> search());
        boosButton.getOnAction().handle(new ActionEvent());
        progressIndicator.setVisible(false);
        itemNameCol = new MFXTableColumn<>("Item Name", true, Comparator.comparing(GameItem::getReal_name));
        linkCol = new MFXTableColumn<>("Item Data", true, Comparator.comparing(GameItem::getItem_id));
        universalisLinkCol = new MFXTableColumn<>("Universalis Link", true, Comparator.comparing(GameItem::getUrl));
        npcVendorInforCol = new MFXTableColumn<>("NPC Vendor Info", true, Comparator.comparing(GameItem::getNpc_vendor_info));
        lowestPriceServerCol = new MFXTableColumn<>("Lowest Price Server", true, Comparator.comparing(GameItem::getServer));
        homeServerPriceCol = new MFXTableColumn<>("Home Server Price", true, Comparator.comparing(GameItem::getHome_Server_price));
        lowestPricePerUnitCol = new MFXTableColumn<>("Lowest Price Per unit", true, Comparator.comparing(GameItem::getPpu));
        profitAmountCol = new MFXTableColumn<>("Profit Amount", true, Comparator.comparing(GameItem::getProfit_amount));
        saleRatesCol = new MFXTableColumn<>("Sale Rates", true, Comparator.comparing(GameItem::getSale_rates));
        avgPricePerUnitCol = new MFXTableColumn<>("Average Price Per Unit", true, Comparator.comparing(GameItem::getAvg_ppu));
        roiCol = new MFXTableColumn<>("Return on Investment", true, Comparator.comparing(GameItem::getRoi));
        profitPercentageCol = new MFXTableColumn<>("Profit Percentage", true, Comparator.comparing(GameItem::getProfit_raw_percent));
        lowestPriceStackPrize = new MFXTableColumn<>("Lowest Price Stack Size", true, Comparator.comparing(GameItem::getStack_size));
        lowestPriceLastUpdateTimeCol = new MFXTableColumn<>("Lowest Price Last Update Time", true, Comparator.comparing(GameItem::getUpdateTime));
        homeServerInfoLastUpdatedAtCol = new MFXTableColumn<>("Home Server Info Last Updated At", true, Comparator.comparing(GameItem::getHome_update_time));

        itemNameCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getReal_name));
        linkCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getItem_id));
        universalisLinkCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getUrl));
        npcVendorInforCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getNpc_vendor_info));
        lowestPriceServerCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getServer));
        homeServerPriceCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getHome_Server_price));
        lowestPricePerUnitCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getPpu));
        profitAmountCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getProfit_amount));
        saleRatesCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getSale_rates));
        avgPricePerUnitCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getAvg_ppu));
        roiCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getRoi));
        profitPercentageCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getProfit_raw_percent));
        lowestPriceStackPrize.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getStack_size));
        lowestPriceLastUpdateTimeCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getUpdateTimeString));
        homeServerInfoLastUpdatedAtCol.setRowCellFactory(gameItem -> new MFXTableRowCell<>(GameItem::getHome_update_timeString));
        itemNameCol.setPrefWidth(250);

        resultsTable.getTableColumns().add(itemNameCol);
        resultsTable.getTableColumns().add(linkCol);
        resultsTable.getTableColumns().add(universalisLinkCol);
        resultsTable.getTableColumns().add(npcVendorInforCol);
        resultsTable.getTableColumns().add(lowestPriceServerCol);
        resultsTable.getTableColumns().add(homeServerPriceCol);
        resultsTable.getTableColumns().add(lowestPricePerUnitCol);
        resultsTable.getTableColumns().add(profitAmountCol);
        resultsTable.getTableColumns().add(saleRatesCol);
        resultsTable.getTableColumns().add(avgPricePerUnitCol);
        resultsTable.getTableColumns().add(roiCol);
        resultsTable.getTableColumns().add(profitPercentageCol);
        resultsTable.getTableColumns().add(lowestPriceStackPrize);
        resultsTable.getTableColumns().add(lowestPriceLastUpdateTimeCol);
        resultsTable.getTableColumns().add(homeServerInfoLastUpdatedAtCol);

        resultsTable.autosizeColumns();
    }

    public void setupServerLocations() {
        locationPicker.getItems().add(new ServerLocation("Aether - Cactuar", "__session=eyJkYXRhX2NlbnRlciI6IkFldGhlciIsIndvcmxkIjoiQ2FjdHVhciJ9"));
        locationPicker.getItems().add(new ServerLocation("Dynamis - Halicarnassus", "__session=eyJkYXRhX2NlbnRlciI6IkR5bmFtaXMiLCJ3b3JsZCI6IkhhbGljYXJuYXNzdXMifQ%3D%3D"));
        locationPicker.getItems().add(new ServerLocation("Dynamis - Seraph", "__session=eyJkYXRhX2NlbnRlciI6IkR5bmFtaXMiLCJ3b3JsZCI6IlNlcmFwaCJ9"));
    }

    public void setupQuickSearch() {
        String[] boosItems = {"Arms", "Tools", "Armor", "Accessories", "Other"};
        boosButton.setOnAction(actionEvent -> applyPreset(99, 2, 99, 1, 100, 100, boosItems, true, false, false, true, "https://saddlebagexchange.com/queries/out-stock-scan?index=&_data=routes%2Fqueries%2Fout-stock-scan%2Findex"));
        String[] lqoossItems = {"-- Dyes", "Other"};
        lqoossButton.setOnAction(actionEvent -> applyPreset(99, 2, 99, 1, 100, 100, lqoossItems, false, false, true, true, "https://saddlebagexchange.com/queries/nq-out-stock-scan?index=&_data=routes%2Fqueries%2Fnq-out-stock-scan%2Findex"));
        String[] fssItems = {"Everything"};
        fssButton.setOnAction(actionEvent -> applyPreset(24, 20, 50, 1, 500, 500, fssItems, false, false, false, true, "https://saddlebagexchange.com/queries/fast-scan?index=&_data=routes%2Fqueries%2Ffast-scan%2Findex"));
        String[] csItems = {"Everything"};
        csButton.setOnAction(actionEvent -> applyPreset(24, 5, 50, 2, 1000, 1000, csItems, false, false, false, true, "https://saddlebagexchange.com/queries/commodity-scan?index=&_data=routes%2Fqueries%2Fcommodity-scan%2Findex"));
        String[] mvsItems = {"Everything"};
        mvsButton.setOnAction(actionEvent -> applyPreset(999, 1, 25, 1, 1000000, 1000000, mvsItems, false, true, true, true, "https://saddlebagexchange.com/queries/mega-value-scan?index=&_data=routes%2Fqueries%2Fmega-value-scan%2Findex"));
        String[] nvisItems = {"Purchasable from NPC Vendor"};
        nvisButton.setOnAction(actionEvent -> applyPreset(48, 5, 50, 1, 1000, 1000, nvisItems, false, false, true, true, "https://saddlebagexchange.com/queries/vendor-scan?index=&_data=routes%2Fqueries%2Fvendor-scan%2Findex"));
        String[] ogfqsItems = {"Everything"};
        ogfqsButton.setOnAction(actionEvent -> applyPreset(48, 5, 25, 1, 5000, 5000, ogfqsItems, false, false, true, true, "https://saddlebagexchange.com/queries/olivia1?index=&_data=routes%2Fqueries%2Folivia1%2Findex"));
        String[] ocqiqsItems = {"Supply and Provisioning Mission Quest Items", "Crafter Class Quest Items"};
        ocqiqsButton.setOnAction(actionEvent -> applyPreset(48, 2, 25, 1, 5000, 5000, ocqiqsItems, false, false, true, true, "https://saddlebagexchange.com/queries/olivia2?index=&_data=routes%2Fqueries%2Folivia2%2Findex"));
        String[] ofiqsItems = {"-- Furnishings", "-- Exterior Fixtures", "-- Interior Fixtures", "-- Outdoor Furnishings", "-- Chairs and Beds", "-- Tables", "-- Tabletop", "-- Wall-mounted", "-- Rugs", "-- Gardening Items", "-- Paintings"};
        ofiqsButton.setOnAction(actionEvent -> applyPreset(48, 5, 25, 1, 5000, 5000, ofiqsItems, false, false, true, true, "https://saddlebagexchange.com/queries/olivia3?index=&_data=routes%2Fqueries%2Folivia3%2Findex"));
        String[] ommaciqsItems = {"-- Minions", "-- Orchestration Components", "-- Registrable Miscellany"};
        ommaciqsButton.setOnAction(actionEvent -> applyPreset(48, 5, 25, 1, 5000, 5000, ommaciqsItems, false, false, true, true, "https://saddlebagexchange.com/queries/olivia4?index=&_data=routes%2Fqueries%2Folivia4%2Findex"));
        String[] ofimsItems = {"-- Furnishings", "-- Exterior Fixtures", "-- Interior Fixtures", "-- Outdoor Furnishings", "-- Chairs and Beds", "-- Tables", "-- Tabletop", "-- Wall-mounted", "-- Rugs", "-- Gardening Items", "-- Paintings"};
        ofimsButton.setOnAction(actionEvent -> applyPreset(168, 2, 25, 1, 75000, 30000, ofimsItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia5?index=&_data=routes%2Fqueries%2Folivia5%2Findex"));
        String[] occmsItems = {"-- Minions", "-- Orchestration Components", "-- Registrable Miscellany"};
        occmsButton.setOnAction(actionEvent -> applyPreset(168, 2, 25, 1, 75000, 30000, occmsItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia6?index=&_data=routes%2Fqueries%2Folivia6%2Findex"));
        String[] ogmsItems = {"Arms", "Tools"};
        ogmsButton.setOnAction(actionEvent -> applyPreset(168, 2, 25, 1, 75000, 30000, ogmsItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia7?index=&_data=routes%2Fqueries%2Folivia7%2Findex"));
        String[] ohifiItems = {"-- Furnishings", "-- Exterior Fixtures", "-- Interior Fixtures", "-- Outdoor Furnishings", "-- Chairs and Beds", "-- Tables", "-- Tabletop", "-- Wall-mounted", "-- Rugs", "-- Gardening Items", "-- Paintings"};
        ohifiButton.setOnAction(actionEvent -> applyPreset(336, 1, 25, 1, 300000, 300000, ohifiItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia10?index=&_data=routes%2Fqueries%2Folivia10%2Findex"));
        String[] ohiciItems = {"-- Minions", "-- Orchestration Components", "-- Registrable Miscellany"};
        ohiciButton.setOnAction(actionEvent -> applyPreset(336, 1, 25, 1, 300000, 300000, ohiciItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia8?index=&_data=routes%2Fqueries%2Folivia8%2Findex"));
        String[] ohvgiItems = {"Arms", "Tools"};
        ohvgiButton.setOnAction(actionEvent -> applyPreset(336, 1, 25, 1, 300000, 300000, ohvgiItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia9?index=&_data=routes%2Fqueries%2Folivia9%2Findex"));
        String[] ohvmItems = {"Materials"};
        ohvmButton.setOnAction(actionEvent -> applyPreset(336, 1, 25, 1, 300000, 300000, ohvmItems, false, true, true, true, "https://saddlebagexchange.com/queries/olivia11?index=&_data=routes%2Fqueries%2Folivia11%2Findex"));
    }

    public void applyPreset(int scanHours, int saleAmount, int roi, int minStackSize, double minProfitAmount, double avgPricePerUnit, String[] items, boolean enableHQ, boolean regionWide, boolean includeVendor, boolean includeOOS, String apiEndpoint) {
        scanHoursField.setText(String.valueOf(scanHours));
        saleAmountField.setText(String.valueOf(saleAmount));
        roiField.setText(String.valueOf(roi));
        minStackSizeField.setText(String.valueOf(minStackSize));
        minProfitAmountField.setText(String.valueOf(minProfitAmount));
        avgPricePerUnitField.setText(String.valueOf(avgPricePerUnit));
        itemFilterList.getSelectionModel().clearSelection();
        for (String s : items) {
            for (FilterItem f : itemFilterList.getItems()) {
                if (f.getItemText().equals(s))
                    itemFilterList.getSelectionModel().selectItem(f);
            }
        }
        enableHQCheck.setSelected(enableHQ);
        regionWideSearchCheck.setSelected(regionWide);
        includeVendorCheck.setSelected(includeVendor);
        includeOOSCheck.setSelected(includeOOS);
        apiEndpointField.setText(apiEndpoint);
    }

    public void fillItemFilterList() {
        itemFilterList.getItems().add(new FilterItem("Everything", 0));
        itemFilterList.getItems().add(new FilterItem("Purchasable from NPC Vendor", -1));
        itemFilterList.getItems().add(new FilterItem("Supply and Provisioning Mission Quest Items", -2));
        itemFilterList.getItems().add(new FilterItem("Crafter Class Quest Items", -3));
        itemFilterList.getItems().add(new FilterItem("Arms", 1));
        itemFilterList.getItems().add(new FilterItem("-- Archanist's Arms", 16));
        itemFilterList.getItems().add(new FilterItem("-- Archer's Arms", 12));
        itemFilterList.getItems().add(new FilterItem("-- Astrologian's Arms", 78));
        itemFilterList.getItems().add(new FilterItem("-- Blue Mage's Arms", 105));
        itemFilterList.getItems().add(new FilterItem("-- Conjurer's Arms", 15));
        itemFilterList.getItems().add(new FilterItem("-- Dancer's Arms", 87));
        itemFilterList.getItems().add(new FilterItem("-- Dark Knight's Arms", 76));
        itemFilterList.getItems().add(new FilterItem("-- Gladiator's Arms", 10));
        itemFilterList.getItems().add(new FilterItem("-- Gunbreaker's Arms", 86));
        itemFilterList.getItems().add(new FilterItem("-- Lancer's Arms", 13));
        itemFilterList.getItems().add(new FilterItem("-- Machinist's Arms", 77));
        itemFilterList.getItems().add(new FilterItem("-- Marauder's Arms", 11));
        itemFilterList.getItems().add(new FilterItem("-- Pugilist's Arms", 9));
        itemFilterList.getItems().add(new FilterItem("-- Red Mage's Arms", 84));
        itemFilterList.getItems().add(new FilterItem("-- Rogue's Arms", 73));
        itemFilterList.getItems().add(new FilterItem("-- Reaper's Arms", 88));
        itemFilterList.getItems().add(new FilterItem("-- Samurai's Arms", 83));
        itemFilterList.getItems().add(new FilterItem("-- Scholar's Arms", 85));
        itemFilterList.getItems().add(new FilterItem("-- Sage's Arms", 89));
        itemFilterList.getItems().add(new FilterItem("-- Thaumaturge's Arms", 14));
        itemFilterList.getItems().add(new FilterItem("Tools", 2));
        itemFilterList.getItems().add(new FilterItem("-- Alchemist's Tools", 25));
        itemFilterList.getItems().add(new FilterItem("-- Armorer's Tools", 21));
        itemFilterList.getItems().add(new FilterItem("-- Blacksmith's Tools", 20));
        itemFilterList.getItems().add(new FilterItem("-- Botanist's Tools", 28));
        itemFilterList.getItems().add(new FilterItem("-- Carpenter's Tools", 19));
        itemFilterList.getItems().add(new FilterItem("-- Culinarian's Tools", 26));
        itemFilterList.getItems().add(new FilterItem("-- Fisher's Tackle", 30));
        itemFilterList.getItems().add(new FilterItem("-- Fisher's Tools", 29));
        itemFilterList.getItems().add(new FilterItem("-- Goldsmith's Tools", 22));
        itemFilterList.getItems().add(new FilterItem("-- Leatherworker's Tools", 23));
        itemFilterList.getItems().add(new FilterItem("-- Miner's Tools", 27));
        itemFilterList.getItems().add(new FilterItem("-- Weaver's Tools", 24));
        itemFilterList.getItems().add(new FilterItem("Armor", 3));
        itemFilterList.getItems().add(new FilterItem("-- Shields", 17));
        itemFilterList.getItems().add(new FilterItem("-- Head", 31));
        itemFilterList.getItems().add(new FilterItem("-- Body", 33));
        itemFilterList.getItems().add(new FilterItem("-- Legs", 35));
        itemFilterList.getItems().add(new FilterItem("-- Hands", 36));
        itemFilterList.getItems().add(new FilterItem("-- Feet", 37));
        itemFilterList.getItems().add(new FilterItem("Accessories", 4));
        itemFilterList.getItems().add(new FilterItem("-- Necklaces", 39));
        itemFilterList.getItems().add(new FilterItem("-- Earrings", 40));
        itemFilterList.getItems().add(new FilterItem("-- Bracelets", 41));
        itemFilterList.getItems().add(new FilterItem("-- Rings", 42));
        itemFilterList.getItems().add(new FilterItem("Medicines & Meals", 5));
        itemFilterList.getItems().add(new FilterItem("-- Medicine", 43));
        itemFilterList.getItems().add(new FilterItem("-- Ingredients", 44));
        itemFilterList.getItems().add(new FilterItem("-- Meals", 45));
        itemFilterList.getItems().add(new FilterItem("-- Seafood", 46));
        itemFilterList.getItems().add(new FilterItem("Materials", 6));
        itemFilterList.getItems().add(new FilterItem("-- Stone", 47));
        itemFilterList.getItems().add(new FilterItem("-- Metal", 48));
        itemFilterList.getItems().add(new FilterItem("-- Lumber", 49));
        itemFilterList.getItems().add(new FilterItem("-- Cloth", 50));
        itemFilterList.getItems().add(new FilterItem("-- Leather", 51));
        itemFilterList.getItems().add(new FilterItem("-- Bone", 52));
        itemFilterList.getItems().add(new FilterItem("-- Reagents", 53));
        itemFilterList.getItems().add(new FilterItem("-- Dyes", 54));
        itemFilterList.getItems().add(new FilterItem("-- Weapon Parts", 55));
        itemFilterList.getItems().add(new FilterItem("Other", 7));
        itemFilterList.getItems().add(new FilterItem("-- Furnishings", 56));
        itemFilterList.getItems().add(new FilterItem("-- Materia", 57));
        itemFilterList.getItems().add(new FilterItem("-- Crystals", 58));
        itemFilterList.getItems().add(new FilterItem("-- Catalysts", 59));
        itemFilterList.getItems().add(new FilterItem("-- Miscellany", 60));
        itemFilterList.getItems().add(new FilterItem("-- Exterior Fixtures", 65));
        itemFilterList.getItems().add(new FilterItem("-- Interior Fixtures", 66));
        itemFilterList.getItems().add(new FilterItem("-- Outdoor Furnishings", 67));
        itemFilterList.getItems().add(new FilterItem("-- Chairs and Beds", 68));
        itemFilterList.getItems().add(new FilterItem("-- Tables", 69));
        itemFilterList.getItems().add(new FilterItem("-- Tabletop", 70));
        itemFilterList.getItems().add(new FilterItem("-- Wall-mounted", 71));
        itemFilterList.getItems().add(new FilterItem("-- Rugs", 72));
        itemFilterList.getItems().add(new FilterItem("-- Seasonal Miscellany", 74));
        itemFilterList.getItems().add(new FilterItem("-- Minions", 75));
        itemFilterList.getItems().add(new FilterItem("-- Airship/Submersible Components", 79));
        itemFilterList.getItems().add(new FilterItem("-- Orchestration Components", 80));
        itemFilterList.getItems().add(new FilterItem("-- Gardening Items", 81));
        itemFilterList.getItems().add(new FilterItem("-- Paintings", 82));
        itemFilterList.getItems().add(new FilterItem("-- Registrable Miscellany", 90));
    }

    public void search() {
        int scanHours = Integer.parseInt(scanHoursField.getText());
        int saleAmount = Integer.parseInt(saleAmountField.getText());
        int roi = Integer.parseInt(roiField.getText());
        int minStackSize = Integer.parseInt(minStackSizeField.getText());
        double minProfitAmount = Double.parseDouble(minProfitAmountField.getText());
        double avgPricePerUnit = Double.parseDouble(avgPricePerUnitField.getText());
        List<FilterItem> itemFilters = itemFilterList.getSelectionModel().getSelectedValues();
        boolean enableHQ = enableHQCheck.isSelected();
        boolean regionWide = regionWideSearchCheck.isSelected();
        boolean includeVendor = includeVendorCheck.isSelected();
        boolean includeOOS = includeOOSCheck.isSelected();
        String apiEndpoint = apiEndpointField.getText();
        String cookieValue = locationPicker.getValue().getCookieValue();
        progressIndicator.setVisible(true);
        resultsLabel.setVisible(false);
        searchButton.setDisable(true);
        resultsTable.setDisable(true);
        exportButton.setVisible(false);
        resultsTable.getItems().clear();
        Task<ObservableList<GameItem>> task = new Task<>() {
            @Override
            protected ObservableList<GameItem> call() throws Exception {
                return SearchHandler.search(scanHours, saleAmount, roi, minStackSize, minProfitAmount, avgPricePerUnit, itemFilters, enableHQ, regionWide, includeVendor, includeOOS, apiEndpoint, cookieValue);
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        task.setOnFailed(evt -> {
            progressIndicator.setVisible(false);
            searchButton.setDisable(false);
            resultsLabel.setText("Seacrh failed to return any results, please try again");
            task.getException().printStackTrace();
            resultsLabel.setVisible(true);
            resultsTable.setDisable(false);
        });
        task.setOnSucceeded(evt -> {
            progressIndicator.setVisible(false);
            searchButton.setDisable(false);
            resultsLabel.setText("Found: " + task.getValue().size() + " items!");
            resultsLabel.setVisible(true);
            resultsTable.setDisable(false);
            resultsTable.setItems(task.getValue());
            resultsTable.autosizeColumns();
            exportButton.setVisible(true);
            exportButton.setOnAction(actionEvent -> ExportHandler.export(task.getValue(),main));
        });
        new Thread(task).start();
    }
}