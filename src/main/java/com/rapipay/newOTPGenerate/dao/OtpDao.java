package com.rapipay.newOTPGenerate.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapipay.newOTPGenerate.entities.OTPEntities;

@Repository
public interface OtpDao extends JpaRepository<OTPEntities, String>{

}
