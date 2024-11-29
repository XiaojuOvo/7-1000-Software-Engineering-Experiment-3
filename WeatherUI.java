import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class WeatherUI {

    private WeatherApiManager weatherApiManager;  // 用于查询天气
    private JTextField cityInputField;  // 用户输入城市名
    private JTextArea weatherInfoArea;  // 显示天气信息
    private JButton currentWeatherButton;  // 查询当前天气按钮
    private JButton weatherIn3HoursButton;  // 查询三小时后天气按钮
    private JButton sevenDayForecastButton;  // 查询七天天气预报按钮
    private JButton removeFromFavoritesButton;
    private JButton addButton;  // 添加城市到收藏夹
    private JButton viewFavoritesButton;  // 查看收藏夹
    private JButton weatherTrendButton;  // 查看天气趋势按钮
    private JList<String> favoritesList;  // 收藏夹城市列表
    private DefaultListModel<String> listModel;  // 收藏夹城市管理

    private List<Location> savedCities;  // 收藏夹中的城市列表
    private WeatherApp app;  // 引用WeatherApp实例
    private Location selectedCity;  // 当前选中的城市

    public WeatherUI() {
        weatherApiManager = new WeatherApiManager();
        listModel = new DefaultListModel<>();
        savedCities = new ArrayList<>();
    }

    // 创建UI
    public void createUI() {
        JFrame frame = new JFrame("天气应用");
        frame.setSize(600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建自定义JPanel来显示背景图
        BackgroundPanel backgroundPanel = new BackgroundPanel("OIP.jpg"); // 假设背景图为 "OIP.jpg"
        backgroundPanel.setLayout(null);  // 使用绝对布局
        frame.add(backgroundPanel);

        // 输入框和按钮面板
        cityInputField = new JTextField(20);
        cityInputField.setBounds(120, 20, 200, 25); // 设置输入框的位置和大小
        backgroundPanel.add(cityInputField);

        currentWeatherButton = new JButton("查询当前天气");
        currentWeatherButton.setBounds(120, 60, 200, 30); // 设置按钮位置
        backgroundPanel.add(currentWeatherButton);

        weatherIn3HoursButton = new JButton("查询三小时后天气");
        weatherIn3HoursButton.setBounds(120, 100, 200, 30); // 设置按钮位置
        backgroundPanel.add(weatherIn3HoursButton);

        sevenDayForecastButton = new JButton("查询七日天气预报");
        sevenDayForecastButton.setBounds(120, 140, 200, 30); // 设置按钮位置
        backgroundPanel.add(sevenDayForecastButton);

        addButton = new JButton("加入收藏");
        addButton.setBounds(120, 180, 200, 30); // 设置按钮位置
        backgroundPanel.add(addButton);

        viewFavoritesButton = new JButton("查看收藏夹");
        viewFavoritesButton.setBounds(120, 220, 200, 30); // 设置按钮位置
        backgroundPanel.add(viewFavoritesButton);

        removeFromFavoritesButton = new JButton("移除收藏");
        removeFromFavoritesButton.setBounds(120, 260, 200, 30); // 设置按钮位置
        backgroundPanel.add(removeFromFavoritesButton);

        // 新增查看天气趋势按钮
        weatherTrendButton = new JButton("查看天气趋势");
        weatherTrendButton.setBounds(360, 260, 200, 30); // 设置按钮位置
        backgroundPanel.add(weatherTrendButton);

        // 显示天气信息的文本区域
        weatherInfoArea = new JTextArea(10, 30);
        weatherInfoArea.setEditable(false);  // 不可编辑
        weatherInfoArea.setBounds(120, 300, 400, 80); // 设置文本区域位置
        JScrollPane scrollPane = new JScrollPane(weatherInfoArea);
        scrollPane.setBounds(120, 300, 400, 200); // 设置滚动面板位置
        backgroundPanel.add(scrollPane);

        // 初始化收藏夹JList和滚动条
        favoritesList = new JList<>(listModel);
        favoritesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        favoritesList.setVisibleRowCount(5);
        JScrollPane listScroller = new JScrollPane(favoritesList);
        listScroller.setBounds(350, 20, 150, 200);  // 设置收藏夹列表位置
        backgroundPanel.add(listScroller);

        // 查询按钮事件处理
        currentWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cityInputField.getText().trim().isEmpty() && selectedCity != null) {
                    fetchWeatherForCity(selectedCity);  // 使用选中的城市进行查询
                } else {
                    fetchWeather("Current Weather");  // 使用输入框中的城市进行查询
                }
            }
        });

        weatherIn3HoursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cityInputField.getText().trim().isEmpty() && selectedCity != null) {
                    fetchWeatherForCity(selectedCity);  // 使用选中的城市进行查询
                } else {
                    fetchWeather("Weather in 3 Hours");  // 使用输入框中的城市进行查询
                }
            }
        });

        sevenDayForecastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cityInputField.getText().trim().isEmpty() && selectedCity != null) {
                    fetchWeatherForCity(selectedCity);  // 使用选中的城市进行查询
                } else {
                    fetchWeather("7 Day Forecast");  // 使用输入框中的城市进行查询
                }
            }
        });

        // 添加到收藏按钮事件处理
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = cityInputField.getText().trim();
                if (!cityName.isEmpty()) {
                    Location location = new Location(cityName, 0.0, 0.0);  // 默认经纬度
                    // 如果城市不在收藏夹中，添加城市到收藏夹
                    if (!savedCities.contains(location)) {
                        addCityToFavorites(location);
                    } else {
                        weatherInfoArea.setText(cityName + " 已在收藏夹中。");
                    }
                }
            }
        });

        // 查看收藏夹按钮事件处理
        viewFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFavoritesList();
            }
        });

        // 查看天气趋势按钮事件处理
        weatherTrendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showWeatherTrend();
            }
        });

        // 监听收藏夹城市的选择
        favoritesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCityName = favoritesList.getSelectedValue();
                if (selectedCityName != null) {
                    selectedCity = getCityByName(selectedCityName);  // 更新选中的城市
                    if (selectedCity != null) {
                        fetchWeatherForCity(selectedCity);  // 查询该城市的天气
                    }
                }
            }
        });

        removeFromFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCity != null) {
                    removeCityFromFavorites(selectedCity);  // 从收藏夹移除选中的城市
                } else {
                    weatherInfoArea.setText("请先选择一个城市从收藏夹移除。");
                }
            }
        });

        frame.setVisible(true);
    }

    // 根据查询类型获取天气
    private void fetchWeather(String weatherType) {
        String cityName = cityInputField.getText().trim();
        if (!cityName.isEmpty()) {
            Location location = new Location(cityName, 0.0, 0.0);  // 默认经纬度
            long timestamp = System.currentTimeMillis();
            Object weatherData = weatherApiManager.getWeatherData(location, timestamp, weatherType);

            if (weatherData != null) {
                displayWeatherData(weatherData, location.getCityName());  // 显示该城市的天气
            } else {
                weatherInfoArea.setText("未能获取天气数据。");
            }
        }
    }

    // 使用 Location 查询天气
    private void fetchWeatherForCity(Location location) {
        long timestamp = System.currentTimeMillis();
        WeatherData weatherData = (WeatherData) weatherApiManager.getWeatherData(location, timestamp, "Current Weather");

        if (weatherData != null) {
            displayWeatherData(weatherData, location.getCityName());  // 显示该城市的天气
        } else {
            weatherInfoArea.setText("未能获取天气数据。");
        }
    }

    // 显示天气数据
    private void displayWeatherData(Object weatherData, String cityname) {
        if (weatherData instanceof WeatherData) {
            WeatherData data = (WeatherData) weatherData;
            StringBuilder sb = new StringBuilder();
            sb.append("CityName: ").append(cityname).append("\n")  // 显示城市名称
              .append("Temperature: ").append(String.format("%.1f", data.getTemperature())).append("°C\n")
              .append("Humidity: ").append(String.format("%.1f", data.getHumidity())).append("%\n")
              .append("Wind: ").append(data.getWindDirection()).append(" at ").append(String.format("%.1f", data.getWindSpeed())).append(" km/h\n")
              .append("Pollution: ").append(String.format("%.1f", data.getPollutionLevel())).append("\n")
              .append("Visibility: ").append(String.format("%.1f", data.getVisibility())).append(" km\n")
              .append("Date: ").append(data.getTimestamp()).append("\n");
            weatherInfoArea.setText(sb.toString());
        } else if (weatherData instanceof List<?>) {
            List<WeatherData> forecast = (List<WeatherData>) weatherData;
            StringBuilder sb = new StringBuilder("7 Day Forecast:\n");
            for (WeatherData data : forecast) {
                sb.append("Date: ").append(data.getTimestamp())
                  .append(" | Temp: ").append(String.format("%.1f", data.getTemperature())).append("°C\n");
            }
            weatherInfoArea.setText(sb.toString());
        }
    }

    // 显示天气趋势数据
    private void showWeatherTrend() {
        WeatherTrend trend = new WeatherTrend();
        trend.generateRandomData();  // 生成随机天气趋势数据
        weatherInfoArea.setText(trend.displayTrend());  // 显示随机生成的天气趋势数据
    }

    // 更新收藏夹显示
    // 更新收藏夹显示
    private void showFavoritesList() {
        listModel.clear();
        for (Location location : savedCities) {
            listModel.addElement(location.getCityName());  // 更新 JList 显示
        }
    }


    // 添加城市到收藏夹
    private void addCityToFavorites(Location location) {
        if (!savedCities.contains(location)) {
            savedCities.add(location);
            weatherInfoArea.setText(location.getCityName() + " 已添加到收藏夹。");
        }
    }

    // 从收藏夹中移除城市
    // 从收藏夹中移除城市
private void removeCityFromFavorites(Location location) {
    if (savedCities.contains(location)) {
        savedCities.remove(location);
        listModel.removeElement(location.getCityName());  // 从 JList 中移除城市
        weatherInfoArea.setText(location.getCityName() + " 已从收藏夹中移除。");
    } else {
        weatherInfoArea.setText("该城市不在收藏夹中。");
    }
}

    // 根据城市名查找 Location 对象
    private Location getCityByName(String cityName) {
        for (Location location : savedCities) {
            if (location.getCityName().equalsIgnoreCase(cityName)) {
                return location;
            }
        }
        return null;
    }

    // 获取已收藏的城市列表
    public List<Location> getSavedCities() {
        return savedCities;
    }

    // 设置 WeatherApp 实例
    public void setApp(WeatherApp app) {
        this.app = app;
    }

    // 自定义面板用于设置背景图
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // 绘制所有组件
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);  // 绘制背景图
        }
    }
}
