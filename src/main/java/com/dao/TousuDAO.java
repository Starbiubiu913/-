package com.dao;

import com.entity.Liuyan;
import com.entity.Tousu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TousuDAO {
   void add(Tousu data);
   void del(int id);
   Tousu findById(int id);
   void update(Tousu data);
   List show(@Param("text") String text);
   List my(String room);
}
