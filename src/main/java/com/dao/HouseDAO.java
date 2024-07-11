package com.dao;
import java.util.*;
import com.entity.*;
import org.apache.ibatis.annotations.Param;

public interface HouseDAO {
   void add(House data);
   void del(int id);
   House findById(int id);
   void update(House data);
   List show(@Param("text")String text);

    House selectByFlat(@Param("flat") String flat);
}
