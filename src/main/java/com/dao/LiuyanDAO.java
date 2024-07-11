package com.dao;
import java.util.*;
import com.entity.*;
import org.apache.ibatis.annotations.Param;

public interface LiuyanDAO {
   void add(Liuyan data);
   void del(int id);
   Liuyan findById(int id);
   void update(Liuyan data);
   List show(@Param("text")String text);
   List my(String room);
}
