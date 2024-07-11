package com.dao;

import com.entity.Paywarn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaywarnDAO {
   void add(Paywarn data);
   void del(int id);
   Paywarn findById(int id);
   void update(Paywarn data);
   List show(@Param("text") String text);
   List my(@Param("userId") int userId);


}
