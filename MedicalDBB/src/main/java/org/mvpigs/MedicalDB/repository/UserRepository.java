package org.mvpigs.MedicalDB.repository;
import org.mvpigs.MedicalDB.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {

	@Query(value="SELECT * FROM account WHERE username=?1", nativeQuery = true)
    User findByUsername(@Param("username") String username);
	
	
    

}
