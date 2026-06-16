package com.example.HellTrain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.HellTrain.entity.ChatMessage;

@Repository
public interface ChatMessageDao extends JpaRepository<ChatMessage, Integer> {

	@Query(value = "select * from chat_message where room_id = ?1", nativeQuery = true)
	public List<ChatMessage> history(int roomId);

	@Modifying
	@Transactional
	@Query(value = "insert into chat_message (room_id, sender_id, message_content, is_read) "//
			+ " values (?1, ?2, ?3, ?4)", nativeQuery = true)
	public void save(int roomId, int senderId, String messageContent, boolean isRead);
}
