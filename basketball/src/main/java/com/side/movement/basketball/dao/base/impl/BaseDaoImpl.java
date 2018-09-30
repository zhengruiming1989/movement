package com.side.movement.basketball.dao.base.impl;

import java.util.List;

import com.side.movement.basketball.dao.base.IBaseDao;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;


import tk.mybatis.mapper.common.Mapper;

public class BaseDaoImpl<T> implements IBaseDao<T> {
	@Autowired
    private Mapper<T> mapper;

	@Override
	public int insert(T record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public int delete(T record) {
		// TODO Auto-generated method stub
		return mapper.delete(record);
	}
	@Override
	public int deleteByExample(Object example) {
		// TODO Auto-generated method stub
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int updateByExample(T record, Object example) {
		// TODO Auto-generated method stub
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByExampleSelective(T record, Object example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<T> select(T record) {
		// TODO Auto-generated method stub
		return mapper.select(record);
	}

	@Override
	public List<T> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public List<T> selectByExample(Object example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return mapper.selectByExampleAndRowBounds(example, rowBounds);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return mapper.selectByRowBounds(record, rowBounds);
	}

	@Override
	public int selectCount(T record) {
		// TODO Auto-generated method stub
		return mapper.selectCount(record);
	}

	@Override
	public int selectCountByExample(Object example) {
		// TODO Auto-generated method stub
		return mapper.selectCountByExample(example);
	}

	@Override
	public T selectOne(T record) {
		// TODO Auto-generated method stub
		return mapper.selectOne(record);
	}

}
