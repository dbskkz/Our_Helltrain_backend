package com.example.HellTrain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HellTrain.entity.ChatRoom;

@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, Integer> {

	@Query(value = "select * from chat_room where initiator_id = ?1 and receiver_id = ?2", nativeQuery = true)
	public ChatRoom findByInitiatorIdAndReceiverId(int initiatorId, int receiverId);
}
