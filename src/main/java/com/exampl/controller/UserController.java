package com.exampl.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.exampl.service.PagenationService;
import com.exampl.service.UserService;
import com.example.dto.UserDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	ObjectMapper om;
	@Autowired 
	PagenationService pageService;
	

	@RequestMapping(value="user_list")
	public String toUserList() {
		return "user_list";
	}
	
	/**
	 * @Description: userlist的分页处理
	 */
	@RequestMapping(value="/userlist",method=RequestMethod.POST)
	@ResponseBody
	public String pagenationTest(String models){

		Map usersMap = new HashMap<>();
		
		if(models!=null&&models.length()>0) {

			try {
				usersMap = userService.getUserPage(models);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return JSONObject.toJSONString(usersMap);
	}
	
	
	/**
	 * @Description: userList页面add按钮返回的模态框
	 */
	@RequestMapping(value="serviceAdd",method=RequestMethod.GET)
	public String userAdd(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("u", userDto);
		return "user_modal";
	}
	
	/**
	 * @Description: userList页面edit按钮返回的模态框
	 */
	@RequestMapping(value="serviceEdit",method=RequestMethod.GET)
	public String userEdit(String id,Model model) {
		UserDto userDto = null;
		try {
			userDto = userService.findById(id);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		model.addAttribute("u", userDto);
		return "user_modal";
	}
	
	
	
	/**
	 * @Description: 新增或者编辑按钮的模态框保存按钮的处理
	 */
	@RequestMapping(value="addUser",method=RequestMethod.POST)
	@ResponseBody
	public String addUser(String data) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(data);
		Map<String, String> result = new HashMap<>();
		if(data!=null) {
			UserDto userDto = om.readValue(data, UserDto.class);
			try {
				if(userDto.getId()==null||userDto.getId().isEmpty()) {
					//如果id为null值则为新增用户
					//userDto.setCreateTime(Calendar.getInstance().getTime());
					userService.addUser(userDto);
					result.put("msg", "操作成功");
				}else {
					//id不为null或者空,为修改用户信息
					userService.updateUser(userDto);
					result.put("msg", "操作成功");
				}
				
			} catch (IllegalAccessException | InstantiationException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
				result.put("msg", "操作失败");
			}
		}
		return om.writeValueAsString(result);
	}
	
	
	/**
	 * @Description: userList页面del删除按钮的处理
	 */
	@RequestMapping(value="serviceDel",method=RequestMethod.POST)
	@ResponseBody
	public String userDel(String id) throws JsonProcessingException {
		HashMap<String, String> result = new HashMap<>();
		UserDto userDto = null;
		try {
			userDto = userService.findById(id);
			userService.deleteUser(userDto);
			result.put("msg", "删除成功");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			result.put("msg", "删除失败");
		}
		 return om.writeValueAsString(result);
	}
	

}
