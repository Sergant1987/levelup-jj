package com.marchenko.medcards.models;

import java.util.*;

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

    private final String value;

    TimeReservation(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    private static List<TimeReservation> getAll(){
        return new ArrayList<>(Arrays.asList(TimeReservation.values()));
    }

    public static List<TimeReservation> findNotReservationTime(List<TimeReservation> reservationTime) {
        List<TimeReservation> allTime = getAll();
        allTime.removeAll(reservationTime);
        return allTime;
    }

    public static TimeReservation getByValue(String selectedValue){
        return getAll().stream().filter(v->v.value.equals(selectedValue)).findFirst()
                .orElseThrow(()->new RuntimeException(String.format("Значения %s в классе TimeReservation не существует",selectedValue)));
    }


}
