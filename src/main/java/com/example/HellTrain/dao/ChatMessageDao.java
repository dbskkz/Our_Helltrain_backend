package com.example.HellTrain.dao;

import java.util.List;
import java.util.Optional;

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
	
	@Query(value = "select count(*) from chat_message where room_id = ?1 and sender_id = ?2 and is_read = false", nativeQuery = true)
	public int countUnreadMsg(int roomId, int targetUserId);
	
	@Query(value = "select * from chat_message where room_id = ?1 order by message_id desc limit 1", nativeQuery = true)
	public Optional<ChatMessage> lastMessage(int roomId);
	
	@Modifying
	@Transactional
	@Query(value = "update chat_message set is_read = true where room_id = ?1 and sender_id = ?2 and is_read = false", nativeQuery = true)
	public int readAllMessages(int roomId, int targetUserId);
}
