package ptithcm.bean;

import org.springframework.stereotype.Service;

@Service()
public class PrimaryKeyWithMoreDataHandler {

	public String getPrimaryKeyBaseOnDatas(String data1, String data2, String data3) {
		return data1.strip()+"-"+data2.strip()+"-"+data3.strip();
	}
	
	public String getPrimaryKeyBaseOnDatas(String data1, String data2) {
		return data1.strip()+"-"+data2.strip();
	}
	
	public String getPatternBaseOnDatas(String data1,String data2) {
		return data1.strip()+"-"+data2.strip();
	}
}
