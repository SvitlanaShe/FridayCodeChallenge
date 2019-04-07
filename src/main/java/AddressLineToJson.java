import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SvitlanaShe 07.04.2019
 */

public class AddressLineToJson {


    private static String addressOutputFile = "address_lines_output.txt";

    public static void main(String[] args) {

        List<String> inputs = getAddressLines();
        List<List<Pattern>> patterns = getPatterns();

        deleteOutputFileIfExists(addressOutputFile);

        /**for each address line we check matching to Patterns, if match found, we break and  next address line will be checked,
         * If match was not found, we have the message "!!! Pattern was not found "...
         */
        for (String input : inputs) {

            short countOfPatterns = 0;
            boolean matchPattern = true;

            for (List<Pattern> pat : patterns) {
                /**common pattern*/
                Pattern p = pat.get(0);
                /**street pattern*/
                Pattern street = pat.get(1);
                /**number pattern*/
                Pattern houseNumber = pat.get(2);
                /**increase variable on checking address line with next Pattern*/
                countOfPatterns++;
                /**Should be set using patterns*/
                String streetValue = "", houseNumberValue = "";

                /**Street should be found*/
                Matcher m = p.matcher(input);
                if (m.find()) {
                    System.out.println("Found input data: " + m.group() + ".");
                    m = street.matcher(input);
                    if (m.find()) {
                        /**delete already checked data*/
                        input = input.replace(m.group(), "");
                        /**should be implementing some cleaning*/
                        streetValue = m.group();
                    } else {
                        matchPattern = false;
                    }

                    /**House number should be found if street not empty*/
                    m = houseNumber.matcher(input);
                    if (m.find() && matchPattern) {
                        /**should be implementing some cleaning*/
                        houseNumberValue = m.group().replace(",", "");
                    } else {
                        matchPattern = false;
                    }
                    if (matchPattern) {
                        dataToJson(streetValue.trim(), houseNumberValue.trim());
                        System.out.println("________________________ ");
                        /**if data was sent to Json, then  stop checking this address line*/
                        break;
                    }
                }
                /**If AddressLine does not match any pattern, we will have a message with unmatched addressLine*/
                else if (countOfPatterns == patterns.size()) {
                    System.out.println("!!! Pattern was not found for " + input);
                    System.out.println("________________________ ");
                }
            }
        }
    }

    private static List<List<Pattern>> getPatterns() {
        return Arrays.asList(
                PatternsLibrary.P2.getPatternsCollection(),
                PatternsLibrary.P1.getPatternsCollection(),
                PatternsLibrary.P0.getPatternsCollection()
        );
    }

    private static List<String> getAddressLines() {
        List<String> inputs = new ArrayList<String>();
        inputs.add("Usestreet 4");
        inputs.add("Winterallee 3");
        inputs.add("Musterstrasse 45");
        inputs.add("Bläufeldweg 123B");
        inputs.add("Am Bächle 23");
        inputs.add("Auf der Vogelwiese 23 b");
        inputs.add("Calle Aduana, 29");
        inputs.add("4, rue de la revolution");
        inputs.add("200 Broadway Av");
        inputs.add("I have a dog, but I like my cat better.");
        inputs.add("Calle 39 No 1540");
        inputs.add("Calle dfs fdf 39 No njknn 1540");
        inputs.add("I have a cat, but I like my dog better.");
        return inputs;
    }

    private static void dataToJson(String street, String houseNumber) {
        AddressLine addressPojo = new AddressLine();
        addressPojo.setHouse(houseNumber);
        addressPojo.setStreet(street);
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromPojo = gsonBuilder.toJson(addressPojo);
        System.out.println(jsonFromPojo);

        appendUsingFileOutputStream(addressOutputFile, jsonFromPojo + "\n\t");

    }

    private static void appendUsingFileOutputStream(String fileName, String data) {

        OutputStream os = null;
        try {
            // below true flag tells OutputStream to append
            os = new FileOutputStream(new File(fileName), true);
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteOutputFileIfExists(String fileName) {
        try {
            Files.createFile(Paths.get(fileName));
        } catch (FileAlreadyExistsException f) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        Path fileToDeletePath = Paths.get(fileName);
        try {
            Files.delete(fileToDeletePath);
        } catch (FileAlreadyExistsException f) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}