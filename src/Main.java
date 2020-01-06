
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("aqui");
		 try {
			 
			 BufferedImage image = ImageIO.read(new File("src/assinatura.jpg"));
			 
		    Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f,
		        1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
		    BufferedImageOp op = new ConvolveOp(kernel);
		    image = op.filter(image, null);
			 
			 image = threshold(image,250);
			 
			 image = resize( image , 200,200);

			 File outputfile = new File("src/saved.png");
			 ImageIO.write(image, "png", outputfile);
			 
        } catch (IOException e) {
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            e.printStackTrace();
        }
	}
	
    public static BufferedImage threshold(BufferedImage image, int limiar) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {               
        int rgb = image.getRGB(i, j);               
        int r = (int)((rgb&0x00FF0000)>>>16);
                int g = (int)((rgb&0x0000FF00)>>>8);
                int b = (int) (rgb&0x000000FF);
                int media = (r + g + b) / 3;
                Color white = new Color(255, 255, 255);
                Color black = new Color(0, 0, 0);

                
                System.out.print("media");
                System.out.println(media);
                if (media < limiar)
                    image.setRGB(i, j, black.getRGB());
                else
                    image.setRGB(i, j, white.getRGB());
            }
        }
        return image;
    }
	
	private static BufferedImage resize(BufferedImage image , int maxWidht , int maxHeight ) {
		int percentW , percentH, percent , width, height ;
		
		percentW = (maxWidht*100)/image.getWidth();
		percentH = (maxHeight*100)/image.getHeight();
		
		System.out.println(percentH);
		System.out.println(percentW);
		
		if(percentW < percentH)
			percent = percentW;
		else
			percent = percentH;
		
		
		
		width = (percent * image.getWidth())/100;
		height = (percent * image.getHeight())/100;
		
		
	    BufferedImage resizedImage = new BufferedImage(
	    		width, 
	    		height,
	    		BufferedImage.TYPE_INT_ARGB
	    );
	    
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, width, height, null);
	    g.dispose();
	    return resizedImage;
	}


}
