package edu.obya.blueprint.common.util.search;

import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value(staticConstructor = "of")
public class FindCriteria {
    String key;
    String operation;
    Object value;

    public static List<FindCriteria> empty() {
        return Collections.emptyList();
    }

    public static List<FindCriteria> from(String filter) {
        List<FindCriteria> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(filter + ",");
        while (matcher.find()) {
            result.add(of(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return result;
    }
}
