package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.User;
import com.dev10.models.mappers.DocumentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentJdbcClientRepository implements DocumentRepository{
    @Autowired
    JdbcClient client;

    private final String baseSelect = """
            SELECT * from document doc
            INNER JOIN directory dir using (directory_id)
            INNER JOIN document_type dt using (document_type_id)
            """;

    @Override
    public List<Document> getDocumentsInDirectory(int directoryId) throws DataAccessException {
        final String sql = baseSelect + """
                WHERE doc.directory_id = :directory_id;
                """;

        try{
            return client.sql(sql)
                    .param("directory_id", directoryId)
                    .query(new DocumentRowMapper())
                    .list();
        } catch(Exception e){
            throw new DataAccessException("Something went wrong fetching the documents in a subdirectory", e);
        }
    }

    @Override
    public List<Document> getDocumentsInRoot(User user) throws DataAccessException {
        final String sql = baseSelect + """
                WHERE dir.account_id = :account_id
                AND dir.parent_directory IS NULL
                """;

        try{
            return client.sql(sql)
                    .param("account_id", user.getId())
                    .query(new DocumentRowMapper())
                    .list();
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while fetching the root documents for a user", e);
        }
    }

    @Override
    public List<Document> getAllDocuments(User user) throws DataAccessException {
        final String sql = baseSelect + """
                WHERE dir.account_id = :account_id;
                """;

        try{
            return client.sql(sql)
                    .param("account_id", user.getId())
                    .query(new DocumentRowMapper())
                    .list();

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while fetching all documents for a user", e);
        }
    }

    @Override
    public Document getDocumentById(int id) throws DataAccessException{
        final String sql = baseSelect + """
                WHERE doc.document_id = :document_id
                """;
        try{
            return client.sql(sql)
                    .param("document_id", id)
                    .query(new DocumentRowMapper())
                    .optional().orElse(null);
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while fetching a document by id", e);
        }
    }

    @Override
    public Document createDocument(Document document) throws DataAccessException {
        final String sql = """
                insert into document (document_type_id, document_name, directory_id) values
                	((select document_type_id from document_type where document_type_name = :document_type),
                	:document_name, :directory_id);
                """;

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                    .param("document_type", document.getDocumentType().name())
                    .param("document_name", document.getName())
                    .param("directory_id", document.getParentDirectoryId())
                    .update(keyHolder);

            document.setId(keyHolder.getKey().intValue());
            return document;

        } catch(Exception e){
            throw new DataAccessException("Something went wrong while trying to create a new document", e);
        }
    }

    @Override
    public boolean deleteDocument(int id) throws DataAccessException {
        String sql = """
                DELETE FROM document WHERE document_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", id)
                    .update() > 0;
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to delete a document", e);
        }
    }

    @Override
    public boolean updateDocument(Document document) throws DataAccessException {
        String sql = """
                UPDATE document
                SET document_name = :document_name, directory_id=:directory
                WHERE document_id = :document_id;
                """;

        try{
            return client.sql(sql)
                    .param("document_name", document.getName())
                    .param("directory", document.getParentDirectoryId())
                    .param("document_id", document.getId())
                    .update() > 0;

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to update a document", e);
        }
    }
}
