package com.metanet.amatmu.businessman.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.businessman.dto.BmUpdateRestImgDto;
import com.metanet.amatmu.businessman.dto.BmUpdateRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.UpdateBmInfoDto;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.UpdateRegisterRestaurantDto;

@Repository
@Mapper
public interface IBusinessmanRepository {

	Long selectMaxBmNo();
	void registerBm(Businessman businessman);
	int checkRestaurantDuplicate(long restaurantId);
	int checkLicenseNumberDuplicate(String licenseNumber);
	List<Businessman> getBmListByMemberId(Long memberId);
	Businessman getBmByBmId(Long businessmanId);
	int updateBmInfo(UpdateBmInfoDto updateBmInfoDto);
	
	int updateBmRequested(UpdateRegisterRestaurantDto updateRegisterRestaurantDto);
	int deleteBmByRestId(Long restId);
	Restaurant selectRestaurantByBmId(long bmId);
	int updateRestaurantImage(BmUpdateRestImgDto dto);
	int updateRestaurantInfo(BmUpdateRestaurantInfoDto dto);
	
	List<Businessman>	selectBmanPartnershipRequests();
	int					updateBmStatusGranted(Long businessmanId);
}
