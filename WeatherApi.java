
public interface WeatherApi {
    /**
     * 获取天气数据
     * @param location 城市信息
     * @param timestamp 时间戳
     * @param weatherType 天气查询类型（当前天气、三小时后、七天预报）
     * @return 获取到的天气数据
     */
    Object getWeatherData(Location location, long timestamp, String weatherType);
}