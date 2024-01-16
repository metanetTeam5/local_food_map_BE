package com.metanet.amatmu.menu.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.menu.model.Menu;

@Repository
@Mapper
public interface IMenuRepository {

    List<Menu> selectByRestId(Long restId);
}
