/*
 * Wei Han's New File
 *  Created on 25/09/2011
 */

public class RaceCar {
	final int MaxCarNum = 16;

	private int x_pos;
	private int y_pos;
	private int speed = 3;
	private String cur_Car;

	private int index;
	
	private boolean crashed = false;
	private boolean win = false;
	private boolean top = false;
	
	public RaceCar(){
		
	}
	
	public RaceCar(String carStr){
		this.cur_Car = carStr;
	}
	
	public RaceCar(String carStr,int x, int y){
		this.cur_Car = carStr;
		this.x_pos = x;
		this.y_pos = y;
	}

	public boolean isCrashed() {
		return crashed;
	}

	public void setCrashed(boolean creashed) {
		this.crashed = creashed;
	}

	public boolean isWin() {
		if(top){
			if(this.x_pos<=425&&this.y_pos>=450&&this.y_pos<=510){
				this.win=true;
				return win;
			}
			else {
				return win;
			}
		}
		else {
			if(this.x_pos>=425&&this.y_pos>=50&&this.y_pos<=110){
				this.top=true;
			}
			return win;
		}
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getIndex() {
		index  = getCarIndex(cur_Car);
		return index;
	}

	public int getX_pos() {
		return x_pos;
	}

	public void setX_pos(int x_pos) {
		this.x_pos = x_pos;
	}

	public int getY_pos() {
		return y_pos;
	}

	public void setY_pos(int y_pos) {
		this.y_pos = y_pos;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getCur_Car() {
		return cur_Car;
	}

	public void setCur_Car(String cur_Car) {
		this.cur_Car = cur_Car;
	}
	
	void speedUp(){
		speed +=1;
		if(speed>10)speed =10;
	}
	
	void slowDown(){
		speed -=1;
		if(speed<0) speed = 0;
	}
	
	void turnLeft(){
		cur_Car = getLeftCar(cur_Car);
	}
	
	void turnRight(){
		cur_Car = getRightCar(cur_Car);
	}

	public String getLeftCar(String car){
		int index = getCarIndex(car);
		if(index >= 0){
			index--;
			if(index<0) index=MaxCarNum-1;
			car = getCar(car,index);
		}
		return car;
	}
	
	public String getRightCar(String car){
		int index = getCarIndex(car);
		if(index >= 0){
			index++;
			if(index >= MaxCarNum) index= 0;
			car = getCar(car,index);
		}
		return car;
	}
	
	String getCar(String car,int index){
		int idx = car.lastIndexOf('.');
		idx--;
		while(Character.isDigit(car.charAt(idx)))idx--;
		return (car.substring(0,idx+1)+index+".gif");
	}
	
	int getCarIndex(String car){
		int carIndex;
		int index = car.lastIndexOf('.');
		index-=2;
		if(Character.isDigit(car.charAt(index))){
			carIndex = Integer.parseInt(Character.toString(car.charAt(index)))*10;
			index++;
			carIndex += Integer.parseInt(Character.toString(car.charAt(index)));
			return carIndex;
		}
		else{
			index++;
			if(Character.isDigit(car.charAt(index))){
				carIndex = Integer.parseInt(Character.toString(car.charAt(index)));
				return carIndex;
			}
		}
		return -1;
	}
}

