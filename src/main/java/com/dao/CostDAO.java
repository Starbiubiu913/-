package com.dao;
import java.util.*;
import com.entity.*;
import org.apache.ibatis.annotations.Param;

public interface CostDAO {
   void add(Cost data);
   void del(int id);
   Cost findById(int id);
   void update(Cost data);
   void save(Cost data);
   List show(@Param("text")String text,@Param("flat")Long flat);
   List my(String room);

    void delByCustId(int id);

    void updateStatusById(int userId);
}
