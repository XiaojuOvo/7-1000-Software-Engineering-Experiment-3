import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WeatherApiManager {

    // 接收 Location、timestamp 和 weatherType（查询类型）
    public Object getWeatherData(Location location, long timestamp, String weatherType) {
        switch (weatherType) {
            case "Current Weather":
                return getCurrentWeather(location, timestamp);
            case "Weather in 3 Hours":
                return getWeatherIn3Hours(location, timestamp);
            case "7 Day Forecast":
                return getSevenDayForecast(location, timestamp);  // 返回七天的天气数据
            default:
                return null;
        }
    }

    // 获取当前天气数据
    private WeatherData getCurrentWeather(Location location, long timestamp) {
        return generateRandomWeatherData(location, timestamp);
    }

    // 获取三小时后的天气数据
    private WeatherData getWeatherIn3Hours(Location location, long timestamp) {
        long threeHoursLater = timestamp + 3 * 60 * 60;  // 三小时后的时间戳
        return generateRandomWeatherData(location, threeHoursLater);
    }

    // 获取七天天气预报数据
    private List<WeatherData> getSevenDayForecast(Location location, long timestamp) {
        List<WeatherData> forecast = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            long dayTimestamp = timestamp + (i * 24 * 60 * 60);  // 每天的时间戳
            WeatherData weatherData = generateRandomWeatherData(location, dayTimestamp);
            forecast.add(weatherData);  // 添加每天的天气数据
        }
        return forecast;  // 返回一个包含七天天气数据的列表
    }

    // 生成模拟的天气数据
    private WeatherData generateRandomWeatherData(Location location, long timestamp) {
        Random random = new Random();

        double temperature = -10 + (40 * random.nextDouble());  // 温度范围 -10 到 30 度
        double humidity = 30 + (70 * random.nextDouble());      // 湿度范围 30% 到 100%
        String windDirection = random.nextBoolean() ? "North" : "South";  // 随机风向
        double windSpeed = 0 + (30 * random.nextDouble());      // 风速范围 0 到 30 km/h
        double pollutionLevel = 0 + (500 * random.nextDouble()); // 污染水平范围 0 到 500
        double visibility = 1 + (10 * random.nextDouble());     // 可见度范围 1 到 10 km
        
        Date date = new Date(timestamp * 1000);  // 假设传入的是秒级时间戳

        return new WeatherData(temperature, humidity, windDirection, windSpeed, pollutionLevel, visibility, date);
    }

    private WeatherData generateRealWeatherData(Location location,long timestamp){
        //ToDo
        return new WeatherData(0.0,0.0,"a",0.0,0.0,0.0,new Date(timestamp * 1000));
    }
}

