
  package com.dhatvibs.modules.form.entity; 
  import java.time.LocalDateTime;  
  import jakarta.persistence.*; 
  import lombok.*;
  @Entity  
  @Table(name = "forms")
  @Getter  
  @Setter  
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder 
  public class Form {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long id;
  
  private Long executiveId; 
  private String executiveName; 
  private Long teamleadId; 
  private String teamleadName;
  
  private String vendorShopName;
  private String vendorName;
  private String contactNumber; 
  private String mailId;
  @Enumerated(EnumType.STRING)
  private VendorType vendorType; //added
  private String vendorLocation; //added
  private Double latitude;       //added
  private Double longitude;      //added
  private String doorNumber; 
  private String streetName; 
  private String areaName; 
  private String pinCode; 
  private String district;   //added
  private String state;
  
  @Enumerated(EnumType.STRING) 
  private FormTag tag;
  
  @Enumerated(EnumType.STRING) 
  private FormStatus status;
  
  @Column(length = 1000) 
  private String review;
  
  
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  @PrePersist 
  public void prePersist() { 
	  this.createdAt = LocalDateTime.now();
      this.updatedAt = LocalDateTime.now(); 
      }
  
  @PreUpdate
  public void preUpdate() { 
	  this.updatedAt = LocalDateTime.now(); 
	  }
  
//BPO Assignment
private Long assignedBpoId;
private String assignedBpoName;

//BPO Action
//private Boolean solved;  // true = solved, false = not solved
@Builder.Default
@Column(nullable = false)
private Boolean solved = false;
private String idNumber;     //added
private String bpoName;   //added
private String executiveReview;
private String vendorReview;  

@Column(length = 1000)
private String bpoReason;

private LocalDateTime bpoActionDate;
private LocalDateTime reappearDate; 




//REQUEST RESEND
//Resend Request Flow
@Builder.Default
private Boolean resendRequested = false;

@Column(length = 1000)
private String resendReason;

@Builder.Default
private Boolean resendApproved = false;

 

@Enumerated(EnumType.STRING)
private WorkflowStatus workflowStatus;

private Long managerId;
private String managerName;

private LocalDateTime resendApprovedDate;  

private String vendorMessage;  //added



//added
//Vendor onboarding follow-up
private Boolean vendorReady;
private Integer onboardInDays;
private LocalDateTime onboardFollowupDate;
  
  }
 

