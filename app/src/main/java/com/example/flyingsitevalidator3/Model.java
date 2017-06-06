package com.example.flyingsitevalidator3;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by Brian on 04/03/2017.
 */

//This class is used to represent a users RC Model, it implements serializable so the object can be packed into an intent
public class Model implements Serializable{

    private int modelId;
    private String name;
    private String modelType;
    private double width;
    private double length;
    private String fuelType;
    private int registrationId;

    public Model() {
    }

    public Model(int modelId, String name, String modelType, double width, double length, String fuelType, int registrationId) {
        this.modelId = modelId;
        this.name = name;
        this.modelType = modelType;
        this.width = width;
        this.length = length;
        this.fuelType = fuelType;
        this.registrationId = registrationId;
    }

    Model(Intent intent)
    {
        name = intent.getStringExtra("title");
        modelType = intent.getStringExtra("modType");
        width =  intent.getDoubleExtra("width", 0.00);
        length = intent.getDoubleExtra("length", 0.00);
        fuelType = intent.getStringExtra("fuelType");
        registrationId = intent.getIntExtra("regNo", 0);
    }

    //This method is used t package all the models information into the intent variable when transferring info to another activity or class
    public static void packageIntent(Intent intent, String title,
                                     String modType, double width, double len, String fuel, int reg) {
        intent.putExtra("title", title);
        intent.putExtra("modType", modType);
        intent.putExtra("width", width);
        intent.putExtra("length", len);
        intent.putExtra("fuelType", fuel);
        intent.putExtra("regNo", reg);
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        if (modelId != model.modelId) return false;
        if (Double.compare(model.width, width) != 0) return false;
        if (Double.compare(model.length, length) != 0) return false;
        if (registrationId != model.registrationId) return false;
        if (name != null ? !name.equals(model.name) : model.name != null) return false;
        if (modelType != null ? !modelType.equals(model.modelType) : model.modelType != null)
            return false;
        return fuelType != null ? fuelType.equals(model.fuelType) : model.fuelType == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = modelId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + registrationId;
        return result;
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelId=" + modelId +
                ", name='" + name + '\'' +
                ", modelType='" + modelType + '\'' +
                ", width=" + width +
                ", length=" + length +
                ", fuelType='" + fuelType + '\'' +
                ", registrationId=" + registrationId +
                '}';
    }
}
