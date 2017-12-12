package objects.base;

import objects.units.Unit;

public class BuildOrder {
	public Class<? extends Unit> name;
	public int time;
	public int value;
	
	BuildOrder(Class<? extends Unit> name, int time, int value) {
		this.name = name;
		this.time = time;
		this.value = value;
	}

}
