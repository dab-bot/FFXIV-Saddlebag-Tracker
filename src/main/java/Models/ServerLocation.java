package Models;

public class ServerLocation {
    private String serverName;
    private String cookieValue;

    public ServerLocation(String serverName, String cookieValue) {
        this.serverName = serverName;
        this.cookieValue = cookieValue;
    }
    public String getServerName() {return serverName;}
    public void setServerName(String serverName) {this.serverName = serverName;}
    public String getCookieValue() {return cookieValue;}
    public void setCookieValue(String cookieValue) {this.cookieValue = cookieValue;}
    @Override
    public String toString() {
        return serverName;
    }

}
