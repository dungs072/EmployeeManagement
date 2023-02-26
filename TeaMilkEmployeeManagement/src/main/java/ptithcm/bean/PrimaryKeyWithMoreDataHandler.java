package ptithcm.bean;

import org.springframework.stereotype.Service;

@Service()
public class PrimaryKeyWithMoreDataHandler {

	public String getPrimaryKeyBaseOnDatas(String data1, String data2, String data3) {
		return data1+"-"+data2+"-"+data3;
	}
	
	public String getPatternBaseOnDatas(String data1,String data2) {
		return data1+"-"+data2;
	}
}
