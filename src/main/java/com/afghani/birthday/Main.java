package com.afghani.birthday;

import java.util.List;
import java.util.Map;

import com.afghani.birthday.model.Person;
import com.afghani.birthday.service.BirthdayExcelWriter;
import com.afghani.birthday.service.BirthdayOrganizer;
import com.afghani.birthday.service.ExcelReader;

public class Main {

    private static final String INPUT_FILE = "";
    private static final String OUTPUT_FILE = "";

    public static void main(String[] args) throws Exception {

        ExcelReader reader = new ExcelReader();
        List<Person> people = reader.read(INPUT_FILE);

        BirthdayOrganizer organizer = new BirthdayOrganizer();
        Map<Integer, Map<Integer, List<Person>>> birthdays = organizer.organize(people);

        BirthdayExcelWriter writer = new BirthdayExcelWriter();
        writer.write(birthdays, OUTPUT_FILE);

        System.out.println("File berhasil dibuat.");
    }
}