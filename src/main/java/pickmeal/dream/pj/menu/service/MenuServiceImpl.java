package pickmeal.dream.pj.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import pickmeal.dream.pj.menu.domain.Menu;
import pickmeal.dream.pj.menu.domain.Menuclassify;
import pickmeal.dream.pj.menu.repository.MenuDao;
import pickmeal.dream.pj.weather.domain.PickMealWeather;

@Log
@Service("menuService")
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuDao md;
	
	public MenuServiceImpl() {
		
	}
	@Override
	public void addMenu(Menu Menu) {
		
	}

	@Override
	public List<Menu> findAllMenus() {
		List<Menu> menulist = new ArrayList<Menu>();
		menulist = md.findAllMenus();
		
		return menulist;
	}

	@Override
	public Menu findMenuByClassify(Menuclassify menuclassify) {
		List<Menu> menulist = new ArrayList<Menu>();
		if(menuclassify.getSoupy()==2 && menuclassify.getHot_ice() == 2 && menuclassify.getCarbohydrate() == 4 && menuclassify.getMainFood() == 3 && menuclassify.getSpicy() == 2) {
			menulist = md.findMenuByMenuName("오마카세");
			return menulist.get(0);
		}else{
			
			if(menuclassify.getSoupy() == 2) {
				Random random = new Random();
				int randomMenuIndex = random.nextInt(2);
				menuclassify.setSoupy(randomMenuIndex);
			}
			if(menuclassify.getHot_ice() == 2) {
				Random random = new Random();
				int randomMenuIndex = random.nextInt(2);
				menuclassify.setHot_ice(randomMenuIndex);
			}
			if(menuclassify.getCarbohydrate() == 4) {
				Random random = new Random();
				int randomMenuIndex = random.nextInt(4);
				menuclassify.setCarbohydrate(randomMenuIndex);
			}
			if(menuclassify.getMainFood() == 3) {
				Random random = new Random();
				int randomMenuIndex = random.nextInt(3);
				menuclassify.setMainFood(randomMenuIndex);
			}
			if(menuclassify.getSpicy() == 2) {
				Random random = new Random();
				int randomMenuIndex = random.nextInt(2);
				menuclassify.setSpicy(randomMenuIndex);
			}
			//System.out.println(menuclassify);
			
			
			menulist = md.findMenuByClassify(menuclassify);
			if(menulist.size() == 0) {
				System.out.println("0개니까 오마카세" + menulist.size());
				menulist = md.findMenuByMenuName("오마카세");
				Menu menu = new Menu();
				menu = menulist.get(0);
				return menu;
			}else {
				System.out.println(menulist.size());
				Random random = new Random();
				int randomMenuIndex = random.nextInt(menulist.size());
	
				Menu randomMenu = menulist.get(randomMenuIndex);
				return randomMenu;
			}
		}
	}
	@Override
	public Menu findMenuByWeather(PickMealWeather wc) {
		int temperature;
		if(wc.getTemperature() >= 25) {
			temperature = 1;
		}else if (wc.getTemperature() <= 10) {
			temperature = 2;
		}else {
			temperature = 0;
		}
		
		List<Menu> menuList = md.findMenuByWeather(temperature, wc.getSky());
		Random rand = new Random();
		Menu menu = new Menu();
		
		if(menuList.size() == 0) {
			menu.setMenuName("DB에..추천메뉴가..없어..");
		} else {
			log.info(menuList.size()+"");
			menu = menuList.get(rand.nextInt(menuList.size()));			
		}
		if(menu.getImgPath() == null) {
			menu.setImgPath("");
		}
		return menu;
	}
}
