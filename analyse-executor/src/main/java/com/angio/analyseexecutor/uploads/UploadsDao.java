package com.angio.analyseexecutor.uploads;

import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
import com.angio.analyseexecutor.uploads.type.FileType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@AllArgsConstructor
@Repository
public class UploadsDao {

    private final static String INSERT_UPLOAD_SQL = "insert into uploads (type, uri) values (?, ?)";

    private final DataSource ds;

    public void insertImageInfo(StaticFileDto upload) throws SQLException {
        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_UPLOAD_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, FileType.IMAGE.ordinal());
            ps.setString(2, upload.getFilename());
            ps.addBatch();

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                upload.setId(rs.getLong(1));
            }
        }
    }

    public void insertImageInfo(List<StaticFileDto> uploads) throws SQLException {
        try(Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_UPLOAD_SQL, Statement.RETURN_GENERATED_KEYS)) {

            for (StaticFileDto upload : uploads) {
                ps.setInt(1, FileType.IMAGE.ordinal());
                ps.setString(2, upload.getFilename());
                ps.addBatch();
            }

            ps.executeBatch();
            ResultSet rs = ps.getGeneratedKeys();

            for (StaticFileDto upload : uploads) {
                if (rs.next()) {
                    upload.setId(rs.getLong(1));
                }
            }
        }
    }
}
