package com.example.github.respository;

import com.example.github.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespoitoryMessage extends JpaRepository<Message, Long> {

    List<Message> findByTag(String tag);

}
