import java.util.Date;

public class WeatherData {
    
    private double temperature;
    private double humidity;
    private String windDirection;
    private double windSpeed;
    private double pollutionLevel;
    private double visibility;
    private Date timestamp;

    // 构造方法
    public WeatherData(double temperature, double humidity, String windDirection, 
                       double windSpeed, double pollutionLevel, double visibility, Date timestamp) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.pollutionLevel = pollutionLevel;
        this.visibility = visibility;
        this.timestamp = timestamp;
        
    }

    // Getter 和 Setter 方法
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPollutionLevel() {
        return pollutionLevel;
    }

    public void setPollutionLevel(double pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}