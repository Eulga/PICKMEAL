package pickmeal.dream.pj.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import pickmeal.dream.pj.restaurant.domain.Restaurant;
import pickmeal.dream.pj.restaurant.repository.RestaurantCheckDao;

@Service("RestaurantCheckService")
@Log
public class RestaurantCheckServiceImpl implements RestaurantCheckService {
	
	@Autowired
	RestaurantCheckDao rcd;
	
	
	public List<Restaurant> bringResList(List<Map<String, Object>> resultMap) {
		List<Restaurant> rList = new ArrayList<Restaurant>();
		
		for(int i=0; i<resultMap.size();i++) {
			Restaurant res = new Restaurant();
			Restaurant tempRes;
			long apiId = Long.valueOf(String.valueOf(resultMap.get(i).get("id")));
			double lat = Double.valueOf(String.valueOf(resultMap.get(i).get("y")));
			double lng = Double.valueOf(String.valueOf(resultMap.get(i).get("x")));
			String address = String.valueOf(resultMap.get(i).get("address_name"));
			String rName = String.valueOf(resultMap.get(i).get("place_name"));
			
			res.setApiId(apiId);
			res.setRName(rName);
			res.setAddress(address);
			res.setLat(lat);
			res.setLng(lng);
			// id는 디비의 값을 가져와서 세팅
			// rType은 만약 디비에 값이 있으면 그대로, insert면 false.
			
			rcd.insertResaurant(res);
			System.out.println("test1 : " + res.getRName());
			
//			rList.add(rcd.selectRestaurant(res));
			tempRes = rcd.selectRestaurant(res);
			res.setId(tempRes.getId());
			res.setRType(tempRes.isRType());
			System.out.println("test2 : " + res.getRName());
			log.info("test2 =  " + res.getRName());
			
			rList.add(res);
		}
//		rList.add(res);
//		rcd.checkResEntityByApiID(rList);
		log.info("test3 : " + rList);
		
		for(Restaurant r : rList) {
			log.info("id: " + r.getId() +  " lat: " + r.getLat() + " lng: " + r.getLng() + " address: " + r.getAddress() + " name: " + r.getRName() + " apiId: " + r.getApiId() + " rType: " + r.isRType() );
		}
		return rList;
	}
}
