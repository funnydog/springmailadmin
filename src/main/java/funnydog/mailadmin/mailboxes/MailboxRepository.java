package funnydog.mailadmin.mailboxes;

import java.util.Collection;

public interface MailboxRepository {
	int create(Mailbox mailbox);
	int update(Mailbox mailbox);
	int deleteById(Long id);

	Mailbox findById(Long id);
	Collection<Mailbox> findByDomainId(Long domainId);
}
