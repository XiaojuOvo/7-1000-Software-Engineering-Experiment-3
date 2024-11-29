import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherTrend {

    // 存储天气趋势数据
    private List<String> trendData;

    public WeatherTrend() {
        trendData = new ArrayList<>();
    }

    // 生成随机的天气数据
    public void generateRandomData() {
        Random random = new Random();
        int numberOfDataPoints = 5;  // 生成 5 个数据点，您可以根据需要调整

        for (int i = 0; i < numberOfDataPoints; i++) {
            // 随机生成温度和湿度
            double temperature = 15 + random.nextDouble() * 15;  // 随机温度，范围 15 到 30 度
            double humidity = 40 + random.nextDouble() * 60;     // 随机湿度，范围 40% 到 100%
            
            // 随机生成时间（例如，12:00 PM、1:00 PM 等）
            String time = (12 + i) + ":00 PM";  // 每次递增 1 小时，假设从 12:00 PM 开始

            // 格式化数据并添加到趋势数据列表
            trendData.add("Time: " + time + " | Temp: " + String.format("%.1f", temperature) + "°C | Humidity: " + String.format("%.1f", humidity) + "%");
        }
    }

    public void getRealData(){
        //ToDo
    }

    // 返回天气趋势数据
    public String displayTrend() {
        StringBuilder sb = new StringBuilder();
        for (String data : trendData) {
            sb.append(data).append("\n");
        }
        return sb.toString();
    }

    
}
