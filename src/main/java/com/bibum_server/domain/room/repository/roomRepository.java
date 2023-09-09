package com.bibum_server.domain.room.repository;

import com.bibum_server.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roomRepository extends JpaRepository<Room,Long> {
}
