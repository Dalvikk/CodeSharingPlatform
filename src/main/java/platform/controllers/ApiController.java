package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.entries.CodeSnippet;
import platform.persistence.RepositoryService;

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
        CodeSnippet snippet = ApiUtils.parseSnippetFromJSON(map);
        service.save(snippet);
        return snippet.getSnippetUUID();
    }
}
