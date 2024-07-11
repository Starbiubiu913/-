package com.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface StatisticDAO {
   List house(@Param("text") Integer text);

   List person(@Param("text") Integer text);

   List tousu(@Param("text") Integer text);

}
