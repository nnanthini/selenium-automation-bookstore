package util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DataProviderClass{
	
	
	@DataProvider (name = "name-provider")
	  public Object[][] nameProvider() {
		  return new Object[][] {{"abcxyz"},{"123"},{""},{null},{"A123abc"},{"aAz$"},{" iop8"},{"#123abc"},{"AAaa12$#"}};
	  }

}
