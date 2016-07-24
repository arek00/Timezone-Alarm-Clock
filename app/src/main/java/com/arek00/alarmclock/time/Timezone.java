package com.arek00.alarmclock.time;

public enum Timezone {

    UTC(0, "London, Lisbon, Casablanca, Dakar"),
    CEST(1, "Berlin, Paris, Madrid, Rome, Warsaw, Algiers, Lagos, Kinshasa"),
    UTC2(2, "Helsinki, Kiev, Istanbul, Cairo, Johannesburg"),
    UTC3(3, "Moscow, Baghdad, Riyadh, Addis Ababa, Nairobi"),
    UTC4(4, "Tbilisi, Baku, Seychelles"),
    UTC5(5, "Yekaterinburg, Karachi, Maldives"),
    UTC6(6, "Astana, Omsk"),
    UTC7(7, "Jakarta, Bangkok, Krasnoyarsk"),
    UTC8(8, "Shanghai, Beijing, Kuala Lumpur, Perth, Manilla"),
    UTC9(9, "Tokyo, Seoul"),
    UTC10(10, "Sydney, Melbourne"),
    UTC11(11, "Vanuatu, Solomon Islands, Magadan"),
    UTC12(12, "Aukland, Fiji, Tuvalu, Kamchatka"),
    LONDON(0, "Londyn"),
    WARSAW(1, "Warszawa"),
    KIEV(2, "Kijów"),
    MOSCOW(3, "Moskwa"),
    DUBAI(4, "Dubaj"),
    ASTANA(6, "Astana"),
    BEIJING(8, "Pekin"),
    TOKYO(9, "Tokio"),
    SYDNEY(10, "Sydney"),
    LOS_ANGELES(-8, "Los Angeles"),
    DENVER(-7, "Denver"),
    CHICAGO(-6, "Chicago"),
    NEW_YORK(-5, "Nowy Jork"),
    BUENOS_AIRES(-3, "Buenos Aires");


    private String description;
    private int utcOffset;

    Timezone(int utcOffset, String description) {
        this.description = description;
        this.utcOffset = utcOffset;
    }

    public String getDescription() {
        return this.description;
    }

    public int getUTCOffset() {
        return this.utcOffset;
    }

}
