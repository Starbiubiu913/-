package com.dao;
import java.util.*;
import com.entity.*;
public interface BasicDAO {
   void add(Basic data);
   void del(int id);
   Basic findById(int id);
   void update(Basic data);
   List show();
}
