package com.syncplus.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityWeather implements Parcelable {
    @SerializedName("weather")
    public ArrayList<Weather> weather;
    @SerializedName("base")
    public String base;
    @SerializedName("main")
    public Main main;
    @SerializedName("visibility")
    public int visibility;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("dt")
    public int dt;
    @SerializedName("timezone")
    public int timezone;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public int cod;

    public CityWeather(ArrayList<Weather> weather, String base, Main main, int visibility, Wind wind, int dt, int timezone, int id, String name, int cod) {
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.dt = dt;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public CityWeather(Parcel in) {
        base = in.readString();
        visibility = in.readInt();
        dt = in.readInt();
        timezone = in.readInt();
        id = in.readInt();
        name = in.readString();
        cod = in.readInt();
    }

    public static final Creator<CityWeather> CREATOR = new Creator<CityWeather>() {
        @Override
        public CityWeather createFromParcel(Parcel in) {
            return new CityWeather(in);
        }

        @Override
        public CityWeather[] newArray(int size) {
            return new CityWeather[size];
        }
    };

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(base);
        dest.writeInt(visibility);
        dest.writeInt(dt);
        dest.writeInt(timezone);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cod);
    }
}
