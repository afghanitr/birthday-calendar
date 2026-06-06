package com.afghani.birthday.util;

public class NikUtil {

	public NikUtil() {
	}
	
	public static int getBirthDay(String nik) {

        int day =
                Integer.parseInt(
                        nik.substring(6, 8));

        if (day > 40) {
            day -= 40;
        }

        return day;
    }

    public static int getBirthMonth(String nik) {

        return Integer.parseInt(
                nik.substring(8, 10));
    }
}
