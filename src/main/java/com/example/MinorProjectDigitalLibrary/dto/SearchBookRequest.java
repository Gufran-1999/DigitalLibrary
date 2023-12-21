package com.example.MinorProjectDigitalLibrary.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchBookRequest {
    private String searchKey;
    private String searchValue;
    private String operator;
    private boolean available;

    private static Set<String> allowedKeys = new HashSet<>();
    private static Map<String, List<String>> allowedOperatorsMap = new HashMap<>();

    SearchBookRequest(){
        allowedKeys.addAll(Arrays.asList("name","author","genre","pages","id"));
        allowedOperatorsMap.put("name",Arrays.asList("=","like"));
        allowedOperatorsMap.put("author_name", Arrays.asList("="));
        allowedOperatorsMap.put("pages", Arrays.asList("<", "<=", ">", ">=", "="));
        allowedOperatorsMap.put("genre", Arrays.asList("="));
        allowedOperatorsMap.put("id", Arrays.asList("="));
    }

}
