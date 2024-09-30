package cz.zcu.kiv.pia.labs.domain;

public enum DocumentState {
    /**
     * Document was requested by the investigator.
     */
    REQUESTED,
    /**
     * Requested document was uploaded by the insured/impaired.
     */
    UPLOADED,
    /**
     * Uploaded document was approved by the investigator.
     */
    APPROVED,
}
