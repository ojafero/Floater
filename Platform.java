
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle{
	
	private double width;
	private double height;
	private double image;
	private double velocity = 200;
	
	Platform(double width,double height, String spriteImgLocation){
		this.width = width;
		this.height = height;
		this.setWidth(width);
		this.setHeight(height);
		
		
		Image sprite = new Image("file:"+spriteImgLocation);
    	ImagePattern imageP = new ImagePattern(sprite);
    	
    	this.setFill(imageP);
    	
	}
	

	public void setPlatformCoordinates(double X, double Y) {
		this.setX(X);
    	this.setY(Y);
	}


	public void moveDown(double deltat) {
		double y = getY();
		y = y + velocity*deltat;
		setY(y);
	}
	
}
