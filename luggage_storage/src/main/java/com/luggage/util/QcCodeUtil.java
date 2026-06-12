package com.luggage.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
@Component
public class QcCodeUtil {
    private final static int WIDTH=300;
    private final static int HEIGHT=300;
    private final static String FORMAT="png";

    public static String generateBase64(String qctoken) throws WriterException {
        try {


        //1.配置参数
        Map<EncodeHintType,Object> hints=new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN,2);

        //2编码
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(qctoken, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        //3.点阵
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }


        //图片到byte
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, os);
        byte[] bytes = os.toByteArray();

        //byte -> base64
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        }
        catch (Exception e){
          throw  new RuntimeException("二维码生成失败");
        }
    }
}
