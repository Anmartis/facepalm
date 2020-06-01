package com.example.facepalm.controller;

import com.example.facepalm.entity.Message;
import com.example.facepalm.entity.Views;
import com.example.facepalm.repo.MessageRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo _messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this._messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return _messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getMessageById(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return _messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message updateMessage (@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return _messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteMessage (@PathVariable("id") Message message) {
        _messageRepo.delete(message);
    }
}
