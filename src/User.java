import java.io.Serializable;

public class User implements Serializable {
	String name;
	Boolean isMOH;

	public User(String name, Boolean isMOH) {
		this.name = name;
		this.isMOH = isMOH;
	}

	public String getName() {
		return name;
	}

	public Boolean getIsMOH() {
		return isMOH;
	}
}