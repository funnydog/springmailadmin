package funnydog.mailadmin.mailboxes;

import java.util.Collection;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMailboxRepository implements MailboxRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int create(Mailbox mailbox) {
		ZonedDateTime now = ZonedDateTime.now();
		mailbox.setCreated(now);
		mailbox.setModified(now);
		return jdbcTemplate.update(
			"INSERT INTO mailbox(domain_id,email,password,active,created,modified) "
			+ "VALUES(?,?,?,?,?,?)",
			mailbox.getDomainId(),
			mailbox.getEmail(),
			mailbox.getPassword(),
			mailbox.getActive(),
			mailbox.getCreated(),
			mailbox.getModified());
	}

	@Override
	public int update(Mailbox mailbox) {
		mailbox.setModified(ZonedDateTime.now());
		return jdbcTemplate.update(
			"UPDATE mailbox SET domain_id=?, email=?, password=?, active=?, created=?, modified=?"
			+ " WHERE id=?",
			mailbox.getDomainId(),
			mailbox.getEmail(),
			mailbox.getPassword(),
			mailbox.getActive(),
			mailbox.getCreated(),
			mailbox.getModified(),
			mailbox.getId());
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update("DELETE FROM mailbox WHERE id=?", id);
	}

	@Override
	public Mailbox findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * FROM mailbox WHERE id=?",
				BeanPropertyRowMapper.newInstance(Mailbox.class), id);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public Collection<Mailbox> findByDomainId(Long domainId) {
		return jdbcTemplate.query(
			"SELECT * FROM mailbox WHERE domain_id=? ORDER BY email",
			BeanPropertyRowMapper.newInstance(Mailbox.class), domainId);
	}
}
