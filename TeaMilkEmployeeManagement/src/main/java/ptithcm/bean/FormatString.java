package ptithcm.bean;

public class FormatString {

	public String getTextFormat(String text) {
		return text.trim().replaceAll(" +", " ");
	}
}
