package org.example;

public class VehicleData {
    private String vin;
    private String make;
    private String model;
    private String year;
    private String bodyClass;
    private String engineSize;
    private String vehicleType;

    public VehicleData() {}

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getBodyClass() { return bodyClass; }
    public void setBodyClass(String bodyClass) { this.bodyClass = bodyClass; }

    public String getEngineSize() { return engineSize; }
    public void setEngineSize(String engineSize) { this.engineSize = engineSize; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}