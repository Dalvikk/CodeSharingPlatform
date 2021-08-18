package platform.persistence;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import platform.entries.CodeSnippet;

import java.util.Optional;

@org.springframework.stereotype.Service
public class RepositoryService {
    private final SnippetRepository snippetRepository;

    @Autowired
    public RepositoryService(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    public Optional<CodeSnippet> findByID(@NotNull String id) {
        return snippetRepository.findById(id);
    }

    public void delete(@NotNull CodeSnippet snippet) {
        snippetRepository.delete(snippet);
    }

    public void save(@NotNull CodeSnippet snippet) {
        snippetRepository.save(snippet);
    }

    public Iterable<CodeSnippet> findAll() {
        return snippetRepository.findAll();
    }
}
