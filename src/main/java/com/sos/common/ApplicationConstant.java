package com.sos.common;

public class ApplicationConstant {
	
	public enum AccountStatus {
		ACTIVE, INACTIVE
	}

	public enum PaymentMethod {
		CASH, BANKING, COD
	}

	public enum PaymentStatus {
		PENDING, RECONCILING, APPROVED
	}

	public enum DeliveryStatus {
		PENDING, TRANSIT, DELIVERED, CANCELLED, FAILED, RETURNED
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

	public enum OrderStatus {
		TEMPORARY, PENDING, CONFIRMED, CANCELLED, APPROVED
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
