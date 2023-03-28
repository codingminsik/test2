package MinSik.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	
	public static String getNowDateTime() {
	    LocalDateTime now = LocalDateTime.now();

        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
 
        return formatedNow;
	}

}
