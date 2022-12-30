package com.game.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException, IllegalArgumentException{
		
		// fetch image from path
		image = ImageIO.read(this.getClass().getResource(path));
		return image;
	}
	
}
