package sansanvn.web.chatweb.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sansanvn.web.chatweb.entity.DBChatRoom;
import sansanvn.web.chatweb.entity.DBChatRoomExample;

public interface DBChatRoomMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	long countByExample(DBChatRoomExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int deleteByExample(DBChatRoomExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int insert(DBChatRoom record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int insertSelective(DBChatRoom record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	List<DBChatRoom> selectByExample(DBChatRoomExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	DBChatRoom selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int updateByExampleSelective(@Param("record") DBChatRoom record, @Param("example") DBChatRoomExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int updateByExample(@Param("record") DBChatRoom record, @Param("example") DBChatRoomExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int updateByPrimaryKeySelective(DBChatRoom record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table public.dbchatroom
	 * @mbg.generated  Tue Jul 23 21:20:20 ICT 2019
	 */
	int updateByPrimaryKey(DBChatRoom record);
}