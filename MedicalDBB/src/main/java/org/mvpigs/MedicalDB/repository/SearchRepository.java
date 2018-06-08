package org.mvpigs.MedicalDB.repository;

import org.mvpigs.MedicalDB.domain.SearchDrug;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SearchRepository extends CrudRepository<SearchDrug, Long> {
	    
    @Modifying
    @Transactional
    @Query(value="INSERT INTO drug_search VALUES (?1 ,?2,?3 );", nativeQuery = true)
    Integer addSearch(long search_id, long user_id, String search_object);

    @Query(value = "SELECT max(search_id) FROM drug_search;", nativeQuery = true)
    Integer findMaxID();
    
    


}	