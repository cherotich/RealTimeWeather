package com.example.realtimeweather.models;

public class wetherdata {
    Long Humidity, Temperature, A_Windsepeed, Barometric_Pressure, Rainfall_1hr, Rainfall_24hr, Wind_direction, M_Windspeed;
    String Date, Time;

    public wetherdata() {
    }

    public wetherdata(Long humidity, Long temperature, Long a_Windsepeed, Long barometric_Pressure, Long rainfall_1hr, Long rainfall_24hr, Long wind_direction, Long m_Windspeed, String date, String time) {
        Humidity = humidity;
        Temperature = temperature;
        A_Windsepeed = a_Windsepeed;
        Barometric_Pressure = barometric_Pressure;
        Rainfall_1hr = rainfall_1hr;
        Rainfall_24hr = rainfall_24hr;
        Wind_direction = wind_direction;
        M_Windspeed = m_Windspeed;
        Date = date;
        Time = time;
    }

    public Long getHumidity() {
        return Humidity;
    }

    public void setHumidity(Long humidity) {
        Humidity = humidity;
    }

    public Long getTemperature() {
        return Temperature;
    }

    public void setTemperature(Long temperature) {
        Temperature = temperature;
    }

    public Long getA_Windsepeed() {
        return A_Windsepeed;
    }

    public void setA_Windsepeed(Long a_Windsepeed) {
        A_Windsepeed = a_Windsepeed;
    }

    public Long getBarometric_Pressure() {
        return Barometric_Pressure;
    }

    public void setBarometric_Pressure(Long barometric_Pressure) {
        Barometric_Pressure = barometric_Pressure;
    }

    public Long getRainfall_1hr() {
        return Rainfall_1hr;
    }

    public void setRainfall_1hr(Long rainfall_1hr) {
        Rainfall_1hr = rainfall_1hr;
    }

    public Long getRainfall_24hr() {
        return Rainfall_24hr;
    }

    public void setRainfall_24hr(Long rainfall_24hr) {
        Rainfall_24hr = rainfall_24hr;
    }

    public Long getWind_direction() {
        return Wind_direction;
    }

    public void setWind_direction(Long wind_direction) {
        Wind_direction = wind_direction;
    }

    public Long getM_Windspeed() {
        return M_Windspeed;
    }

    public void setM_Windspeed(Long m_Windspeed) {
        M_Windspeed = m_Windspeed;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
