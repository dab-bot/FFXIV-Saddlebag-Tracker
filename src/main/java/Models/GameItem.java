package Models;

import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class GameItem {
    private double roi;
    private double avg_ppu;
    private double home_Server_price;
    private String home_update_time;
    private String item_id;
    private String npc_vendor_info;
    private double ppu;
    private double profit_amount;
    private double profit_raw_percent;
    private String real_name;
    private String sale_rates;
    private String server;
    private double stack_size;
    private String updateTime;
    private String url;

    public GameItem(double roi, double avg_ppu, double home_Server_price, String home_update_time, String item_id, String npc_vendor_info, double ppu, double profit_amount, double profit_raw_percent, String real_name, String sale_rates, String server, double stack_size, String updateTime, String url) {
        this.roi = roi;
        this.avg_ppu = avg_ppu;
        this.home_Server_price = home_Server_price;
        this.home_update_time = home_update_time;
        this.item_id = item_id;
        this.npc_vendor_info = npc_vendor_info;
        this.ppu = ppu;
        this.profit_amount = profit_amount;
        this.profit_raw_percent = profit_raw_percent;
        this.real_name = real_name;
        this.sale_rates = sale_rates;
        this.server = server;
        this.stack_size = stack_size;
        this.updateTime = updateTime;
        this.url = url;
    }

    public GameItem(JSONObject json){
        this.roi = json.getDouble("ROI");
        this.avg_ppu = json.getDouble("avg_ppu");
        this.home_Server_price = json.getDouble("home_server_price");
        this.home_update_time = json.getString("home_update_time");
        this.item_id = json.getString("item_id");
        this.npc_vendor_info = json.getString("npc_vendor_info");
        this.ppu = json.getDouble("ppu");
        this.profit_amount = json.getDouble("profit_amount");
        this.profit_raw_percent = json.getDouble("profit_raw_percent");
        this.real_name = json.getString("real_name");
        this.sale_rates = json.getString("sale_rates");
        this.server = json.getString("server");
        this.stack_size = json.getDouble("stack_size");
        this.updateTime = json.getString("update_time");
        this.url = json.getString("url");
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getAvg_ppu() {
        return avg_ppu;
    }

    public void setAvg_ppu(double avg_ppu) {
        this.avg_ppu = avg_ppu;
    }

    public double getHome_Server_price() {
        return home_Server_price;
    }

    public void setHome_Server_price(double home_Server_price) {
        this.home_Server_price = home_Server_price;
    }

    public String getHome_update_time() {
        return home_update_time;
    }

    public String getHome_update_timeString(){
        Date d = new Date(Long.parseLong(home_update_time));
        PrettyTime p = new PrettyTime();
        return p.format(d);
    }

    public void setHome_update_time(String home_update_time) {
        this.home_update_time = home_update_time;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getNpc_vendor_info() {
        return npc_vendor_info;
    }

    public void setNpc_vendor_info(String npc_vendor_info) {
        this.npc_vendor_info = npc_vendor_info;
    }

    public double getPpu() {
        return ppu;
    }

    public void setPpu(double ppu) {
        this.ppu = ppu;
    }

    public double getProfit_amount() {
        return profit_amount;
    }

    public void setProfit_amount(double profit_amount) {
        this.profit_amount = profit_amount;
    }

    public double getProfit_raw_percent() {
        return profit_raw_percent;
    }

    public void setProfit_raw_percent(double profit_raw_percent) {
        this.profit_raw_percent = profit_raw_percent;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getSale_rates() {
        return sale_rates;
    }

    public void setSale_rates(String sale_rates) {
        this.sale_rates = sale_rates;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public double getStack_size() {
        return stack_size;
    }

    public void setStack_size(double stack_size) {
        this.stack_size = stack_size;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getUpdateTimeString(){
        Date d = new Date(Long.parseLong(updateTime));
        PrettyTime p = new PrettyTime();
        return p.format(d);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
