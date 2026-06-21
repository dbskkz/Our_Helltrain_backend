package com.example.HellTrain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.HellTrain.entity.ChatRoom;

@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, Integer> {

	@Query(value = "select * from chat_room where ( initiator_id = ?1 and receiver_id = ?2 ) "
			+ " or ( initiator_id = ?2 and receiver_id = ?1 ) ", nativeQuery = true)
	public ChatRoom findChatRoom(int initiatorId, int receiverId);

	@Query(value = "select * from chat_room where ( initiator_id = ?1 or receiver_id = ?1 ) "
			+ " or ( initiator_id = ?1 or receiver_id = ?1 ) ", nativeQuery = true)
	public List<ChatRoom> getAllRoom(int userId);

	@Modifying
	@Transactional
	@Query(value = "update chat_room set product_id = ?1 where room_id = ?2 ", nativeQuery = true)
	public int updateProductId(Integer productId, int roomId);
}
