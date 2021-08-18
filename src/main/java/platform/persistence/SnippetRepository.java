package platform.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entries.CodeSnippet;

@Repository
public interface SnippetRepository extends CrudRepository<CodeSnippet, String> {
}
