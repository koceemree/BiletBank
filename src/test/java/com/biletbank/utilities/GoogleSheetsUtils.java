package com.biletbank.utilities;

import io.restassured.path.json.JsonPath;
import java.util.*;
import static io.restassured.RestAssured.given;

public class GoogleSheetsUtils {

    // Kendi bilgilerinizle doldurun
    private static final String API_KEY = ConfigReader.getProperty("sheetApikey"); // Az önce aldığın API Key
    private static final String SPREADSHEET_ID = "1ksMmUm1VV0wkvlT_azisy34QRsCdazwQev1L_iXiuXY"; // Sayfa URL'indeki uzun ID
    private static final String BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets/";

    /**
     * Google Sheets'ten verileri bir Map listesi olarak çeker.
     * Sütun başlıklarını "Key", satır verilerini "Value" yapar.
     */
    public static List<Map<String, String>> getSheetDataAsMap(String sheetName) {
        String url = BASE_URL + SPREADSHEET_ID + "/values/" + sheetName + "?key=" + API_KEY;

        // RestAssured ile veriyi çekiyoruz
        JsonPath jsonPath = given().get(url).jsonPath();
        List<List<Object>> allValues = jsonPath.getList("values");

        if (allValues == null || allValues.isEmpty()) {
            throw new RuntimeException("Sayfada veri bulunamadı!");
        }

        // İlk satırı (Header) alıyoruz
        List<Object> headers = allValues.get(0);
        List<Map<String, String>> dataList = new ArrayList<>();

        // 2. satırdan itibaren (Data) eşleştirme yapıyoruz
        for (int i = 1; i < allValues.size(); i++) {
            List<Object> currentRow = allValues.get(i);
            Map<String, String> rowMap = new LinkedHashMap<>();

            for (int j = 0; j < headers.size(); j++) {
                String key = headers.get(j).toString();
                // Eğer hücre boşsa hata almamak için kontrol
                String value = (j < currentRow.size()) ? currentRow.get(j).toString() : "";
                rowMap.put(key, value);
            }
            dataList.add(rowMap);
        }
        return dataList;
    }
}