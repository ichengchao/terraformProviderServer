package terraformDemo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import terraformDemo.model.Demo;
import terraformDemo.model.Page;
import terraformDemo.utils.UUIDUtils;

@Component
public class DemoService {

	private static Map<String, Demo> map = new ConcurrentHashMap<>();
//	static {
//		for (int i = 0; i < 55; i++) {
//			String id = UUIDUtils.generateUUID();
//			Demo demo = new Demo();
//			demo.setId(id);
//			demo.setName("name_" + i);
//			demo.setComment(RandomStringUtils.randomAlphabetic(10));
//			map.put(id, demo);
//		}
//	}

	public List<Demo> searchDemo(String simpleSearch, Page page) {
		List<Demo> result = new ArrayList<Demo>();
		for (Demo demo : map.values()) {
			if (StringUtils.isBlank(simpleSearch) || demo.getName().contains(simpleSearch)) {
				result.add(demo);
			}
		}

		// 排序
		if (null != page) {
			if (StringUtils.isNotBlank(page.getSort())) {
				if ("name".equalsIgnoreCase(page.getSort())) {
					Collections.sort(result, new Comparator<Demo>() {

						@Override
						public int compare(Demo o1, Demo o2) {
							return o1.getName().compareTo(o2.getName());
						}

					});
				} else if ("comment".equalsIgnoreCase(page.getSort())) {
					Collections.sort(result, new Comparator<Demo>() {

						@Override
						public int compare(Demo o1, Demo o2) {
							return o1.getComment().compareTo(o2.getComment());
						}

					});
				} else if ("id".equalsIgnoreCase(page.getSort())) {
					Collections.sort(result, new Comparator<Demo>() {

						@Override
						public int compare(Demo o1, Demo o2) {
							return o1.getId().compareTo(o2.getId());
						}

					});
				}

			}
			if ("DESC".equalsIgnoreCase(page.getDir())) {
				Collections.reverse(result);
			}

			if (null != page.getStart() && null != page.getLimit()) {
				int toIndex = result.size() < (page.getStart() + page.getLimit()) ? result.size()
						: (page.getStart() + page.getLimit());
				result = result.subList(page.getStart(), toIndex);
			}

			page.setTotal(map.size());
		}

		return result;
	}

	public void addDemo(Demo demo) {
		Assert.notNull(demo, "demo can not be null!");
		Assert.hasText(demo.getName(), "name can not be blank!");
		demo.setId(UUIDUtils.generateUUID());
		map.put(demo.getId(), demo);
	}

	public void updateDemo(Demo demo) {
		map.put(demo.getId(), demo);
	}

	public void deleteDemo(String id) {
		Assert.notNull(id, "id can not be null!");
		map.remove(id);
	}

	public void batchDeleteDemo(List<String> idList) {
		Assert.notNull(idList, "idList can not be null!");
		for (String id : idList) {
			map.remove(id);
		}
	}

	public Demo getDemoById(String id) {
		Assert.notNull(id, "id can not be null!");
		return map.get(id);
	}

}
