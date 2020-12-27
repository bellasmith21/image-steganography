import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class TextHider {
	/* pre : takes in a buffered image and a string
	 * post: embeds the string of text into the image using only the least
	 * 		 significant digit, and returns a buffered image
	 */
	private static BufferedImage hideTextLSB(BufferedImage image, String text) {
		//pixels
		int x = 0;
		int y = 0;
		//current lsb
		int K = 1;
		//iterates through char bits
		int decToBit = 0x00000001;
		for(int i = 0; i < text.length(); i++) {
			//stores ascii num of current character
			int character = (int) text.charAt(i);
			for(int j = 0; j < 8; j++) {
				//stores current bit of character (true is 1; false is 0)
				boolean currentBit = (character & decToBit) == 1;
				//if ldb of pixel rgb is not equal to the currentBit, then add 2^k 0-> 1; else subtract 2^k
				if(LSB(K, image.getRGB(x,y)) != currentBit) {
					if(currentBit) {
						image.setRGB(x, y, image.getRGB(x,  y)+(int)Math.pow(2, K));
					} else {
						image.setRGB(x, y, image.getRGB(x,  y)-(int)Math.pow(2, K));
					}
				}
				//update current pixel
				if(x == image.getWidth()-1) {
					x = 0;
					y++;
				} else {
					x++;
				}
				if(y == image.getHeight()) {
					x = 0;
					y = 0;
					K++;
				}
				//iterates through character bit
				character = character >> 1;
			}
		}
		
		//save image
        try {
            ImageIO.write(image, "jpg", new File("C://Users/HP/Documents/MY WEBSITE/assets/img/Projects/StegImages/843750chars.jpg"));

        } catch (IOException e) {
        	System.out.println(e);
        }
		return image;
	}
	
	/* pre : takes in an encoded image and the int length of the secret message
	 * post: returns the message encoded in the image
	 */
	public static void revealText(BufferedImage image, int length) {
		System.out.println("Revealed text: ");
		int x = 0;
		int y = 0;
		int K = 1;
		
		for(int i = 0; i < length; i++) {	
			int character = 0;
			for(int j = 0; j < 8; j++) {
				if(LSB(K, image.getRGB(x, y))) {
					character = character >> 1;	
					character = character | 0x80;
				} else {
					character = character >> 1;
				}
				//update current pixel
				if(x == image.getWidth()-1) {
					x = 0;
					y++;
				} else {
					x++;
				}
				if(y == image.getHeight()) {
					x = 0;
					y = 0;
					K++;
				}
			}
			//System.out.print((char)character);
		}
	}	
	
	//private helper method to get Kth least signicant bit from a given int
	private static boolean LSB(int K, int num) {
		boolean x = (num & (1 << (K-1))) != 0; 
        return x; 
	}
	
	public static void main(String[] args) {
		BufferedImage originalImage = null;
		BufferedImage newImage = null;
		String s = "";
		try {
			originalImage = ImageIO.read(new File("PICTURES/IMG_3459.JPG"));
			newImage = ImageIO.read(new File("PICTURES/IMG_3459.JPG"));
			
			Scanner scan = new Scanner(new File("TEXT/Shakespeare.txt"));
            while(scan.hasNextLine() && s.length() <= 843750) {
            	if(s != "") {
            		s += " ";
            	}
            	s += scan.nextLine();
            }
            scan.close();
        } catch (IOException e) {
            System.out.println("an error occured while processing file");
            System.out.println(e);
            System.exit(1);
        }
		newImage = hideTextLSB(newImage, s);
		
		revealText(newImage, s.length());
		JFrame frame = new JFrame("Text Steganography");
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel(new ImageIcon(originalImage));
		JLabel label2 = new JLabel(new ImageIcon(newImage));
		panel.add(label1);
		panel.add(label2);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
