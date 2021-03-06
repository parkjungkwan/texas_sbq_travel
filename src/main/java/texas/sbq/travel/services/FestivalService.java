package texas.sbq.travel.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import texas.sbq.travel.domains.Festival;
import texas.sbq.travel.domains.Pager;
import texas.sbq.travel.domains.Room;
import texas.sbq.travel.domains.Festival;
import texas.sbq.travel.mappers.FestivalMapper;
import texas.sbq.travel.mappers.FestivalMapper;
import texas.sbq.travel.services.FestivalService;
import texas.sbq.travel.utils.Box;
import texas.sbq.travel.utils.Inventory;
import texas.sbq.travel.utils.Printer;

@Service
public class FestivalService implements IService{
	@Autowired FestivalMapper festivalMapper;
	@Autowired Inventory<HashMap<String,String>> inventory;
	@Autowired Box<String> box;
	@Autowired Printer printer;
	@Autowired Festival festival;
	@Override public void save(Object o) { festivalMapper.insert((Festival) o);}
	@Override public String count(Object o) { return festivalMapper.count();}
	@Override public Festival detail(Object o) { return festivalMapper.selectById(String.valueOf(o));}
	@Override public List<?> list(Object o){ return festivalMapper.select((Pager) o);}
	@Override public void edit(Object o) { festivalMapper.update((Festival) o);}
	@Override public void remove(Object o) { festivalMapper.delete(String.valueOf(o));}
	public void create() { festivalMapper.create();}
	@Transactional
	public ArrayList<HashMap<String,String>> crawling(){
		String url = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EC%84%9C%EC%9A%B8%ED%96%89%EC%82%AC";
		inventory.clear();
		try {
			Document rawData = Jsoup.connect(url).timeout(10*1000).get();
			Elements title = rawData.select("span[class=tit_box]");
			Elements festivalimg = rawData.select("span[class=img_al] img");
			Elements date = rawData.select("span[class=date]");
			HashMap<String, String> map = null;
			for(int i = 0; i<title.size(); i++) {
			map = new HashMap<>();
			map.put("title", title.get(i).text());
			map.put("festivalimg", festivalimg.get(i).select("img").attr("src"));
			map.put("date", date.get(i).text());
			festival.setTitle(title.get(i).text());
			festival.setImage(festivalimg.get(i).select("img").attr("src"));
			festival.setTerm(date.get(i).text());
			festivalMapper.insert(festival);
			System.out.println("****"+festivalimg.get(i).text());
			inventory.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--------크롤링 결과---------");
		inventory.get().forEach(System.out::println);
		return inventory.get();
	}
	
	

}
