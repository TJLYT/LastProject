package com.example.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import com.example.domain.Role;

/**        
 * @version      
 */ 
@Repository
public interface RoleRepository extends JpaRepository<Role,String>,JpaSpecificationExecutor<Role>,PagingAndSortingRepository<Role, String> {
	
}
