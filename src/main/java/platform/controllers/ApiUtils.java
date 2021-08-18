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
        return getClassOrNullOrThrowByKey(map, key, String.class, false);
    }

    private static String getStringOrNullByKey(@NotNull Map<String, Object> map, @NotNull String key) {
        return getClassOrNullOrThrowByKey(map, key, String.class, true);
    }

    private static Integer getIntegerOrNullByKey(@NotNull Map<String, Object> map, @NotNull String key) {
        return getClassOrNullOrThrowByKey(map, key, Integer.class, true);
    }

    private static <T> T getClassOrNullOrThrowByKey(@NotNull Map<String, Object> map, @NotNull String key,
                                                    Class<T> type, boolean nullable) {
        Object value = map.get(key);
        if (value == null) {
            if (nullable) {
                return null;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value can't be empty");
            }
        }
        if (value.getClass() == type) {
            return type.cast(value);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value should be " + type.toString());
    }
}
