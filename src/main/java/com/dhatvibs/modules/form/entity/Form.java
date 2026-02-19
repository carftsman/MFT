
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
  private String vendorLocation; //added
  private String doorNumber; 
  private String streetName; 
  private String areaName; 
  private String pinCode;
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
  
 
  
  }
 


/*
 * package com.dhatvibs.modules.form.entity;
 * 
 * import java.time.LocalDateTime;
 * 
 * import jakarta.persistence.*; import lombok.*;
 * 
 * @Entity
 * 
 * @Table(name = "forms")
 * 
 * @Getter
 * 
 * @Setter
 * 
 * @NoArgsConstructor
 * 
 * @AllArgsConstructor
 * 
 * @Builder public class Form {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * private Long executiveId; private String executiveName; private Long
 * teamleadId; private String teamleadName;
 * 
 * private String vendorShopName; private String vendorName; private String
 * contactNumber; private String mailId; private String vendorLocation; private
 * String doorNumber; private String streetName; private String areaName;
 * private String pinCode; private String state;
 * 
 * @Enumerated(EnumType.STRING) private FormTag tag;
 * 
 * @Enumerated(EnumType.STRING) private FormStatus status;
 * 
 * @Column(length = 1000) private String review;
 * 
 * // ============================= // 🔥 BPO RELATED FIELDS //
 * =============================
 * 
 * private Long assignedBpoId; private String assignedBpoName; private Boolean
 * isAssigned = false;
 * 
 * private Boolean bpoSolved;
 * 
 * private LocalDateTime nextFollowUpDate;
 * 
 * @Column(length = 1000) private String bpoReview;
 * 
 * private LocalDateTime createdAt; private LocalDateTime updatedAt;
 * 
 * @PrePersist public void prePersist() { this.createdAt = LocalDateTime.now();
 * this.updatedAt = LocalDateTime.now(); }
 * 
 * @PreUpdate public void preUpdate() { this.updatedAt = LocalDateTime.now(); }
 * }
 */
