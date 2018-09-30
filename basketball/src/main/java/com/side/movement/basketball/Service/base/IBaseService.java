package com.side.movement.basketball.Service.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
/**
 * 基础service
 * @author zrm
 *
 * @param <T>
 */
public interface IBaseService<T> {
	/**
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * @param record
	 * @return
	 */
	public int insert(T record);
	
	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param record
	 * @return
	 */
	public int insertSelective(T record);
	
	/**
	 * 	根据实体属性作为条件进行删除，查询条件使用等号
	 * @param record
	 * @return
	 */
	public int delete(T record);
	
	/**
	 * 根据Example条件删除数据
	 * @param example
	 * @return
	 */
	public int deleteByExample(Object example);
	
	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * @param key
	 * @return
	 */
	public int deleteByPrimaryKey(Object key);
	
	/**
	 * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExample(T record, Object example);
	
	/**
	 * 根据Example条件更新实体`record`包含的不是null的属性值
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExampleSelective(T record, Object example);
	
	/**
	 * 根据主键更新实体全部字段，null值会被更新
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKey(T record);
	
	/**
	 * 根据主键更新属性不为null的值
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(T record);
	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> select(T record);

	/**
	 * 查询全部结果
	 */
	public List<T> selectAll();
	
	/**
	 * 根据Example条件进行查询
	 */
	public List<T> selectByExample(Object example);
	
	/**
	 * 根据example条件和RowBounds进行分页查询
	 */
	public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds);
	
	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 * @param key
	 * @return
	 */
	public T selectByPrimaryKey(Object key);
	
	/**
	 * 根据实体属性和RowBounds进行分页查询
	 * @param record
	 * @param rowBounds
	 * @return
	 */
	public List<T> selectByRowBounds(T record, RowBounds rowBounds);
	
	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 * @param record
	 * @return
	 */
	public int selectCount(T record);
	
	/**
	 * 根据Example条件进行查询总数
	 * @param example
	 * @return
	 */
	public int selectCountByExample(Object example);
	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 */
	public T selectOne(T record);
}
