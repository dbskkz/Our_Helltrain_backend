package com.example.HellTrain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.constant.UserStatus;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.dao.WishDao;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.entity.Wish;
import com.example.HellTrain.request.WishReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetWishesRes;
import com.example.HellTrain.vo.SimpleUserVo;
import com.example.HellTrain.vo.WishesVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.transaction.annotation.Transactional;

@EnableScheduling 
@Service
public class WishService {

    @Autowired
    private WishDao wishDao;

    @Autowired
    private UserDao userDao;

    private ObjectMapper mapper = new ObjectMapper();

    // ── 私有工具方法 ──────────────────────────────────────────

    // JSON 字串轉 List<String>
    private List<String> parseJsonList(String json) {
        if (json == null || json.isBlank())
            return new ArrayList<>();
        try {
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // 判斷 JSON 字串欄位是否包含 targets 其中任一個值
    private boolean jsonContainsAny(String json, List<String> targets) {
        if (json == null || json.isBlank() || CollectionUtils.isEmpty(targets))
            return false;
        List<String> values = parseJsonList(json);
        return values.stream().anyMatch(targets::contains);
    }

    // Wish entity 轉 WishesVo（帶入已查好的 wisherVo，避免 N+1）
    private WishesVo toVo(Wish wish, SimpleUserVo wisherVo) {
        return new WishesVo(
            wish.getId(),
            wish.getUserId(),
            wish.getTitle(),
            wish.getDescription(),
            parseJsonList(wish.getType()),   // DB 存 JSON 字串，轉成 List<String>
            parseJsonList(wish.getLocation()),   // DB 存 JSON 字串，轉成 List<String>
            wish.getBudgetMin(),
            wish.getBudgetMax(),
            wish.getStatus(),
            wish.getCreatedAt(),
            wish.getExpiredAt(),
            wisherVo
        );
    }

    // List<Wish> 轉成前端格式（批次查 User，避免 N+1）
    private GetWishesRes convertToFrontEndFormat(List<Wish> wishList) {
        if (CollectionUtils.isEmpty(wishList)) {
            return new GetWishesRes(ReplyMessage.NO_DATA_FOUND.getCode(),
                    ReplyMessage.NO_DATA_FOUND.getMessage());
        }

        // 批次查詢所有許願者資料
        List<Integer> userIds = wishList.stream()
                .map(Wish::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Integer, SimpleUserVo> wisherMap = userDao.findByUserIdIn(userIds)
                .stream()
                .collect(Collectors.toMap(
                    User::getUserId,
                    u -> new SimpleUserVo(u.getUserId(), u.getUserName(),
                                         u.getSchool(), u.getImgPath(), u.getDepartment(), u.getGoodLevel())
                ));

        List<WishesVo> voList = wishList.stream()
                .map(w -> toVo(w, wisherMap.get(w.getUserId())))
                .collect(Collectors.toList());

        return new GetWishesRes(ReplyMessage.SUCCESS.getCode(),
                ReplyMessage.SUCCESS.getMessage(), voList);
    }

    // ── 參數檢查 ──────────────────────────────────────────────

    private BasicResponse checkParam(WishReq req) {
        // 標題不能為空
        if (!StringUtils.hasText(req.getTitle())) {
            return new BasicResponse(ReplyMessage.INVALID_PARAM.getCode(),
                    "許願標題不能為空");
        }

        // 分類不能為空
//        if (CollectionUtils.isEmpty(req.getType())) {
//            return new BasicResponse(ReplyMessage.INVALID_PARAM.getCode(),
//                    "請至少選擇一個分類");
//        }

        // 預算區間合理性檢查
        if (req.getBudgetMin() < 0 || req.getBudgetMax() < 0) {
            return new BasicResponse(ReplyMessage.INVALID_PARAM.getCode(),
                    "預算不能為負數");
        }

        if (req.getBudgetMin() > req.getBudgetMax()) {
            return new BasicResponse(ReplyMessage.INVALID_PARAM.getCode(),
                    "最低預算不能大於最高預算");
        }

        return new BasicResponse(ReplyMessage.SUCCESS.getCode(),
                ReplyMessage.SUCCESS.getMessage());
    }

    // ── POST：新增許願 ────────────────────────────────────────

    @Transactional(rollbackFor = Exception.class)
    public BasicResponse addWish(int userId, WishReq req) {

        // 1. 確認使用者存在且狀態正常
        User user = userDao.getById(userId);
        if (user == null || !user.getStatus().equals(UserStatus.Normal.getMessage())) {
            return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(),
                    ReplyMessage.NO_PERMISSIONS.getMessage());
        }
        
        // 限制許願額度
        if (wishDao.countWishesByIdAndDate(userId, LocalDate.now()) > 2)
        {
        	return new BasicResponse(ReplyMessage.TOO_MANY.getCode(),
                    ReplyMessage.TOO_MANY.getMessage());
        }
        
        // 2. 參數檢查
        BasicResponse checkResult = checkParam(req);
        if (checkResult.getStatusCode() != 200) {
            return checkResult;
        }

        // 3. 轉換格式並寫入 DB
        try {
            String typeJson = mapper.writeValueAsString(req.getType());
            String locationJson = mapper.writeValueAsString(req.getLocation());

            wishDao.insert(
                userId,
                req.getTitle(),
                req.getDescription() != null ? req.getDescription() : "",
                typeJson,
                locationJson,
                req.getBudgetMin(),
                req.getBudgetMax(),
                req.getStatus(),                      // 預設狀態
                LocalDateTime.now(),           // 建立時間
                LocalDateTime.now().plusDays(30) // 30 天後到期
            );

        } catch (Exception e) {
            return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(),
                    ReplyMessage.PLEASE_TRY_LATE.getMessage());
        }

        return new BasicResponse(ReplyMessage.SUCCESS.getCode(),
                ReplyMessage.SUCCESS.getMessage());
    }

    // ── QUERY 1：查詢所有 active 許願 ────────────────────────

    public GetWishesRes getAllWishes() {
        List<Wish> wishList = wishDao.findAllActive();
        return convertToFrontEndFormat(wishList);
    }

    // ── QUERY 2：篩選指定學校的許願 ──────────────────────────

    public GetWishesRes getWishesBySchool(String school) {
        if (!StringUtils.hasText(school)) {
            return new GetWishesRes(ReplyMessage.INVALID_PARAM.getCode(),
                    ReplyMessage.INVALID_PARAM.getMessage());
        }
        List<Wish> wishList = wishDao.findActiveBySchool(school);
        return convertToFrontEndFormat(wishList);
    }

    // ── QUERY 3：以分類篩選許願 ───────────────────────────────

//    public GetWishesRes getWishesByType(List<String> types) {
//        if (CollectionUtils.isEmpty(types)) {
//            return new GetWishesRes(ReplyMessage.INVALID_PARAM.getCode(),
//                    ReplyMessage.INVALID_PARAM.getMessage());
//        }
//
//        // category 在 DB 是 JSON 字串，只能在 Java 層過濾
//        List<Wish> filtered = wishDao.findAllActive().stream()
//                .filter(w -> jsonContainsAny(w.getType(), types))
//                .collect(Collectors.toList());
//
//        return convertToFrontEndFormat(filtered);
//    }

    // ── PATCH：將許願標記為已實現 ─────────────────────────────

    public BasicResponse fulfillWish(int userId, int wishId) {

    	Wish wish = wishDao.findById(wishId).orElse(null);

        if (wish == null) {
            return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(),
                    ReplyMessage.NO_DATA_FOUND.getMessage());
        }

        // 只有許願者本人可以標記為已實現
        if (wish.getUserId() != userId) {
            return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(),
                    ReplyMessage.NO_PERMISSIONS.getMessage());
        }

        if (!wish.getStatus().equals("active")) {
            return new BasicResponse(ReplyMessage.INVALID_PARAM.getCode(),
                    "此願望已非許願中狀態");
        }

        wishDao.updateStatus(wishId, "fulfilled");

        return new BasicResponse(ReplyMessage.SUCCESS.getCode(),
                ReplyMessage.SUCCESS.getMessage());
    }
    
    // 每天凌晨 3 點執行
    // cron 格式：秒 分 時 日 月 星期
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredWishes() {
        wishDao.deleteExpired();
    }
}