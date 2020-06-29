package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zyl.shop.entity.GoodsType;

@Mapper
public interface GoodsTypeDao {
	@Select("select * from tb_goodstype")
	List<GoodsType> getAllTypes();

	@Update("update tb_goodstype set status=#{status} where gtid=#{gtid}")
	void updateStatus(@Param("gtid")int tid, @Param("status")String status);

	@Insert("insert into tb_goodstype(gtid, typename, status) values(0, #{tname}, 1)")
	void addType(@Param("tname")String tname);
}
