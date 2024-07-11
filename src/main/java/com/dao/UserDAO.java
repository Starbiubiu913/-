package com.dao;
import com.entity.*;
import java.util.*;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserDAO {
   Userinfo login(Userinfo data);
   Userinfo findById(int id);
   void update(Userinfo data);
   List show();
}
