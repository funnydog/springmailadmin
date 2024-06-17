package funnydog.mailadmin.domains;

import java.util.Collection;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDomainRepository implements DomainRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int create(Domain domain) {
		ZonedDateTime now = ZonedDateTime.now();
		domain.setCreated(now);
		domain.setModified(now);
		return jdbcTemplate.update(
			"INSERT INTO domain(name,description,active,backup,created,modified) " +
			"VALUES(?, ?, ?, ?, ?, ?)",
			domain.getName(),
			domain.getDescription(),
			domain.getActive(),
			domain.getBackup(),
			domain.getCreated(),
			domain.getModified());
	}

	@Override
	public int update(Domain domain) {
		domain.setModified(ZonedDateTime.now());
		return jdbcTemplate.update(
			"UPDATE domain SET name = ?, description = ?, active = ?, backup = ?, modified = ? " +
			"WHERE id = ?",
			domain.getName(),
			domain.getDescription(),
			domain.getActive(),
			domain.getBackup(),
			domain.getModified(),
			domain.getId());
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update("DELETE FROM domain WHERE id = ?", id);
	}

	@Override
	public Domain findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * FROM domain WHERE id = ?",
				BeanPropertyRowMapper.newInstance(Domain.class), id);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public Collection<Domain> findAll() {
		return jdbcTemplate.query(
			"SELECT * FROM domain ORDER BY name",
			BeanPropertyRowMapper.newInstance(Domain.class));
	}
}
