package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirMeasureDto {
    private Double co;
    private Double no;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double pm2_5;
    private Double pm10;
    private Double nh3;

    public AirMeasureDto() {
    }

    /**
     * Getter
     *
     * @return co
     */
    public Double getCo() {
        return co;
    }

    /**
     * Setter
     *
     * @param co sets value
     */
    public void setCo(Double co) {
        this.co = co;
    }

    /**
     * Getter
     *
     * @return no
     */
    public Double getNo() {
        return no;
    }

    /**
     * Setter
     *
     * @param no sets value
     */
    public void setNo(Double no) {
        this.no = no;
    }

    /**
     * Getter
     *
     * @return no2
     */
    public Double getNo2() {
        return no2;
    }

    /**
     * Setter
     *
     * @param no2 sets value
     */
    public void setNo2(Double no2) {
        this.no2 = no2;
    }

    /**
     * Getter
     *
     * @return o3
     */
    public Double getO3() {
        return o3;
    }

    /**
     * Setter
     *
     * @param o3 sets value
     */
    public void setO3(Double o3) {
        this.o3 = o3;
    }

    /**
     * Getter
     *
     * @return so2
     */
    public Double getSo2() {
        return so2;
    }

    /**
     * Setter
     *
     * @param so2 sets value
     */
    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    /**
     * Getter
     *
     * @return pm2_5
     */
    public Double getPm2_5() {
        return pm2_5;
    }

    /**
     * Setter
     *
     * @param pm2_5 sets value
     */
    public void setPm2_5(Double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    /**
     * Getter
     *
     * @return pm10
     */
    public Double getPm10() {
        return pm10;
    }

    /**
     * Setter
     *
     * @param pm10 sets value
     */
    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    /**
     * Getter
     *
     * @return nh3
     */
    public Double getNh3() {
        return nh3;
    }

    /**
     * Setter
     *
     * @param nh3 sets value
     */
    public void setNh3(Double nh3) {
        this.nh3 = nh3;
    }
}
