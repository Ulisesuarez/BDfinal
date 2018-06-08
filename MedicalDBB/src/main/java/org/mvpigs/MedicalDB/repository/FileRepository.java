package org.mvpigs.MedicalDB.repository;


import org.mvpigs.MedicalDB.domain.File;
import org.mvpigs.MedicalDB.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface FileRepository extends CrudRepository<File, Long> {
	
	@Query(value = "SELECT file_id, name, description, type, color, deleted, deleted_at, created_at, updated_at, account_owner_id, username, email, ts_headline('spanish', description, q) AS headline, COUNT(*) OVER() AS total FROM full_text_search , to_tsquery('spanish',?1) q WHERE  deleted = false AND (tsv @@ q OR tsv_es @@ q AND type=?2);", nativeQuery = true)
    ArrayList<File> findByType(String query, String type);

    @Query(value = "SELECT file_id, name, description, type, color, deleted, deleted_at, created_at, updated_at, account_owner_id, username, email FROM full_text_search , to_tsquery('spanish',' ') q WHERE  deleted = FALSE AND file_id=?1", nativeQuery = true)
    File findByFileId(long file_id);
    
    @Query(value = "SELECT file_id, name, description, type, color, deleted, deleted_at, created_at, updated_at, account_owner_id, username, email, ts_headline('spanish', description, q) AS headline, COUNT(*) OVER() AS total FROM full_text_search , to_tsquery('spanish',?1) q WHERE  deleted = false AND  (tsv @@ q OR tsv_es @@ q) AND account_owner_id = ?2 ;", nativeQuery = true)
    ArrayList<File> findFilesByQuery(String query, Integer account_id);
    
    @Modifying
    @Transactional
    @Query(value="INSERT INTO document (file_id, name, description, deleted, deleted_at, created_at, updated_at, account_owner_id, uuid, type, original_filename, status, has_thumbnail ) VALUES (?1 ,?2 ,?3 ,false , null ,?4 , null , ?5, 'tomauuid', 'unknown', 'no', 'completed',false);", nativeQuery = true)
    Integer uploadDocument(long file_id, String name, String description, OffsetDateTime localDate, long account_owner_id);
    
    @Modifying
    @Transactional
    @Query(value="INSERT INTO \"case\" (file_id, name, description, deleted, deleted_at, created_at, updated_at, account_owner_id ) VALUES (?1 ,?2 ,?3 ,false , null ,?4 , null , ?5 );", nativeQuery = true)
    Integer uploadCase(long file_id, String name, String description, OffsetDateTime localDate, long account_owner_id);

    
    @Modifying
    @Transactional
    @Query(value="INSERT INTO study (file_id, name, description, deleted, deleted_at, created_at, updated_at, account_owner_id ) VALUES (?1 ,?2 ,?3 ,false , null ,?4 , null , ?5 );", nativeQuery = true)
    Integer uploadStudy(long file_id, String name, String description, OffsetDateTime localDate, long account_owner_id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO directory (file_id, name, description, deleted, deleted_at, created_at, updated_at, account_owner_id ) VALUES (?1 ,?2 ,?3 ,false , null ,?4 , null , ?5 );", nativeQuery = true)
    Integer uploadDirectory(long file_id, String name, String description, OffsetDateTime localDate, long account_owner_id);

    @Query(value = "SELECT max(file_id) FROM full_text_search;", nativeQuery = true)
    Integer findMaxID();
   
    @Modifying
    @Transactional
    @Query(value = "UPDATE file SET deleted=true WHERE file_id = ?1 ;", nativeQuery = true)
	Integer deleteFile(long file_id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE file SET name=?1 WHERE file_id = ?2 ;", nativeQuery = true)
	Integer updateFileName(String name, long file_id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE file SET description=?1 WHERE file_id = ?2 ;", nativeQuery = true)
	Integer updateFileDescription(String description, long file_id);
    
    @Modifying
    @Transactional
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY full_text_search;", nativeQuery = true)
	Integer refresh();
    @Modifying
    @Transactional
    @Query(value ="REFRESH MATERIALIZED VIEW CONCURRENTLY search_document;", nativeQuery = true)
	Integer refreshDocument();
    @Modifying
    @Transactional
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY search_case;" , nativeQuery = true)
	Integer refreshCase();
    @Modifying
    @Transactional
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY search_study;", nativeQuery = true)
	Integer refreshStudy();
    @Modifying
    @Transactional
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY search_directory;", nativeQuery = true)
	Integer refreshDirectory();
    @Modifying
    @Transactional
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY search_dicom;", nativeQuery = true)
	Integer refreshDicom();


}	

/*uuid	
	36				type
	
		original_filename	
	100		
	
		mime_type	
	50		
	
		size	
			
	
		status	
	20		
	
		has_thumbnail*/