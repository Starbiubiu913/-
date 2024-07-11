package com.dao;

import com.entity.Liuyan;
import com.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeDAO {
   void add(Notice data);
   void del(int id);
   Notice findById(int id);
   void update(Notice data);
   List<Notice> show(@Param("text") String text);

}
