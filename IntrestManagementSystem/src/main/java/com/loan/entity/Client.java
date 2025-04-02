package com.loan.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Entity
@Table(name = "clients")
public class Client {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getAmountTook() {
		return amountTook;
	}

	public void setAmountTook(double amountTook) {
		this.amountTook = amountTook;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getTotalMonths() {
		return totalMonths;
	}

	public void setTotalMonths(long totalMonths) {
		this.totalMonths = totalMonths;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;
    private String address;
    private LocalDate dateTaken;
    private LocalDate datePaid;
    private double interestRate;
    private double amountTook;
    private double totalAmount;
    private long totalMonths;
    private String status; // New field to track payment status

    public long calculateTotalMonths() {
        if (this.dateTaken == null || this.datePaid == null) {
            throw new IllegalArgumentException("Both dateTaken and datePaid must be provided");
        }
        return ChronoUnit.MONTHS.between(dateTaken, datePaid);
    }

    public void calculateTotalAmount() {
        double interestAmount = (amountTook * interestRate * totalMonths) / 100;
        this.totalAmount = amountTook + interestAmount;
    }

    public void determinePaymentStatus() {
        this.status = (datePaid != null && (datePaid.isBefore(LocalDate.now()) || datePaid.isEqual(LocalDate.now()))) ? "Paid" : "Pending";
    }

    @PrePersist
    @PreUpdate
    public void preCalculate() {
        if (this.dateTaken != null && this.datePaid != null) {
            this.totalMonths = calculateTotalMonths();
            calculateTotalAmount();
            determinePaymentStatus();
        }
    }

	public LocalDate getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(LocalDate dateTaken) {
		this.dateTaken = dateTaken;
	}

	public LocalDate getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(LocalDate datePaid) {
		this.datePaid = datePaid;
	}

    // Custom setters can be removed due to @Data handling them
}
