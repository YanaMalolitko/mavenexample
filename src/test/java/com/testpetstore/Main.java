package com.testpetstore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.transform.Result;
import java.io.*;
import java.util.Arrays;

/**
 * Created by Yana on 8/24/2018.
 */
public class Main {


    public static void toJson() {

        Pet pet1 = new Pet();
        pet1.setId(1);
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Cat");
        Tag tag1 = new Tag();
        tag1.setId(1);
        Result r = new Result();
        r.setSystemId(Arrays.asList(t1, t2));
// used to pretty print result
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String strJson = gson.toJson(r);
        FileWriter writer = null;
        try {
            writer = new FileWriter("gen.json");
            writer.write(strJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    public static void toObject () {

        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("data.json"));
            Result result = gson.fromJson(br, Result.class);
            if (result != null) {
                for (Pet t : result.getPet()) {
                    System.out.println(t.getId() + " - " + t.getTitle() + " - " + t.getCompleted());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


}
