package ptithcm.bean;

import org.springframework.stereotype.Service;

@Service()
public class PassDataBetweenControllerHandler {

	private String data;
	private String authorityId;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data.strip();
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

}
