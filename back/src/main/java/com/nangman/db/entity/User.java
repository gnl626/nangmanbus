package com.nangman.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 승객 모델 정의.
 */

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@Table(indexes = {@Index(name="Uk_USER_EMAIL", columnList = "useremail", unique = true)})
public class User extends BaseEntity {

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = true)
    private String useremail;

    private String userBirthday;

    private String socialToken;

    @ManyToOne
    @JoinColumn(name = "nickname_id")
    private Nickname nickname;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedDate;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isRouletted;

/*

    public String isRouletted() {
        return isRouletted ? "Y" : "N" ;
    }

    public String isDeleted() {
        return isDeleted ? "Y" : "N";
    }
*/

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    public void addBoard(Board board) {
        this.boards.add(board);
        if (board.getUser() != this) {
            board.setUser(this);
        }
    }

    // 무한루프 빠지지 않도록 체크
    public void setNickname(Nickname nickname) {
        this.nickname = nickname;

        if (!nickname.getUsers().contains(this)) {
            nickname.getUsers().add(this);
        }
    }

    @OneToOne(mappedBy = "user")
    private Setting setting;

    @OneToMany(mappedBy = "user")
    private List<Chat> chats = new ArrayList<>();

    public void addChat(Chat chat) {
        this.chats.add(chat);

        if (chat.getUser() != this) {
            chat.setUser(this);
        }
    }

    @OneToMany(mappedBy = "user")
    private List<ChatInOutRecord> chatInOutRecords = new ArrayList<>();

    public void addChatInOutRecord(ChatInOutRecord chatInOutRecord) {
        this.chatInOutRecords.add(chatInOutRecord);

        if (chatInOutRecord.getUser() != this) {
            chatInOutRecord.setUser(this);
        }
    }

    @OneToMany(mappedBy = "user")
    private List<UserReport> userReports = new ArrayList<>();
    public void addUserReport(UserReport userReport) {
        this.userReports.add(userReport);

        if (userReport.getUser() != this) {
            userReport.setUser(this);
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", useremail='" + useremail + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", socialToken='" + socialToken + '\'' +
                ", nickname=" + nickname +
                ", isDeleted=" + isDeleted +
                ", deletedDate=" + deletedDate +
                ", isRouletted=" + isRouletted +
                ", boards=" + boards +
                ", setting=" + setting +
                ", chats=" + chats +
                ", chatInOutRecords=" + chatInOutRecords +
                ", userReports=" + userReports +
                '}';
    }
}
