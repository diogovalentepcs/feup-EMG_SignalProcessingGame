package com.game.src.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;

	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int imageNumber, int width, int height) {
		
		// getting image from sprite sheet
		int col = this.getImagePosition(imageNumber)[0];
		int row = this.getImagePosition(imageNumber)[1];

		
		BufferedImage img = image.getSubimage(col * 50 - 50, row * 50 - 50, width, height); // grabImage(1,1,50,50) grabs image from row 1, col 1, with 50x50px
		return img;
	}
	
	public int[] getImagePosition(int imageNumber) {
		//0 -> col, 1 -> row
		int arr[] = {0,0};
		if(imageNumber == 1) { 
			arr[0] = 1;
			arr[1] = 1;
		} else if(imageNumber == 2) {
			arr[0] = 2;
			arr[1] = 1;	
		} else if(imageNumber == 3) {
			arr[0] = 3;
			arr[1] = 1;	
		} else if(imageNumber == 4) {
			arr[0] = 1;
			arr[1] = 2;	
		} else if(imageNumber == 5) {
			arr[0] = 2;
			arr[1] = 2;	
		} else if(imageNumber == 6) {
			arr[0] = 3;
			arr[1] = 2;	
		} else if(imageNumber == 7) {
			arr[0] = 1;
			arr[1] = 3;	
		} else if(imageNumber == 8) {
			arr[0] = 2;
			arr[1] = 3;	
		} else if(imageNumber == 9) {
			arr[0] = 3;
			arr[1] = 3;	
		}
		
		return arr;	
	}
}
