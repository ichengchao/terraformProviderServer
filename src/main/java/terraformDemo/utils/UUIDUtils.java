package terraformDemo.utils;

import java.util.UUID;

public class UUIDUtils {

//    public static String generateUUID() {
//        return UUID.randomUUID().toString().replaceAll("-", "");
//    }

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		System.out.println(generateUUID());
	}

}
