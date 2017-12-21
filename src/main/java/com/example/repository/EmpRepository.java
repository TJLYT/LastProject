package com.example.repository;
import com.example.domain.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpRepository extends JpaRepository<Employee,String>,JpaSpecificationExecutor<Employee>,PagingAndSortingRepository<Employee, String>{
	
}