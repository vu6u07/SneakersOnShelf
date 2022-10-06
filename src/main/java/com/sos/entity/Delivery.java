package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sos.common.ApplicationConstant.DeliveryPartner;
import com.sos.common.ApplicationConstant.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String parcelTrackingId;

	private long fee;

	@Enumerated(EnumType.STRING)
	private DeliveryPartner deliveryPartner;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;

	private String note;

	private Date createDate;

	private Date updateDate;

	public Delivery(String parcelTrackingId, long fee, DeliveryPartner deliveryPartner, DeliveryStatus deliveryStatus,
			String note, Date createDate, Date updateDate) {
		this.parcelTrackingId = parcelTrackingId;
		this.fee = fee;
		this.deliveryPartner = deliveryPartner;
		this.deliveryStatus = deliveryStatus;
		this.note = note;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
