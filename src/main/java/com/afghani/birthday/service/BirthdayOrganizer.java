package com.afghani.birthday.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.afghani.birthday.model.Person;
import com.afghani.birthday.util.NikUtil;

public class BirthdayOrganizer {

    public Map<Integer, Map<Integer, List<Person>>> organize(List<Person> people) {

        Map<Integer, Map<Integer, List<Person>>> result = new HashMap<>();

        for (Person person : people) {
            int month = NikUtil.getBirthMonth(person.getNik());
            int day = NikUtil.getBirthDay(person.getNik());

            result
                .computeIfAbsent(month, k -> new HashMap<>())
                .computeIfAbsent(day, k -> new ArrayList<>())
                .add(person);
        }

        return result;
    }
}