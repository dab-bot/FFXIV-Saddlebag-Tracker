package controllers;


import application.Main;
import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Optional;


public class MainMenuController extends Controller {

    @FXML
    private BorderPane contentPane, topPane;
    @FXML
    private HBox controlBox;
    @FXML
    private Button maximize, minimize, close;
    @FXML
    private VBox accountRowContainer;
    @FXML
    private MFXScrollPane accountScroll;
    @FXML
    private MFXCheckListView<FilterItem> itemFilterList;
    @FXML
    private MFXButton boosButton, csButton, fssButton, lqoossButton, mvsButton, nvisButton, occmsButton, ocqiqsButton, ofimsButton, ofiqsButton, ogfqsButton, ogmsButton, ohiciButton, ohifiButton, ohvgiButton, ohvmButton, ommaciqsButton;
    @FXML
    private MFXTextField scanHoursField, saleAmountField, roiField, minStackSizeField, minProfitAmountField, avgPricePerUnitField, apiEndpointField;
    @FXML
    private MFXCheckbox enableHQCheck, regionWideSearchCheck, includeVendorCheck, includeOOSCheck;
    private Main main;
    private ObservableList<HBox> rows = FXCollections.observableArrayList();
    private int threadCount, expectedThreadCount;
    private boolean loadingAccounts = false;

    @Override
    public void setMain(Main main) {
        this.main = main;
    }

    public void fill() {
        this.main.getBs().setMoveControl(topPane);
        close.setOnAction(a -> this.main.getStg().close());
        minimize.setOnAction(a -> this.main.getStg().setIconified(true));
        maximize.setOnAction(a -> this.main.getBs().maximizeStage());
        fillItemFilterList();
        setupQuickSearch();
        loadAccounts();
    }

