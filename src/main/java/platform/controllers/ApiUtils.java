package platform.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import platform.entries.CodeSnippet;

import java.util.Map;

public class ApiUtils {

    public static CodeSnippet parseSnippetFromJSON(@NotNull Map<String, Object> map) {
        CodeSnippet snippet = new CodeSnippet(getStringByKey(map, "code"));
        snippet.setViewsLimit(getIntegerOrNullByKey(map, "viewsLimit"));
        Integer minutesLimit = getIntegerOrNullByKey(map, "minutesLimit");
        String header = getStringOrNullByKey(map, "header");
        if (minutesLimit != null) {
            snippet.setDeleteDate(snippet.getCreateDate().plusMinutes(minutesLimit));
        }
        if (header != null) {
            snippet.setHeader(header);
        }
        return snippet;
    }

    private static String getStringByKey(@NotNull Map<String, Object> map, @NotNull String key) {
        Object value = map.get(key);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value can't be empty");
        }
        if (value instanceof String) {
            return (String) value;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value should be a String");
    }

    private static String getStringOrNullByKey(@NotNull Map<String, Object> map, @NotNull String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value should be a String");
    }

    private static Integer getIntegerOrNullByKey(@NotNull Map<String, Object> map, @NotNull String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value should be an Integer");
    }
}
