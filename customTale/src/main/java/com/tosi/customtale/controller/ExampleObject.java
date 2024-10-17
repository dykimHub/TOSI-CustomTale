package com.tosi.customtale.controller;

public class ExampleObject {
    public static final String customRequest = """
            {
              "childName": "연아",
              "childGender": 1,
              "backGround": "하늘",
              "keyWord": [
                "고래","여행"
              ]
            }
            """;

    public static final String customResponse = """
            {
              "customTale": "생성된 커스텀 동화를 붙여넣어 주세요.",
              "customImageDallEURL": "생성된 커스텀 동화 삽화의 주소를 붙여넣어 주세요."
            }
            """;

    public static final String customSaveRequest = """
            {
              "childId": 6,
              "title": "하늘 고래",
              "customTale": "생성된 커스텀 동화를 붙여넣어 주세요.",
              "customImageDallEURL": "생성된 커스텀 동화 삽화의 주소를 붙여넣어 주세요.",
              "backGround": "하늘",
              "keyWord": [
                "고래", "여행"
              ],
              "isPublic": true
            }
            """;

}