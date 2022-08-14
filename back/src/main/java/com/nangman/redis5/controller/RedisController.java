package com.nangman.redis5.controller;

import com.nangman.db.entity.Bus;
import com.nangman.db.entity.Route;
import com.nangman.redis5.dto.*;
import com.nangman.redis5.service.RedisService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/redis")
@Api(value = "레디스 API", tags = {"redis"})
public class RedisController {
//    private final RedisRepositoryConfig repositoryConfig;
//    private final RedisServiceImpl redisService = new RedisServiceImpl(redisTemplate);
//    private final RedisServiceImpl redisService;
    private final RedisService redisService;


    @GetMapping("/selectRooms/{lat}/{lng}")
    public ResponseEntity<List<ChattingRoomDto>> selectRooms(@PathVariable double lat, @PathVariable double lng) {
        return new ResponseEntity<List<ChattingRoomDto>>(
                redisService.selectRooms(lat, lng),HttpStatus.OK);
    }

    @GetMapping("/getRandomBus")
    public ResponseEntity<List<RandomBusDto>> getRandomBus() {
        return new ResponseEntity<List<RandomBusDto>>(
                redisService.getRandomBus(), HttpStatus.OK);
    }

    @GetMapping("/isAccessibleRoom/{lat}/{lng}/{sessionId}")
    public boolean isAccessibleRoom(@PathVariable double lat, @PathVariable double lng, @PathVariable String sessionId) {
        return redisService.isAccessibleRoom(lat, lng, sessionId);
    }


    @GetMapping("/")
    public String ok() {
        return "ok";
    }





//    @GetMapping("/test06")
//    public void test06() {
//        redisService.test01();
//    }

    @GetMapping("/test07")
    public void test07() {
        System.out.println(redisService.deleteChattingRoom("sessionId").toString());
    }

    private String testSessionId = "sessionId_20220810236801";
    private String user1 = "1";
//    @GetMapping("/test/updateBusData")
//    public void updateBusData() {
//        Bus bus = new Bus();
////        Route route = new Route();
//
//        bus.setId(0L);
//        bus.setLicenseNo("경기77바3771");
////        busLog.setRoute("8100");
//        bus.setLat(37.34033);
//        bus.setLng(127.10899);
//        bus.setCreatedDate(LocalDateTime.now());
//        bus.setNodeOrd(46);
//        bus.setNodeId("GGB206000215");
//
//        redisService.updateBudData(testSessionId, bus);
//    }
    @GetMapping("/test/createChattingRoom")
    public void createChattingRoom() {
//        Bus busLog = new Bus();
//        busLog.setId(0L);
//        busLog.setLicenseNo("경기77바3771");
////        busLog.setRoute("8100");
//        busLog.setLat(37.32341);
//        busLog.setLng(127.1259);
//        busLog.setCreatedDate(LocalDateTime.now());
//        busLog.setNodeOrd(2);
//        busLog.setNodeId("GGB228001978");
//        busLog.setNodeName("단국대정문");
//
//        List<String> busStops = new ArrayList<>();
//        String[] strs = "단국대영업소:단국대정문:꽃메마을.새에덴교회:보정동주민센터:오리역:미금역.청솔마을.2001아울렛:정자역:분당구청입구.수내교:순천향대학병원:남대문세무서:종로2가사거리(중):을지로입구역.광교:북창동.남대문시장:서울역버스환승센터(5번 승강장):숭례문:명동국민은행앞:남대문세무서.서울백병원:순천향대학병원:분당구청입구,수내교:정자역:미금역,청솔마을.2001아울렛:오리역:보정동행정복지센터:꽃메마을2단지:단국대,치과병원".split(":");
//        for(String str : strs) {
//            busStops.add(str);
//        }
//
//        this.testSessionId = redisService.createChattingRoom(busLog, busStops);
    }
//    @GetMapping("/test/deleteChattingRoom")
//    public ChatLogDto deleteChattingRoom() {
//        return redisService.deleteChattingRoom(testSessionId);
//    }

    @GetMapping("/test/sessions")
    public List<String> getSessionList() {
        return redisService.getSessionList();
    }






    @GetMapping("/test/upLike")
    public void upLike() {
        redisService.upLike(testSessionId, "1");
    }
    @GetMapping("/test/downLike")
    public void downLike() {
        redisService.downLike(testSessionId, "1");
    }

    @GetMapping("/test/getLike")
    public long getLike() {
        return redisService.getLike(testSessionId, "1");
    }
    @GetMapping("/test/updateMyEmotion")
    public void updateMyEmotion() {
        redisService.updateMyEmotion(testSessionId, "1", 1);
    }
    @GetMapping("/test/roomUserList")
    public List<RoomUserDto> roomUserList() {
        return redisService.roomUserList(testSessionId);
//        return ResponseEntity<List<roomUserDto>>(new List<roomUserDto>(redisService.roomUserList(testSessionId)), HttpStatus.OK);
    }
    @GetMapping("/test/createChat")
    public void createChat() {
        redisService.createChat(testSessionId,"6", LocalDateTime.now().toString(), "hello");
    }
    @GetMapping("/test/joinRoom")
    public void joinRoom() {
        RoomUserDto roomUserDto = new RoomUserDto();
        roomUserDto.setNickName("nickName11");
        roomUserDto.setBirth("today");
        roomUserDto.setEmotion(1);
        roomUserDto.setOutBusStop("3");
        redisService.joinRoom(testSessionId, "11", roomUserDto);
    }
    @GetMapping("/test/exitRoom")
    public void exitRoom() {
        redisService.exitRoom(testSessionId, "10");
    }
    @GetMapping("/test/setOutBusStop")
    public void setOutBusStop() {
        redisService.setOutBusStop(testSessionId, "10", "1");
    }
}
