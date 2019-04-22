package cn.nju.se15.enginems.view.map.direction;

public interface Isensor {
	
	public boolean isSupport();
	
	public void on(int speed);
	
	public void off();

	public float getMaximumRange();
}
