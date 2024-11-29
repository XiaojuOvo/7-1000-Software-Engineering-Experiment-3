public class WeatherApp {

    public static void main(String[] args) {
        // 创建WeatherUI实例
        WeatherUI ui = new WeatherUI();
        // 创建WeatherApp实例并传递给WeatherUI
        WeatherApp app = new WeatherApp();
        ui.setApp(app);  // 传递WeatherApp实例给WeatherUI
        
        // 启动WeatherUI
        ui.createUI();
    }
}
