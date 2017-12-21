package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.*;


@Repository
public interface MenuRepository extends JpaRepository<Menu,String>,JpaSpecificationExecutor<Menu>,PagingAndSortingRepository<Menu, String> {

}