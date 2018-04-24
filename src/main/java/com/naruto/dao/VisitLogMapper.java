package com.naruto.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.naruto.entity.VisitLog;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月23日
 */
@Mapper
public interface VisitLogMapper {

	@Insert("insert into visitlog (id,ip,browser,os,time) values ( #{id},#{ip},#{browser},#{os},#{time} ) ")
	public int add(VisitLog log);

}
