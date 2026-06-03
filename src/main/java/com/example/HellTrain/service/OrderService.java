package com.example.HellTrain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.OrderStatus;
import com.example.HellTrain.constant.ProductStatus;
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.constant.UserStatus;
import com.example.HellTrain.dao.OrderDao;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.Order;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.request.GoodLevelReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.vo.ChangeOrderStatusVo;
import com.example.HellTrain.vo.OrderVo;

@Service
public class OrderService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderDao orderDao;

	// 新增訂單
	public BasicResponse addOrder(int id, OrderVo vo) {// (Get)

//		User user = userDao.getById(vo.getBuyerId());

		User user = userDao.getById(id);
		// 查無此買家帳號
		if (user == null) {
			return new BasicResponse(ReplyMessage.EMAIL_NOT_FOUND.getCode(), //
					ReplyMessage.EMAIL_NOT_FOUND.getMessage());
		}

		// 檢查帳號是否被停權。改成不等於正常，阿靠邀我她媽咧寫三小，但至少不會檢查有無驗證時間
//		if(user.getStatus()==UserStatus.Suspension.getMessage()) {
		if (!user.getStatus().equals(UserStatus.Normal.getMessage())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}

		Product product = productDao.findByProductId(vo.getProductId());
		// 確定有商品
		if (product == null) {
			return new BasicResponse(ReplyMessage.PRODUCT_IS_NOTFOUND.getCode(), //
					ReplyMessage.PRODUCT_IS_NOTFOUND.getMessage());
		}
		// 檢查商品是否為販售中
		if (!product.getStatus().equals(ProductStatus.OnSale.getMassage())) {
			return new BasicResponse(ReplyMessage.PRODUCT_IS_UNSELL.getCode(), //
					ReplyMessage.PRODUCT_IS_UNSELL.getMessage());
		}
		// 檢查買賣家是否為同一個帳號
		if (product.getUserId() == user.getUserId()) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		LocalDate createDate = LocalDate.now();
		// 血寫dao
		// buyerId>productId>createDate>status buyerCheck、sellerCheck在dao給0就好
		orderDao.addOrder(id, vo.getProductId(), createDate, OrderStatus.PENDING.getMessage());

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

	// 同意交易(賣家操作)
	public BasicResponse acceptOrder(int id, ChangeOrderStatusVo vo) {

		// 由訂單編號查詢完整訂單資訊
		Order order = orderDao.getOrderById(vo.getOrderId());

		// 檢查是否有完整資訊
		if (order == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		// 有，以session傳入id查詢user，此時session是賣家
		User user = userDao.getById(id);
		// 當user=null
		if (user == null) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		// 抓商品資訊
		Product product = productDao.findByProductId(order.getProductId());
		User owner = userDao.getById(product.getUserId());
		// email.equals(order.s...)
		if (!user.getUserEmail().equals(owner.getUserEmail())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}

		// 改變交易狀況
		orderDao.updateStatus(vo.getOrderId(), OrderStatus.ACCEPTED.getMessage());

		// 傳入第一個status為取消,第二個為等待中(只有這個會更改)
		orderDao.cancelOtherOrders(order.getProductId(), order.getOrderId(), OrderStatus.CANCELLED.getMessage(),
				OrderStatus.PENDING.getMessage());
		// 更改交易狀況為交易中
		productDao.changeStatus(order.getProductId(), ProductStatus.Trade.getMassage());

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

	// 買家主動取消訂單(vo.email是買家的)
	public BasicResponse canaelOrder(int id, ChangeOrderStatusVo vo) {
		// 由訂單編號查詢完整訂單資訊
		Order order = orderDao.getOrderById(vo.getOrderId());

		// 檢查是否有完整資訊
		if (order == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}

		// 先檢查user有沒有資料，或他的狀態是不是正常，只要其一沒滿足就沒有權限
		User user = userDao.getById(id);
		if (user == null || !(user.getStatus().equals(UserStatus.Normal.getMessage()))) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		// 取得訂單中的買家資訊
		User buyer = userDao.getById(order.getBuyerId());
		// 如email != 買家email，則沒有進行此操作的權限
		//(因為email是買家的，所以如果不相等則沒有進行此操作的權限)
		if (!user.getUserEmail().equals(buyer.getUserEmail())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}

		orderDao.updateStatus(vo.getOrderId(), OrderStatus.CANCELLED.getMessage());
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());

	}

	// 買賣家確認交易 delivery->交貨
	// check資料0->1，兩個皆為1->change status
	public BasicResponse checkDelivery(int id, ChangeOrderStatusVo vo) {

		Order order = orderDao.getOrderById(vo.getOrderId());

		if (order == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}

		if (!order.getStatus().equals(OrderStatus.ACCEPTED.getMessage())) {
			return new BasicResponse(ReplyMessage.ORDER_STATUS_ERROR.getCode(), //
					ReplyMessage.ORDER_STATUS_ERROR.getMessage());
		}

		// 提取order其中的productId找出完整的product資料
		Product product = productDao.findByProductId(order.getProductId());

		// 檢查是哪一方的帳號
				if (id==order.getBuyerId()) {
					orderDao.buyerCheack(order.getOrderId());
				}

				else if (id==product.getUserId()) {
					orderDao.salesCheack(order.getOrderId());
				}
				// 都不是
				else {
					return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
							ReplyMessage.NO_PERMISSIONS.getMessage());
				}

		// 抓最新的order資料
		Order update = orderDao.getOrderById(vo.getOrderId());

		if (update.isBuyerCheck() && update.isSellerCheck()) {
			orderDao.updateStatus(vo.getOrderId(), OrderStatus.COMPLETED.getMessage());
		}
		// 確認交易完成後商品下架
		productDao.changeStatus(update.getProductId(), ProductStatus.Removed.getMassage());
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

	// 給予評價
	public BasicResponse giveLevel(int id, GoodLevelReq req) {

		User user = userDao.getById(id);
		if (user == null) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}

		/* 取得雙方id 1.取得完整訂單資訊 */
		Order order = orderDao.getOrderById(req.getOrderId());
		// 買方資料
		User buyer = userDao.getById(order.getBuyerId());
		// 賣方資料
		Product product = productDao.findByProductId(order.getProductId());
		User salersman = userDao.getById(product.getUserId());
		// 確認訂單狀況，如果不是完成就不能執行
		if (!order.getStatus().equals(OrderStatus.COMPLETED.getMessage())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		// 判斷帳號歸屬
		if (id == buyer.getUserId()) {
			// 當user為buyer時，給予的評價是賣家的
			// 設定salesman_rank
			orderDao.setSalesRank(req.getOrderId(), req.getLevel());
		} else if (id == salersman.getUserId()) {
			// 當user為salesman時，給予的評價是買家的
			// 設定buyer_rank
			orderDao.setbuyerRank(req.getOrderId(), req.getLevel());
		} else {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

}
