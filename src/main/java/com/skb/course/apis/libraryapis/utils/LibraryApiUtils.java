package com.skb.course.apis.libraryapis.utils;

public class LibraryApiUtils {

	public static boolean doesStringValueExists(String str) {
		if(str != null && str.length() > 0)
		{
			return true;
		}
		return false;
	}

}
