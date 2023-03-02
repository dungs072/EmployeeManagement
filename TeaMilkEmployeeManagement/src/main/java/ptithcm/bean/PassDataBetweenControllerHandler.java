package ptithcm.bean;

import org.springframework.stereotype.Service;

@Service()
public class PassDataBetweenControllerHandler {

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
