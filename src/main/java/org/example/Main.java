package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void validatePhoneNumber(File file){
        if(file.exists()) {
            Pattern pattern = Pattern.compile("^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$|^\\d{3}-\\d{3}-\\d{4}$");

            try(FileInputStream fis = new FileInputStream(file);
                Scanner scanner = new Scanner(fis)) {
                String phoneNumber = "";
                while(scanner.hasNextLine()) {
                    phoneNumber = scanner.nextLine();
                    Matcher matcher = pattern.matcher(phoneNumber);
                    if(matcher.find()) {
                        System.out.println(phoneNumber);
                    } else {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> convertToUsers(File file) throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(fis);
        if(scanner.next().equals("name")){
            scanner.next();
            while (scanner.hasNextLine()){
                users.add(new User(scanner.next(), Integer.parseInt(scanner.next())));
            }
        } else if(scanner.next().equals("age")){
            scanner.next();
            while (scanner.hasNextLine()){
                users.add(new User(Integer.parseInt(scanner.next()), scanner.next()));
            }
        } else{
            System.out.println("ERROR");
            return null;
        }

        return users;
    }

    public static Map<String, Integer> countFrequency(File file) throws FileNotFoundException {
        Map<String, Integer> wordCounts = new HashMap<>();
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(fis);
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (!wordCounts.containsKey(word)) {
                wordCounts.put(word, 1);
            } else {
                wordCounts.put(word, wordCounts.get(word) + 1);
            }
        }
        return wordCounts;
    }

    public static void main(String[] args) throws IOException {
//-------------------------task 1-------------------------
        File file1 = new File("task1.txt");
        validatePhoneNumber(file1);
//-------------------------task 1-------------------------


//-------------------------task 2-------------------------
        File file2 = new File("task2.txt");
        List<User> users = convertToUsers(file2);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);
        OutputStream fos = new FileOutputStream("users.json");
        fos.write(json.getBytes());
//-------------------------task 2-------------------------


//-------------------------task 3-------------------------
        File file3 = new File("task3.txt");
        Map<String, Integer> wordCounts = countFrequency(file3);

        Comparator<String> comparator = (o1, o2) -> wordCounts.get(o1).compareTo(wordCounts.get(o2));
        Map<String, Integer> sortedWordCounts = new TreeMap<>(comparator.reversed());
        sortedWordCounts.putAll(wordCounts);

        System.out.println("sortedWordCounts = " + sortedWordCounts);
//-------------------------task 3-------------------------

    }
}