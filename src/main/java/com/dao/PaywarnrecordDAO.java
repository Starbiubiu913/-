package com.dao;

import com.entity.Paywarnrecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaywarnrecordDAO {
   void add(Paywarnrecord data);
   Paywarnrecord findById(int id);
   void update(Paywarnrecord data);

}
