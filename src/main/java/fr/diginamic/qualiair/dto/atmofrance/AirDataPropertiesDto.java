package fr.diginamic.qualiair.dto.atmofrance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO réponse atmo-france
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirDataPropertiesDto {
    @JsonProperty("date_maj")
    private String dateMaj;

    @JsonProperty("code_no2")
    private String codeNo2;

    @JsonProperty("code_o3")
    private String codeO3;

    @JsonProperty("code_pm10")
    private String codePm10;

    @JsonProperty("code_pm25")
    private String codePm25;

    @JsonProperty("code_qual")
    private String codeQual;

    @JsonProperty("code_so2")
    private String codeSo2;

    @JsonProperty("lib_zone")
    private String libZone;

    @JsonProperty("x_wgs84")
    private String xWgs84;

    @JsonProperty("y_wgs84")
    private String yWgs84;

    @JsonProperty("date_ech")
    private String dateEch;


    public AirDataPropertiesDto() {
    }

    /**
     * Getter
     *
     * @return dateMaj
     */
    public String getDateMaj() {
        return dateMaj;
    }

    /**
     * Setter
     *
     * @param dateMaj sets value
     */
    public void setDateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
    }

    /**
     * Getter
     *
     * @return codeNo2
     */
    public String getCodeNo2() {
        return codeNo2;
    }

    /**
     * Setter
     *
     * @param codeNo2 sets value
     */
    public void setCodeNo2(String codeNo2) {
        this.codeNo2 = codeNo2;
    }

    /**
     * Getter
     *
     * @return codeO3
     */
    public String getCodeO3() {
        return codeO3;
    }

    /**
     * Setter
     *
     * @param codeO3 sets value
     */
    public void setCodeO3(String codeO3) {
        this.codeO3 = codeO3;
    }

    /**
     * Getter
     *
     * @return codePm10
     */
    public String getCodePm10() {
        return codePm10;
    }

    /**
     * Setter
     *
     * @param codePm10 sets value
     */
    public void setCodePm10(String codePm10) {
        this.codePm10 = codePm10;
    }

    /**
     * Getter
     *
     * @return codePm25
     */
    public String getCodePm25() {
        return codePm25;
    }

    /**
     * Setter
     *
     * @param codePm25 sets value
     */
    public void setCodePm25(String codePm25) {
        this.codePm25 = codePm25;
    }

    /**
     * Getter
     *
     * @return codeQual
     */
    public String getCodeQual() {
        return codeQual;
    }

    /**
     * Setter
     *
     * @param codeQual sets value
     */
    public void setCodeQual(String codeQual) {
        this.codeQual = codeQual;
    }

    /**
     * Getter
     *
     * @return codeSo2
     */
    public String getCodeSo2() {
        return codeSo2;
    }

    /**
     * Setter
     *
     * @param codeSo2 sets value
     */
    public void setCodeSo2(String codeSo2) {
        this.codeSo2 = codeSo2;
    }

    /**
     * Getter
     *
     * @return libZone
     */
    public String getLibZone() {
        return libZone;
    }

    /**
     * Setter
     *
     * @param libZone sets value
     */
    public void setLibZone(String libZone) {
        this.libZone = libZone;
    }

    /**
     * Getter
     *
     * @return xWgs84
     */
    public String getxWgs84() {
        return xWgs84;
    }

    /**
     * Setter
     *
     * @param xWgs84 sets value
     */
    public void setxWgs84(String xWgs84) {
        this.xWgs84 = xWgs84;
    }

    /**
     * Getter
     *
     * @return yWgs84
     */
    public String getyWgs84() {
        return yWgs84;
    }

    /**
     * Setter
     *
     * @param yWgs84 sets value
     */
    public void setyWgs84(String yWgs84) {
        this.yWgs84 = yWgs84;
    }

    /**
     * Getter
     *
     * @return dateEch
     */
    public String getDateEch() {
        return dateEch;
    }

    /**
     * Setter
     *
     * @param dateEch sets value
     */
    public void setDateEch(String dateEch) {
        this.dateEch = dateEch;
    }
}
