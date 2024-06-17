package funnydog.mailadmin.aliases;

import java.util.Collection;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAliasRepository implements AliasRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int create(Alias alias) {
		ZonedDateTime now = ZonedDateTime.now();
		alias.setCreated(now);
		alias.setModified(now);
		return jdbcTemplate.update(
			"INSERT INTO alias(domain_id,destination,redirect,active,created,modified)"
			+ " VALUES(?, ?, ?, ?, ?, ?)",
			alias.getDomainId(),
			alias.getDestination(),
			alias.getRedirect(),
			alias.getActive(),
			alias.getCreated(),
			alias.getModified());
	}

	@Override
	public int update(Alias alias) {
		alias.setModified(ZonedDateTime.now());
		return jdbcTemplate.update(
			"UPDATE alias SET domain_id=?, destination=?, redirect=?, active=?, created=?,"
			+ " modified=? WHERE id=?",
			alias.getDomainId(),
			alias.getDestination(),
			alias.getRedirect(),
			alias.getActive(),
			alias.getCreated(),
			alias.getModified(),
			alias.getId());
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update("DELETE FROM alias WHERE id=?", id);
	}

	@Override
	public Alias findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * from alias WHERE id=?",
				BeanPropertyRowMapper.newInstance(Alias.class), id);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public Collection<Alias> findByDomainId(Long domainId) {
		return jdbcTemplate.query(
			"SELECT * FROM alias WHERE domain_id=? ORDER BY destination",
			BeanPropertyRowMapper.newInstance(Alias.class), domainId);
	}
}
