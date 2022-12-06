package com.sos.common;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApplicationConstant {

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum ActiveStatus {
		ACTIVE("ACTIVE", "Kích hoạt", "primary"), INACTIVE("INACTIVE", "Ngừng kích hoạt", "error");

		private final String name;
		private final String description;
		private final String color;

		private ActiveStatus(String name, String description, String color) {
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
	public enum MemberRank {
		CLIENT("CLIENT", "Khách hàng", "default"), MEMBER("MEMBER", "Thành viên", "success"),
		SILVER("SILVER", "Bạc", "primary"), GOLD("GOLD", "Vàng", "warning"), DIAMOND("DIAMOND", "Kim cương", "error");

		private final String name;
		private final String description;
		private final String color;

		private MemberRank(String name, String description, String color) {
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
	public enum AccountStatus {
		ACTIVE("ACTIVE", "Kích hoạt", "primary"), INACTIVE("INACTIVE", "Ngừng kích hoạt", "error");

		private final String name;
		private final String description;
		private final String color;

		private AccountStatus(String name, String description, String color) {
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

	public enum OAuthProvider {
		local, facebook, google
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum PaymentMethod {
		CASH("CASH", "Tiền mặt", "success"), BANKING("BANKING", "Chuyển khoản", "primary");

		private final String name;
		private final String description;
		private final String color;

		private PaymentMethod(String name, String description, String color) {
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
		PENDING("PENDING", "Đang chờ", "warning"), FAILED("FAILED", "Thất bại", "error"),
		APPROVED("APPROVED", "Thành công", "primary");

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
		CREATED("CREATED", "Tạo Đơn Hàng", "FaRegFileAlt", "#2dc258"),
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

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum TransactionType {
		PAYMENT("PAYMENT", "Thanh toán", "primary"), REVERSE("REVERSE", "Hoàn tiền", "error");

		private final String name;
		private final String description;
		private final String color;

		private TransactionType(String name, String description, String color) {
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

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum ProductGender {
		MEN("MEN", "Nam", "primary"), WOMAN("WOMAN", "Nữ", "success"), UNISEX("UNISEX", "Unisex", "warning");

		private final String name;
		private final String description;
		private final String color;

		private ProductGender(String name, String description, String color) {
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

	public enum NotificationTopic {
		ADMIN("ADMIN", "Quản trị viên", "sos-admin"), USER("USER", "Người dùng", "sos-user"),
		ALL("ALL", "Tất cả", "sos-all");

		private final String name;
		private final String description;
		private final String topic;

		private NotificationTopic(String name, String description, String topic) {
			this.name = name;
			this.description = description;
			this.topic = topic;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getTopic() {
			return topic;
		}
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum ProductStatus {
		ACTIVE("ACTIVE", "Kinh doanh", "primary"), SUSPENSION("SUSPENSION", "Ngừng kinh doanh", "error"),
		COMING_SOON("COMING_SOON", "Hàng sắp về", "success");

		private final String name;
		private final String description;
		private final String color;

		private ProductStatus(String name, String description, String color) {
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
	public enum ShoeHeight {
		LOW_TOP("LOW_TOP", "Thấp", "primary"), MID_TOP("MID_TOP", "Vừa", "success"),
		HIGH_TOP("HIGH_TOP", "Cao", "warning");

		private final String name;
		private final String description;
		private final String color;

		private ShoeHeight(String name, String description, String color) {
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
	public enum Benefit {
		NEUTRAL("NEUTRAL", "Tất cả", "default"), WARM("WARM", "Thời tiết ấm áp", "warning"),
		COLD("COLD", "Thời tiết lạnh", "secondary"), HUMID("HUMID", "Thời tiết ẩm ướt", "primary");

		private final String name;
		private final String description;
		private final String color;

		private Benefit(String name, String description, String color) {
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
	public enum ShoeFeel {
		NEUTRAL("NEUTRAL", "Ổn định", "default"), FLEXIBLE("FLEXIBLE", "Thoải mái linh hoạt", "primary"),
		SRPINGY("SRPINGY", "Co dãn đàn hồi", "secondary"), SOFT("SOFT", "Mềm mại", "secondary");

		private final String name;
		private final String description;
		private final String color;

		private ShoeFeel(String name, String description, String color) {
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
	public enum Surface {
		NEUTRAL("NEUTRAL", "Tất cả", "default"), 
		FIRM("FIRM", "Kiên cố", "primary"),
		HARD_COURT("HARD_COURT", "Sân cứng", "secondary"),
		INDOOR_COURT("INDOOR_COURT", "Sân đấu trong nhà", "secondary"),
		ROAD("ROAD", "Đường", "info"),
		TRAIL("TRAIL", "Đường mòn", "default"),
		TURF("TURF", "Cỏ", "success");

		private final String name;
		private final String description;
		private final String color;

		private Surface(String name, String description, String color) {
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
