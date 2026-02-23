package com.dhatvibs.modules.form.entity;

public enum WorkflowStatus {

    SUBMITTED,              // Executive submitted
    BPO_VERIFIED,           // BPO reviewed
    CORRECTION_REQUESTED,   // BPO requested correction
    REOPENED,               // Manager approved & reopened
    RESUBMITTED,            // BPO modified & resubmitted
    CLOSED                  // Final state
}