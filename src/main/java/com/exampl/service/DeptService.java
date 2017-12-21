package com.exampl.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.domain.Department;
import  com.example.domain.Employee;
import  com.example.dto.TreeDto;
import  com.example.repository.DeptRepository;

@Service
public class DeptService {
	@Autowired
	DeptRepository deptRepo;
	@Autowired 
	ObjectMapper om;
	
	/**
	 * @Description: 根据上级机构id查找部门信息，将其转换为treeDto类，再转换为json格式响应给前台
	 * @param parent d
	 * @return
	 * @throws JsonProcessingException String  
	
	 */
	public String findDeptTree(String id) throws JsonProcessingException {
		Department dept = deptRepo.findOne(id);
		TreeDto treeDto = new TreeDto();
		if(dept.getDeptName()!=null) {
			treeDto.setText(dept.getDeptName());
			treeDto.setColor("#009900");
			treeDto.setBackColor("#d6f5d6");
			treeDto.setIcon("glyphicon glyphicon-user");
			treeDto.setSelectedIcon("glyphicon glyphicon-ok");
			treeDto.setHref("getEmps?id="+dept.getId());
			if(dept.getDepartments()!=null) {
				//二级部门，这里只有二级部门，没有写递归
				List<Department> childs = dept.getDepartments();
				List<TreeDto> nodes = new ArrayList<>();
				Map<String, TreeDto> childMap = new HashMap<>();
				for(Department child:childs) {
					
					TreeDto childNode = new TreeDto();
					if(childMap.get(child.getDeptName())==null) {
					childNode.setText(child.getDeptName());
					childNode.setColor("#4d88ff");
					childNode.setBackColor("#e5ffee");
					childNode.setIcon("glyphicon glyphicon-user");
					childNode.setSelectedIcon("glyphicon glyphicon-ok");
					childNode.setHref("getEmps?id="+child.getId());
					nodes.add(childNode);
					childMap.put(child.getDeptName(), childNode);
					}
				}
				treeDto.setNodes(nodes);
			}
		}
		String data = om.writeValueAsString(treeDto);
		return data;
	}
	
	/**
	 * @Description: 根据部门id查询该部门下的员工信息
	 * @param id
	 * @return List<Employee>  
	 * @throws
	 * @author lyt
	 * @date 2017年12月21日
	 */
	public List<Employee> findById(String id){
		Department department = deptRepo.findOne(id);
		List<Employee> employees = department.getEmployees();
		return employees;
	}
}