package com.sos.common;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApplicationConstant {

	public enum AccountStatus {
		ACTIVE, INACTIVE
	}

	public enum OAuthProvider {
		local, facebook, google
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum PaymentMethod {
		CASH("Tiền mặt"), BANKING("Chuyển khoản"), COD("Thanh toán khi nhận hàng");

		private final String description;

		private PaymentMethod(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum PaymentStatus {
		PENDING("Đang chờ xác nhận"), APPROVED("Đã thanh toán");

		private final String description;

		private PaymentStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum DeliveryStatus {

		PENDING("Đang chờ vận chuyển"), TRANSIT("Đang vận chuyển"), DELIVERED("Đã giao hàng"), CANCELLED("Đã hủy"),
		FAILED("Thất bại"), RETURNED("Đã trả hàng");

		private final String description;

		private DeliveryStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	public enum DeliveryPartner {
		PERSONAL("Cá Nhân"), GHN("Giao Hàng Nhanh");

		private final String description;

		private DeliveryPartner(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	public enum ExchangeStatus {
		PENDING, FAILED, APPROVED
	}

	public enum ProductStatus {
		ACTIVE, SUSPENSION, SOLD_OUT, COMING_SOON
	}

	public enum WishlistStatus {
		ACTIVE, CLOSE, NOTIFIED
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum OrderStatus {
		PENDING("Đang chờ xác nhận"), CONFIRMED("Đã xác nhận"), CANCELLED("Đã hủy"), APPROVED("Đã hoàn thành");

		private final String description;

		private OrderStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum CartStatus {
		PENDING("Đang chờ xác nhận"), CANCELLED("Đã hủy"), APPROVED("Đã hoàn thành");

		private final String description;

		private CartStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	public enum CustomerInfoStatus {
		ACTIVE, INACTIVE
	}

	public enum VerifyStatus {
		VERIFIED, UNVERIFIED
	}

	public enum VerifyCodeType {
		VERIFY_EMAIL
	}

	public enum ProductGender {
		MEN, WOMAN, UNISEX
	}

	public enum StockTransactionType {
		IMPORT, EXPORT, RETURN
	}

	public enum VoucherType {
		PUBLIC, REDEEM
	}

	public enum ReturnType {
		DEFECT
	}

	public enum ExchangeType {
		SIZE_CHANGE, DEFECT
	}

}