    public void setupQuickSearch() {
        String[] boosItems = {"Arms", "Tools", "Armor", "Accessories", "Other"};
        boosButton.setOnAction(actionEvent -> applyPreset(99, 2, 99, 1, 100, 100, boosItems, true, false, false, true, "https://saddlebagexchange.com/queries/out-stock-scan?index=&_data=routes%2Fqueries%2Fout-stock-scan%2Findex"));
        String[] lqoossItems = {"-- Dyes", "Other"};
        lqoossButton.setOnAction(actionEvent -> applyPreset(99, 2, 99, 1, 100, 100, lqoossItems, false, false, true, true, "https://saddlebagexchange.com/queries/nq-out-stock-scan?index=&_data=routes%2Fqueries%2Fnq-out-stock-scan%2Findex"));
        String[] fssItems = {"Everything"};
        fssButton.setOnAction(actionEvent -> applyPreset(24,20,50,1,500,500,fssItems,false,false,false,true,"https://saddlebagexchange.com/queries/fast-scan?index=&_data=routes%2Fqueries%2Ffast-scan%2Findex"));
        String[] csItems = {"Everything"};
        csButton.setOnAction(actionEvent -> applyPreset(24,5,50,2,1000,1000,csItems,false,false,false,true,"https://saddlebagexchange.com/queries/commodity-scan?index=&_data=routes%2Fqueries%2Fcommodity-scan%2Findex"));
        String[] mvsItems = {"Everything"};
        mvsButton.setOnAction(actionEvent -> applyPreset(999,1,25,1,1000000,1000000,mvsItems,false,true,true,true,"https://saddlebagexchange.com/queries/mega-value-scan?index=&_data=routes%2Fqueries%2Fmega-value-scan%2Findex"));
        String[] nvisItems = {"Purchasable from NPC Vendor"};
        nvisButton.setOnAction(actionEvent -> applyPreset(48,5,50,1,1000,1000,nvisItems,false,false,true,true,"https://saddlebagexchange.com/queries/vendor-scan?index=&_data=routes%2Fqueries%2Fvendor-scan%2Findex"));
        String[] ogfqsItems = {"Everything"};
        ogfqsButton.setOnAction(actionEvent -> applyPreset(48,5,25,1,5000,5000,ogfqsItems,false,false,true,true,"https://saddlebagexchange.com/queries/olivia1?index=&_data=routes%2Fqueries%2Folivia1%2Findex"));
        String[] ocqiqsItems = {"Supply and Provisioning Mission Quest Items","Crafter Class Quest Items"};
        ocqiqsButton.setOnAction(actionEvent -> applyPreset(48,2,25,1,5000,5000,ocqiqsItems,false,false,true,true,"https://saddlebagexchange.com/queries/olivia2?index=&_data=routes%2Fqueries%2Folivia2%2Findex"));
        String[] ofiqsItems = {"-- Furnishings","-- Exterior Fixtures","-- Interior Fixtures","-- Outdoor Furnishings","-- Chairs and Beds","-- Tables","-- Tabletop","-- Wall-mounted","-- Rugs","-- Gardening Items","-- Paintings"};
        ofiqsButton.setOnAction(actionEvent -> applyPreset(48,5,25,1,5000,5000,ofiqsItems,false,false,true,true,"https://saddlebagexchange.com/queries/olivia3?index=&_data=routes%2Fqueries%2Folivia3%2Findex"));
        String [] ommaciqsItems = {"-- Minions","-- Orchestration Components","-- Registrable Miscellany"};
        ommaciqsButton.setOnAction(actionEvent -> applyPreset(48,5,25,1,5000,5000,ommaciqsItems,false,false,true,true,"https://saddlebagexchange.com/queries/olivia4?index=&_data=routes%2Fqueries%2Folivia4%2Findex"));
        String [] ofimsItems = {"-- Furnishings","-- Exterior Fixtures","-- Interior Fixtures","-- Outdoor Furnishings","-- Chairs and Beds","-- Tables","-- Tabletop","-- Wall-mounted","-- Rugs","-- Gardening Items","-- Paintings"};
        ofimsButton.setOnAction(actionEvent -> applyPreset(168,2,25,1,75000,30000,ofimsItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia5?index=&_data=routes%2Fqueries%2Folivia5%2Findex"));
        String[] occmsItems = {"-- Minions","-- Orchestration Components","-- Registrable Miscellany"};
        occmsButton.setOnAction(actionEvent -> applyPreset(168,2,25,1,75000,30000,occmsItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia6?index=&_data=routes%2Fqueries%2Folivia6%2Findex"));
        String[] ogmsItems = {"Arms","Tools"};
        ogmsButton.setOnAction(actionEvent -> applyPreset(168,2,25,1,75000,30000,ogmsItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia7?index=&_data=routes%2Fqueries%2Folivia7%2Findex"));
        String[] ohifiItems = {"-- Furnishings","-- Exterior Fixtures","-- Interior Fixtures","-- Outdoor Furnishings","-- Chairs and Beds","-- Tables","-- Tabletop","-- Wall-mounted","-- Rugs","-- Gardening Items","-- Paintings"};
        ohifiButton.setOnAction(actionEvent -> applyPreset(336,1,25,1,300000,300000,ohifiItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia10?index=&_data=routes%2Fqueries%2Folivia10%2Findex"));
        String[] ohiciItems = {"-- Minions","-- Orchestration Components","-- Registrable Miscellany"};
        ohiciButton.setOnAction(actionEvent -> applyPreset(336,1,25,1,300000,300000,ohiciItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia8?index=&_data=routes%2Fqueries%2Folivia8%2Findex"));
        String[] ohvgiItems = {"Arms","Tools"};
        ohvgiButton.setOnAction(actionEvent -> applyPreset(336,1,25,1,300000,300000,ohvgiItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia9?index=&_data=routes%2Fqueries%2Folivia9%2Findex"));
        String[] ohvmItems = {"Materials"};
        ohvmButton.setOnAction(actionEvent -> applyPreset(336,1,25,1,300000,300000,ohvmItems,false,true,true,true,"https://saddlebagexchange.com/queries/olivia11?index=&_data=routes%2Fqueries%2Folivia11%2Findex"));
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

    public void loadAccounts() {
        if (!loadingAccounts) {
            ObservableList<String> battleTags = FXCollections.observableArrayList();
            ObservableList<String> emails = FXCollections.observableArrayList();
            String sql = null;
//            sql = "SELECT * FROM accounts";
//            try {
//                preparedStatement = con.prepareStatement(sql);
//                resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    battleTags.add(resultSet.getString("BattleTag"));
//                    if(resultSet.getString("Email")!=null){
//                        emails.add(resultSet.getString("Email"));
//                    }else{
//                        emails.add("");
//                    }
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
            expectedThreadCount = battleTags.size();
            threadCount = 0;
            loadingAccounts = true;
            rows.clear();
            for (int i = 0; i < battleTags.size(); i++)
                addAccountRow(battleTags.get(i), emails.get(i));
        }
    }

    public void addAccount() {
        if (!loadingAccounts) {
            TextInputDialog td = new TextInputDialog();
            td.setTitle("Add battletag");
            td.setContentText("Enter in the account battletag here");
            td.setHeaderText(null);
            Optional<String> result = td.showAndWait();
            String battleTag = td.getEditor().getText();
            if (result.isPresent()) {
                td = new TextInputDialog();
                td.setTitle("Add Email");
                td.setContentText("Enter in the email for the account: " + battleTag);
                td.setHeaderText(null);
                result = td.showAndWait();
                if (result.isPresent()) {
                    String email = td.getEditor().getText();
//                    String sql = "INSERT INTO accounts(BattleTag,Email) VALUES(?,?)";
//                    try {
//                        preparedStatement = con.prepareStatement(sql);
//                        preparedStatement.setString(1, battleTag);
//                        preparedStatement.setString(2, email);
//                        preparedStatement.executeUpdate();
//                    } catch (SQLException ex) {
//                        System.err.println(ex.getMessage());
//                    }
                    expectedThreadCount = 1;
                    threadCount = 0;
                    loadingAccounts = true;
                    addAccountRow(battleTag, email);
                }
            }
        }
    }

    public void addAccountRow(String battleTag, String email) {
//            progressIndicator.setVisible(true);
        MainMenuController me = this;
        Task<HBox> task = new Task<HBox>() {
            @Override
            protected HBox call() throws Exception {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FXML/AccountRow.fxml"));
                HBox accountRow = null;
                try {
                    accountRow = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AccountRowController sc = loader.getController();
                sc.setMain(main);
                sc.setAccount(battleTag);
                sc.setEmail(email);
                sc.setParent(me);
                sc.fill();
                return accountRow;
            }
        };
//            progressIndicator.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(evt -> {
            if (threadCount < expectedThreadCount - 1) {
                rows.add(task.getValue());
                threadCount++;
            } else {
                rows.add(task.getValue());
//                HBoxComparator comparator = new HBoxComparator();
//                rows.sort(comparator);
                accountRowContainer.getChildren().clear();
                accountRowContainer.getChildren().setAll(rows);
                loadingAccounts = false;
            }
        });
        new Thread(task).start();
    }
}

class FilterItem {
    private String itemText;
    private int filterValue;

    FilterItem(String itemText, int filterValue) {
        this.itemText = itemText;
        this.filterValue = filterValue;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public int getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(int filterValue) {
        this.filterValue = filterValue;
    }

    @Override
    public String toString() {
        return itemText;
    }

}

