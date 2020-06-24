package frameboi;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
 
public class TemplateMatching {
    public static Rect findMatch(Mat source, Mat template) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String filePath="C:\\Users\\MAHE\\Documents\\NetBeansProjects\\FrameBoi\\src\\frameboi\\";
        Mat outputImage=new Mat();    
        int machMethod=Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.cvtColor(template, template, Imgproc.COLOR_BGRA2BGR);
        Imgproc.matchTemplate(source, template, outputImage, machMethod);
            
        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        outputImage = null;
        Point matchLoc=mmr.maxLoc;
        Rect rect = new Rect(matchLoc, new Size(template.width(), template.height()));
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows()), new Scalar(255, 255, 255), 2);
        Imgcodecs.imwrite(filePath+"sonuc.jpg", source);
        System.out.println("Complated.");
        return rect;
        //Draw rectangle on result image
       
        
    }
 
}