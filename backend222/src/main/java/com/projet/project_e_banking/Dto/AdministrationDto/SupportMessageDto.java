package com.projet.project_e_banking.Dto.AdministrationDto;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.Date;

public class SupportMessageDto {

    private Long id ;
    private String message ;
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestmp ;
    private Long userid;
    private Long adminid ;

    public SupportMessageDto(){}

    public SupportMessageDto(Long id, String message,String type, Date timestmp, Long userid, Long adminid) {
        this.id = id;
        this.message = message;
        this.type=type;
        this.timestmp = timestmp;
        this.userid = userid;
        this.adminid=adminid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestmp() {

        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getAdminid() {
        return adminid;
    }

    public void setAdminid(Long adminid) {
        this.adminid = adminid;
    }
}
