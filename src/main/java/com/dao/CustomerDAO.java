package com.dao;
import java.util.*;
import com.entity.*;
import org.apache.ibatis.annotations.Param;

public interface CustomerDAO {
   void add(Customer data);
   void del(int id);
   Customer findById(int id);

   Customer findByMobile(String tel);


   void update(Customer data);
   List show(@Param("text") String text,@Param("flat") Long flat);
   List my(int id,@Param("text")String text,@Param("flat") Long flat);
}
