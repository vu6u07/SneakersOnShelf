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
	public enum SaleMethod {
		DELIVERY("DELIVERY", "Giao hàng", "primary"), RETAIL("RETAIL", "Tại quầy", "success");

		private final String name;
		private final String description;
		private final String color;

		private SaleMethod(String name, String description, String color) {
			this.name = name;
			this.description = description;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getColor() {
			return color;
		}
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum TransactionStatus {
		PENDING("PENDING", "Đang chờ xác nhận", "warning"), APPROVED("APPROVED", "Đã thanh toán", "primary");

		private final String name;
		private final String description;
		private final String color;

		private TransactionStatus(String name, String description, String color) {
			this.name = name;
			this.description = description;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getColor() {
			return color;
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
		PENDING("PENDING", "Đang chờ xác nhận", "warning"), CONFIRMED("CONFIRMED", "Đã xác nhận", "success"),
		SHIPPING("SHIPPING", "Đang vận chuyển", "secondary"), CANCELLED("CANCELLED", "Đã hủy", "error"),
		APPROVED("APPROVED", "Đã hoàn thành", "primary"), REVERSE("REVERSE", "Đã trả hàng", "info");

		private final String name;
		private final String description;
		private final String color;

		private OrderStatus(String name, String description, String color) {
			this.name = name;
			this.description = description;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getColor() {
			return color;
		}
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum OrderTimelineType {
		CREATED("CREATED", "Đơn Tạo Đơn Hàng", "FaRegFileAlt", "#2dc258"),
		CONFIRMED("CONFIRMED", "Đã Xác Nhận Thông Tin Thanh Toán", "FaMoneyCheckAlt", "#2dc258"),
		SHIPPING("SHIPPING", "Đã Giao Cho Đơn Vị Vận Chuyển", "FaTruck", "#2dc258"),
		APPROVED("APPROVED", "Đã Nhận Được Hàng", "FaCalendarCheck", "#2dc258"),
		CANCELLED("CANCELLED", "Đơn Hàng Đã Hủy", "FaWindowClose", "#9c2919"),
		EDITED("EDITED", "Chỉnh Sửa Đơn Hàng", "FaPenSquare", "#ffc107");

		private final String name;
		private final String title;
		private final String icon;
		private final String color;

		private OrderTimelineType(String name, String title, String icon, String color) {
			this.name = name;
			this.title = title;
			this.icon = icon;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getTitle() {
			return title;
		}

		public String getIcon() {
			return icon;
		}

		public String getColor() {
			return color;
		}

	}

	public enum OrderItemStatus {
		APPROVED, REVERSE
	}

	public enum TransactionType {
		PAYMENT, REVERSE
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum CartStatus {
		PENDING("PENDING", "Đang chờ", "primary"), CANCELLED("CANCELLED", "Đã hủy", "error"),
		APPROVED("APPROVED", "Đã hoàn thành", "success");

		private final String name;
		private final String description;
		private final String color;

		private CartStatus(String name, String description, String color) {
			this.name = name;
			this.description = description;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getColor() {
			return color;
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

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum VoucherStatus {
		ACTIVE("ACTIVE", "Kích hoạt", "primary"), INACTIVE("INACTIVE", "Đã hủy", "error");
		
		private final String name;
		private final String description;
		private final String color;

		private VoucherStatus(String name, String description, String color) {
			this.name = name;
			this.description = description;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getColor() {
			return color;
		}
	}

	public enum VoucherType {
		DISCOUNT, PERCENT
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum VoucherAccess {
		PUBLIC("PUBLIC", "Công khai", "primary"), PROTECTED("PROTECTED", "Giới hạn", "warning");
		
		private final String name;
		private final String description;
		private final String color;

		private VoucherAccess(String name, String description, String color) {
			this.name = name;
			this.description = description;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getColor() {
			return color;
		}
	}

}
