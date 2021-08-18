package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.entries.CodeSnippet;

@Controller
public class HTMLController {

    private final ApiController apiController;

    @Autowired
    public HTMLController(ApiController apiController) {
        this.apiController = apiController;
    }

    @GetMapping(value = "/code/{uuid}")
    private String pageWithSnippetByUUID(@PathVariable String uuid, Model model) {
        CodeSnippet snippet = apiController.snippetByUUID(uuid);
        model.addAttribute("snippet", snippet);
        return "viewCodeSnippet";
    }

    @GetMapping(value = "/code/new")
    private String submitCodeSnippet() {
        return "submitCodeSnippet";
    }


    @GetMapping(value = "/code/all")
    private String viewLatestCodeSnippets(Model model) {
        model.addAttribute("snippets", apiController.allSnippets());
        return "viewCodeSnippets";
    }
}
