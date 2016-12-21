package gov.va.vba.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ProSphere User on 12/21/2016.
 */
public class LongRowMapper implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getLong(1);
    }

}
