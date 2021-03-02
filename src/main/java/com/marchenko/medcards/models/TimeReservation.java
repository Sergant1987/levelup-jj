package com.marchenko.medcards.models;

public enum TimeReservation {
    EIGHT_ZERO("8-00"),
    EIGHT_HALF("8-30"),
    NINE_ZERO("9-00"),
    NINE_HALF("9-30"),
    TEN_ZERO("10-00"),
    TEN_HALF("10-30"),
    ELEVEN_ZERO("11-00"),
    ELEVEN_HALF("11-30"),
    THIRTEEN_ZERO("13-00"),
    THIRTEEN_HALF("13-30"),
    FOURTEEN_ZERO("14-00"),
    FOURTEEN_HALF("14-30"),
    FIFTEEN_ZERO("15-00"),
    FIFTEEN_HALF("15-30"),
    SIXTEEN_ZERO("16-00"),
    SIXTEEN_HALF("16-30");

    private String value;

    TimeReservation(String value) {
        this.value = value;
    }
}
