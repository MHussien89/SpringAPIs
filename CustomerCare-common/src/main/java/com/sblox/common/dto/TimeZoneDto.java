package com.sblox.common.dto;

/**
 *
 * @author Mostafa Hussien
 */
public class TimeZoneDto {

    private Long id;
//    private String arabicName;
    private String englishName;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getArabicName() {
//        return arabicName;
//    }
//
//    public void setArabicName(String arabicName) {
//        this.arabicName = arabicName;
//    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
