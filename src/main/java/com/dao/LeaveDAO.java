package com.dao;

import com.entity.Leave;
import com.entity.Paywarn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaveDAO {
   void add(Leave data);
   void del(int id);
   Leave findById(int id);
   void update(Leave data);
   List show(@Param("text") String text);
   List my(@Param("userId") int userId);


}
