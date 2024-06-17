package funnydog.mailadmin.domains;

import java.util.Collection;

public interface DomainRepository {
	int create(Domain domain);
	int update(Domain domain);
	int deleteById(Long id);

	Domain findById(Long id);

	Collection<Domain> findAll();
}
