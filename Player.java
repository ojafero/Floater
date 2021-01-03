
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	
	private double width;
	private double height;
	private Image sprite;
	
	Player(double width,double height, String spriteImgLocation){
		this.width = width;
		this.height = height;
		Image sprite = new Image("file:"+spriteImgLocation);
    	ImagePattern imageP = new ImagePattern(sprite);
    	
    	this.setFill(imageP);
    	setCharacterSize(width,height);
	}
	
	public void setCharacterCoordinates(double X, double Y) {
		this.setX(X);
    	this.setY(Y);
	}
	
	public void setCharacterSize(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	//Move character to left and update image
	public void moveLeft(double deltat) {
		setX(getX()-200*deltat);
		
		//Image to show that character is moving left
		Image image = new Image("file:Wraith_03_Moving Backward_000.png");
		ImagePattern imagePattern = new ImagePattern(image);
    	setFill(imagePattern);
	}
	
	public void moveRight(double deltat) {
		
		setX(getX()+200*deltat);
		Image image = new Image("file:Wraith_03_Moving Forward_000.png");
		ImagePattern imagePattern = new ImagePattern(image);
    	setFill(imagePattern);
	}
	
	//Update image of character to show its not in motion
	public void remainIdle() {
		Image img = new Image("file:Wraith_03_Idle_011.png");
		ImagePattern imagePattern = new ImagePattern(img);
    	setFill(imagePattern);
	}
}
