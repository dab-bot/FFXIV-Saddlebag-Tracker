package utils;

import Models.GameItem;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import Models.FilterItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchHandler {

    public static ObservableList<GameItem> search(int scanHours, int saleAmount, int roi, int minStackSize, double minProfitAmount, double avgPricePerUnit, List<FilterItem> itemFilters, boolean enableHQ, boolean regionWide, boolean includeVendor, boolean includeOOS, String apiEndpoint, String cookieValue) throws UnirestException {

        String filters = "";
        for(FilterItem f: itemFilters){
            filters += f.getFilterValue();
            filters += ",";
        }
        StringUtils.chop(filters);

        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", cookieValue);

        Map<String, Object> fields = new HashMap<>();
        fields.put("scan_hours", scanHours);
        fields.put("sale_amount", saleAmount);
        fields.put("roi", roi);
        fields.put("minimum_stack_size", minStackSize);
        fields.put("minimum_profit_amount", minProfitAmount);
        fields.put("price_per_unit", avgPricePerUnit);
        fields.put("filters", filters);
        if(enableHQ)
            fields.put("hq_only", "on");
        if(regionWide)
            fields.put("region_wide", "on");
        if(includeVendor)
            fields.put("include_vendor", "on");
        if(includeOOS)
            fields.put("out_of_stock", "on");

        HttpResponse<JsonNode> response;
        response = Unirest.post(apiEndpoint)
                .headers(headers)
                .fields(fields)
                .asJson();
        JSONArray results = response.getBody().getObject().getJSONArray("data");
        ObservableList<GameItem> items = FXCollections.observableArrayList();
        for(Object o: results){
            JSONObject json = (JSONObject) o;
            GameItem g = new GameItem(json);
            items.add(g);
        }
        return items;
    }
}
