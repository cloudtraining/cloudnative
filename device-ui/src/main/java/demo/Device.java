package demo;


import java.io.Serializable;


public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    private String identifier;
    private String name;
    private String description;
    private String imei;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (identifier != null ? !identifier.equals(device.identifier) : device.identifier != null) return false;
        if (name != null ? !name.equals(device.name) : device.name != null) return false;
        if (description != null ? !description.equals(device.description) : device.description != null) return false;
        return imei != null ? imei.equals(device.imei) : device.imei == null;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Device{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}
