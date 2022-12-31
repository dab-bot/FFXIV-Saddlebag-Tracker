package Models;

public class FilterItem {
    private String itemText;
    private int filterValue;

    public FilterItem(String itemText, int filterValue) {
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
