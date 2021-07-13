package com.lin.simserver.repository;

import com.lin.simserver.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:16
 */
public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findByFromUserIdAndToUserId(String fromUserId,String toUserId);

    @Modifying
    @Transactional
    @Query(value = "update Message m set m.status=:status,m.updateDate=:updateDate where m.id=:id")
    int updateMessage(@Param("status") int status,@Param("updateDate")Date updateDate,@Param("id") int id);

}
