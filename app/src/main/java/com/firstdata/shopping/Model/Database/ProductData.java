/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.firstdata.shopping.Model.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Class for get the input json contains the product data's.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductData {

    String productJson = "[\n" +
            "  {\n" +
            "    \"uid\": 1,\n" +
            "    \"name\": \"Microwave oven\",\n" +
            "    \"image\": \"https://images-na.ssl-images-amazon.com/images/I/618O0ywM1SL._SX569_.jpg\",\n" +
            "    \"description\": \"Its innovative technology makes wide distribution of microwaves so that food is evenly cooked. With a touch of button, it lowers Standby power consumption to save electricity.\",\n" +
            "    \"price\": 3000,\n" +
            "    \"category\": \"Electronics\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"uid\": 2,\n" +
            "    \"name\": \"Telvision\",\n" +
            "    \"image\": \"https://images-na.ssl-images-amazon.com/images/I/81UKyrqJn%2BL._SL1500_.jpg\",\n" +
            "    \"description\": \"Global top 2 Television Brand With world-class A+ grade FHD panel and cutting-edge dynamic picture enhancement technology, the TCL S62 series brings the real nature picture and provide a balanced control between brightness and darkness. Built-in stereo box speaker, dolby audio and smart sound create a rich immersive sound experience. TCL launcher and TCL app store let you enjoy all the lastest video content and downloadable applications.\",\n" +
            "    \"price\": 5000,\n" +
            "    \"category\": \"Electronics\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"uid\": 3,\n" +
            "    \"name\": \"Vaccum Cleaner\",\n" +
            "    \"image\": \"https://images-na.ssl-images-amazon.com/images/I/41VXr6i72xL.jpg\",\n" +
            "    \"description\": \"The Dyson V7 Animal cord-free vacuum is engineered for all floor types, the direct-drive cleaner head drives bristles into the carpet to remove ground-in dirt. The Dyson V7 Animal cord-free vacuum quickly transforms to a handheld for quick clean ups, spot cleaning and cleaning difficult places. It provides up to 30 minutes of powerful fade-free suction. The hygienic dirt ejector empties dust from the bin in a single action. The Dyson V7 Animal's 2 Tier Radial™ cyclones technology captures microscopic dust. The max power mode provides up to 6 minutes of higher suction for more difficult tasks. Balanced for cleaning up high, down below and anywhere in between. The convenient docking station that comes with Dyson cord-free vacuum cleaners stores and charges the machine and holds additional attachments. So it's always ready to go. Two tools in one, for simple switching between surfaces.\",\n" +
            "    \"price\": 4000,\n" +
            "    \"category\": \"Electronics\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"uid\": 4,\n" +
            "    \"name\": \"Table\",\n" +
            "    \"image\": \"https://images-na.ssl-images-amazon.com/images/I/41cqPibBomL.jpg\",\n" +
            "    \"description\": \"The polypropylene material makes the table light weight and rigid, corrosion resistant It is easy to maintain and clean and has a long life span\",\n" +
            "    \"price\": 500,\n" +
            "    \"category\": \"Furniture\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"uid\": 5,\n" +
            "    \"name\": \"Chair\",\n" +
            "    \"image\": \"https://4.imimg.com/data4/UN/SS/MY-27393808/lotus-plastic-chair-500x500.jpg\",\n" +
            "    \"description\": \"We have carved a niche amongst the most trusted names in this business, engaged in offering comprehensive range of Lotus Plastic Chair.\",\n" +
            "    \"price\": 1000,\n" +
            "    \"category\": \"Furniture\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"uid\": 6,\n" +
            "    \"name\": \"Almirah\",\n" +
            "    \"image\": \"https://5.imimg.com/data5/JK/IL/MY-16049624/modern-design-steel-almirah-500x500.jpg\",\n" +
            "    \"description\": \"Metal Wardrobe with Long lasting CRCA Steel , Compact Design , Durable, Strong and Good Looking , Ample Internal Storage , Safe and Secure\",\n" +
            "    \"price\": 1000,\n" +
            "    \"category\": \"Furniture\"\n" +
            "  }\n" +
            "]";
    List<Product> productsList = new ArrayList<>();

    public ProductData() {
        try {
            JSONArray jsonArray = new JSONArray(productJson);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                productsList.add(new Product(Long.valueOf(jsonObject.get("uid").toString()),
                        jsonObject.get("name").toString(),
                        jsonObject.get("image").toString(),
                        jsonObject.get("description").toString(),
                        Long.valueOf(jsonObject.get("price").toString()),
                        jsonObject.get("category").toString()));
            }
        } catch (JSONException e) {

        }
    }


    /**
     * Method to get the list of product from json.
     *
     * @return List<Product> list of products
     */
    public List<Product> getProductsList() {
        return productsList;
    }


    /**
     * Method to get the product by uid.
     *
     * @param uid uid of the requested product
     * @return Product returning the product
     */
    public Product getProductByUid(Long uid) {
        for (int i = 0; i < productsList.size(); i++) {
            if (uid == productsList.get(i).getUid()) {
                return productsList.get(i);
            }
        }
        return null;
    }

}
