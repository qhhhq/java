package util;

import java.util.UUID;

public class UUIDTest {

	public static void main(String[] args) {
		UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
