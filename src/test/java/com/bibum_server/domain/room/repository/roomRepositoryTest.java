package com.bibum_server.domain.room.repository;

import com.bibum_server.domain.room.entity.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class roomRepositoryTest {

    @Autowired
    private roomRepository roomRepository;

    @Test
    public void roomSaveTest(){
        Room room = Room.builder()
                .title("test")
                .url("www.test.com")
                .build();

        Room saveRoom = roomRepository.save(room);

        assertThat("test").isEqualTo(saveRoom.getTitle());
        assertThat("www.test.com").isEqualTo(saveRoom.getUrl());
    }


}