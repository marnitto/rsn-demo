package risk.dashboard.ehcache;

import java.io.Serializable;

/**
 * A Widget, which is an object that will be cached by EHCache
 */
@SuppressWarnings("serial")
public class Widget implements Serializable {
	private String name;

	public Widget() {
	}

	public Widget(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}