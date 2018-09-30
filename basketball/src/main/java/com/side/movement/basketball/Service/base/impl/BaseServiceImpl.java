package com.side.movement.basketball.Service.base.impl;

import java.util.List;

import com.side.movement.basketball.Service.base.IBaseService;
import com.side.movement.basketball.dao.base.IBaseDao;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseServiceImpl<T> implements IBaseService<T> {
	@Autowired
    private IBaseDao<T> dao;

	@Override
	public int insert(T record) {
		// TODO Auto-generated method stub
		return dao.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		// TODO Auto-generated method stub
		return dao.insertSelective(record);
	}

	@Override
	public int delete(T record) {
		// TODO Auto-generated method stub
		return dao.delete(record);
	}

	@Override
	public int deleteByExample(Object example) {
		// TODO Auto-generated method stub
		return dao.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(key);
	}

	@Override
	public int updateByExample(T record, Object example) {
		// TODO Auto-generated method stub
		return dao.updateByExample(record, example);
	}

	@Override
	public int updateByExampleSelective(T record, Object example) {
		// TODO Auto-generated method stub
		return dao.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<T> select(T record) {
		// TODO Auto-generated method stub
		return dao.select(record);
	}

	@Override
	public List<T> selectAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

	@Override
	public List<T> selectByExample(Object example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}

	@Override
	public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return dao.selectByExampleAndRowBounds(example, rowBounds);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(key);
	}

	@Override
	public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return dao.selectByRowBounds(record, rowBounds);
	}

	@Override
	public int selectCount(T record) {
		// TODO Auto-generated method stub
		return dao.selectCount(record);
	}

	@Override
	public int selectCountByExample(Object example) {
		// TODO Auto-generated method stub
		return dao.selectCountByExample(example);
	}

	@Override
	public T selectOne(T record) {
		// TODO Auto-generated method stub
		return dao.selectOne(record);
	}
}
