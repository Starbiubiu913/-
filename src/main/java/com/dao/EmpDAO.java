package com.dao;
import java.util.*;
import com.entity.*;
import org.apache.ibatis.annotations.Param;

public interface EmpDAO {
   void add(Emp data);
   void del(int id);
   Emp findById(int id);
   void update(Emp data);
   List show(@Param("text") String text,@Param("flat") Long flat);
   List my(int id,@Param("flat") Long flat);

    Emp selectByNo(@Param("no")String no);

}
