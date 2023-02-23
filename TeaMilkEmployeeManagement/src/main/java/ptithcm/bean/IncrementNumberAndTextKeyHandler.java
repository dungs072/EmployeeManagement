package ptithcm.bean;

import java.util.List;

import org.springframework.stereotype.Service;

@Service()
public class IncrementNumberAndTextKeyHandler {
	
	private List<Primarykeyable> keys = null;
	private int maxNumber;
	
	
	
	public void initialKeyHandler(List<Primarykeyable> keys) {
		this.keys = keys;
		maxNumber = 0;
		findMaxNumberInKeys();
	}
	
	private void findMaxNumberInKeys() {
		for(var key:keys) {
			String idText = key.getPrimaryKey().replaceAll("[^0-9]", "");
			int idNumber = Integer.parseInt(idText);
			if(maxNumber<idNumber) {
				maxNumber = idNumber;
			}
		}
	}
	public String getNewKey(String prefix) {
		maxNumber++;
		return prefix+ Integer.toString(maxNumber);
	}
	
	

}
