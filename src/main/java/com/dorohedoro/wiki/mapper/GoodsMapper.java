package com.dorohedoro.wiki.mapper;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.GoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}