package platform.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.persistence.RepositoryService;
import platform.entries.CodeSnippet;

import java.util.Map;
import java.util.Optional;

@Controller
public class ApiController {
    private final RepositoryService service;

    @Autowired
    public ApiController(RepositoryService service) {
        this.service = service;
    }

    @GetMapping(value = "/api/code/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CodeSnippet snippetByUUID(@PathVariable String uuid) {
        Optional<CodeSnippet> snippetOptional = service.findByID(uuid);
        if (snippetOptional.isPresent()) {
            CodeSnippet snippet = snippetOptional.get();
            if (snippet.checkIsAvailable()) {
                snippet.view();
                service.save(snippet);
                return snippet;
            } else {
                service.delete(snippet);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/code/all")
    @ResponseBody
    public Iterable<CodeSnippet> allSnippets() {
        return service.findAll();
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addSnippet(@RequestBody Map<String, Object> map) {
        CodeSnippet snippet = new CodeSnippet(getStringByKey(map, "code"));
        snippet.setViewsLimit(getIntegerOrNullByKey(map, "viewsLimit"));
        Integer minutesLimit = getIntegerOrNullByKey(map, "minutesLimit");
        if (minutesLimit != null) {
            snippet.setDeleteDate(snippet.getCreateDate().plusMinutes(minutesLimit));
        }
        service.save(snippet);
        return snippet.getSnippetUUID();
    }

    private String getStringByKey(@NotNull Map<String, Object> map, @NotNull String key) {
        Object value = map.get(key);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value can't be empty");
        }
        if (value instanceof String) {
            return (String) value;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " value should be a String");
    }

    private Integer getIntegerOrNullByKey(@NotNull Map<String, Object> map, @NotNull String key) {
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
