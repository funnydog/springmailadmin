package funnydog.mailadmin.aliases;

import java.util.Collection;

public interface AliasRepository {
	int create(Alias alias);
	int update(Alias alias);
	int deleteById(Long id);

	Alias findById(Long id);
	Collection<Alias> findByDomainId(Long domainId);
}
